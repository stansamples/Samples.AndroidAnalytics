package org.stansamples.android.analytics.provider

import kotlinx.coroutines.CoroutineDispatcher

internal class Contexts(
    val main: CoroutineDispatcher,
    val default: CoroutineDispatcher,
    val io: CoroutineDispatcher,
)
