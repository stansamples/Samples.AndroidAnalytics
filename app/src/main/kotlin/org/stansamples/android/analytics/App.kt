package org.stansamples.android.analytics

import android.app.Application
import kotlinx.coroutines.Dispatchers
import org.stansamples.android.analytics.provider.Analytics
import org.stansamples.android.analytics.provider.Contexts
import org.stansamples.android.analytics.provider.Providers

internal class App : Application() {
    override fun onCreate() {
        super.onCreate()
        val contexts = Contexts(
            main = Dispatchers.Main,
            default = Dispatchers.Default,
            io = Dispatchers.IO,
        )
        val analytics: Analytics = TODO()
        _providers = Providers(
            contexts = contexts,
            analytics = analytics,
        )
    }

    companion object {
        private var _providers: Providers? = null
        val providers: Providers get() = checkNotNull(_providers) { "No providers!" }
    }
}
