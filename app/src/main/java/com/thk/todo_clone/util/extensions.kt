package com.thk.todo_clone.util

import android.util.Log
import androidx.compose.ui.graphics.Color
import com.thk.data.models.Priority

val Priority.color: Color
    get() = Color(hexColor)

fun String?.toAction(): Action = kotlin.runCatching {
    Action.valueOf(this!!)
}.getOrElse {
    Action.NO_ACTION
}

inline fun <reified T> T.logd(message: String) = Log.d(T::class.java.simpleName, message)