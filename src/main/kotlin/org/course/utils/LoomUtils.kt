package org.course.utils


import kotlinx.coroutines.*
import java.util.concurrent.Executors
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext



public fun <T> runVirtual(context:CoroutineContext = EmptyCoroutineContext, block: suspend CoroutineScope.() -> T): T =
    runBlocking(context + Dispatchers.LOOM, block)


val Dispatchers.LOOM: CoroutineDispatcher
    get() = Executors.newVirtualThreadPerTaskExecutor().asCoroutineDispatcher()
