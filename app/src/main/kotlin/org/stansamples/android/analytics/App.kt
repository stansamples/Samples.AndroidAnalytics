package org.stansamples.android.analytics

import android.app.Application
import kotlinx.coroutines.Dispatchers
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
        _providers = Providers(
            contexts = contexts,
        )
    }

    companion object {
        private var _providers: Providers? = null
        val providers: Providers get() = checkNotNull(_providers) { "No providers!" }
    }
}
