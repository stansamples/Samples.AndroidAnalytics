package org.stansamples.android.analytics.provider

import kotlinx.coroutines.flow.Flow
import java.io.File

internal interface Analytics {
    sealed interface Event {
        class OnSave(val result: Result<File>) : Event
        class OnClear(val result: Result<File>) : Event
    }

    val events: Flow<Event>
    fun save(message: String)
    fun clear()
}
