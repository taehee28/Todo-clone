package com.thk.todo_clone.model

/**
 * 화면에서 SnackBar를 표시하기 위해 사용하는 클래스
 *
 * @param message SnackBar에 표시될 내용
 * @param actionLabel SnackBar의 label에 표시될 String
 * @param action SnackBar의 actionLabel을 클릭했을 때 동작 할 lambda
 */
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
