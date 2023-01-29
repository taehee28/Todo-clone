package com.thk.todo_clone.util

import androidx.compose.runtime.Stable
import com.thk.data.models.Priority

@Stable
data class ToDoTaskState(
    val id: Int = 0,
    val title: String = "",
    val description: String = "",
    val priority: Priority = Priority.LOW,
    val hasTitleError: Boolean = true,
    val hasDescriptionError: Boolean = true
)
