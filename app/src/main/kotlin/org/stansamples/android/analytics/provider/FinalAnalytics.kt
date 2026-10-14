package org.stansamples.android.analytics.provider

import android.os.Environment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import org.stansamples.android.analytics.BuildConfig
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import java.util.concurrent.locks.ReentrantReadWriteLock
import kotlin.concurrent.withLock

internal class FinalAnalytics(
    private val contexts: Contexts,
) : Analytics {
    private val coroutineScope = CoroutineScope(SupervisorJob() + contexts.main)
    private val locks = ReentrantReadWriteLock()
    private val dirFormat = SimpleDateFormat("yyyyMMdd", Locale.US)
    private val _events = MutableSharedFlow<Analytics.Event>()
    override val events = _events

    init {
        dirFormat.timeZone = TimeZone.getTimeZone("utc")
    }

    override fun save(message: String) {
        coroutineScope.launch {
            val result = withContext(contexts.io) {
                runCatching {
                    locks.writeLock().withLock {
                        val now = System.currentTimeMillis()
                        val docs = Environment
                            .getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)
                            ?: error("No docs!")
                        val dir = docs
                            .resolve(BuildConfig.APPLICATION_ID)
                            .resolve("Events")
                            .resolve(dirFormat.format(Date(now)))
                        dir.mkdirs()
                        check(dir.exists()) { "No dir: $dir!" }
                        val fileName = "Events_${now}.json"
                        val file = dir.resolve(fileName)
                        val obj = JSONObject()
                        obj.put("date", now)
                        obj.put("message", message)
                        file.writeText(obj.toString())
                        file
                    }
                }
            }
            _events.emit(Analytics.Event.OnSave(result = result))
        }
    }

    override fun clear() {
        coroutineScope.launch {
            val result = withContext(contexts.io) {
                runCatching {
                    locks.writeLock().withLock {
                        val docs = Environment
                            .getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)
                            ?: error("No docs!")
                        val dir = docs
                            .resolve(BuildConfig.APPLICATION_ID)
                            .resolve("Events")
                        dir.deleteRecursively()
                        check(!dir.exists()) { "$dir exists!" }
                        dir
                    }
                }
            }
            _events.emit(Analytics.Event.OnClear(result = result))
        }
    }
}
