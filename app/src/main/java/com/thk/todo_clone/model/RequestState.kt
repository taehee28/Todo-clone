package com.thk.todo_clone.model

/**
 * Repository로의 요청 상태를 나타내는 클래스
 */
sealed class RequestState<out T> {
    object Idle : RequestState<Nothing>()
    object Loading : RequestState<Nothing>()
    data class Success<T>(val data: T) : RequestState<T>()
    data class Error(val error: Throwable) : RequestState<Nothing>()
}
