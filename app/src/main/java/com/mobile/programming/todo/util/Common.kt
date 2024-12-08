package com.mobile.programming.todo.util

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

val defaultDispatcher = Dispatchers.Default
val ioDispatcher = Dispatchers.IO
val mainDispatcher = Dispatchers.Main

val defaultScope = CoroutineScope(defaultDispatcher)
val ioScope = CoroutineScope(ioDispatcher)
val mainScope = CoroutineScope(mainDispatcher)

