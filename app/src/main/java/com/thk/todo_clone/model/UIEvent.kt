package com.thk.todo_clone.model

import com.thk.data.models.Priority
import com.thk.data.models.ToDoTask

/**
 * Composable에서 ViewModel로 전달하는 Event
 */
sealed class UIEvent {
    data class SelectTask(val id: Int) : UIEvent()
    data class TitleChanged(val title: String) : UIEvent()
    data class DescriptionChanged(val description: String) : UIEvent()
    data class PriorityChanged(val priority: Priority) : UIEvent()
    data class SortChanged(val sort: Priority) : UIEvent()
    data class SearchTasks(val keyword: String) : UIEvent()
    data class SwipeToDeleteTask(val task: ToDoTask) : UIEvent()
    object AddTask : UIEvent()
    object UpdateTask : UIEvent()
    object DeleteTask : UIEvent()
    object DeleteAllTasks : UIEvent()
    object Undo : UIEvent()
    object OpenSearch : UIEvent()
    object CloseSearch : UIEvent()
    object SnackBarDismissed : UIEvent()
}