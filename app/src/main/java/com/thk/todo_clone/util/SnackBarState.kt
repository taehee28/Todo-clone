package com.thk.todo_clone.util

sealed class SnackBarState(val message: String, val actionLabel: String, val action: (() -> Unit)?) {
    data class Add(val title: String) : SnackBarState(
        message = "ADD: $title",
        actionLabel = "OK",
        action = null
    )
    data class Update(val title: String) : SnackBarState(
        message = "UPDATE: $title",
        actionLabel = "OK",
        action = null
    )
    data class Delete(val title: String, val block: () -> Unit) : SnackBarState(
        message = "DELETE: $title",
        actionLabel = "UNDO",
        action = block
    )
    object DeleteAll : SnackBarState(
        message = "All tasks removed",
        actionLabel = "OK",
        action = null
    )
}
