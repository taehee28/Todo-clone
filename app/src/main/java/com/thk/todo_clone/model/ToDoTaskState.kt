package com.thk.todo_clone.model

import androidx.compose.runtime.Stable
import com.thk.data.models.Priority

/**
 * TaskScreen에서 사용하는 데이터 모델
 */
@Stable
data class ToDoTaskState(
    val id: Int = 0,
    val title: String = "",
    val description: String = "",
    val priority: Priority = Priority.LOW,
    val hasTitleError: Boolean = true,
    val hasDescriptionError: Boolean = true
)
