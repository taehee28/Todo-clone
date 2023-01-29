package com.thk.todo_clone.util

import androidx.compose.runtime.Stable
import com.thk.data.models.Priority

@Stable
data class ToDoTaskState(
    val id: Int,
    val title: String,
    val description: String,
    val priority: Priority,
    val hasTitleError: Boolean = false,
    val hasDescriptionError: Boolean = false
)
