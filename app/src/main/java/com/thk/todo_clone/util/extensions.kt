package com.thk.todo_clone.util

import android.util.Log
import androidx.compose.ui.graphics.Color
import com.thk.data.models.Priority
import com.thk.data.models.ToDoTask
import com.thk.todo_clone.model.Action
import com.thk.todo_clone.model.ToDoTaskState

val Priority.color: Color
    get() = Color(hexColor)

fun String?.toAction(): Action = kotlin.runCatching {
    Action.valueOf(this!!)
}.getOrElse {
    Action.NO_ACTION
}

fun ToDoTask.toToDoTaskState() = ToDoTaskState(
    id = this.id,
    title = this.title,
    description = this.description,
    priority = this.priority,
    hasTitleError = this.title.isBlank(),
    hasDescriptionError = this.description.isBlank()
)

fun ToDoTaskState.toToDoTask() = ToDoTask(
    id = this.id,
    title = this.title,
    description = this.description,
    priority = this.priority
)

inline fun <reified T> T.logd(message: String) = Log.d(T::class.java.simpleName, message)