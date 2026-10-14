package org.stansamples.android.analytics.provider

import kotlin.time.Duration

internal interface Analytics {
    data class Event(val date: Duration, val message: String)

    fun save(event: Event)
}
