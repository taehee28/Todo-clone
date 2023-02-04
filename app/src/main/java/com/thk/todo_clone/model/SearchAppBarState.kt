package com.thk.todo_clone.model

/**
 * Task 목록 화면의 SearchAppBar의 상태를 나타내는 enum
 */
enum class SearchAppBarState {
    /**
     * SearchAppBar가 화면에 나타난 상태
     */
    OPENED,

    /**
     * SearchAppBar가 화면에서 사라진 상태
     */
    CLOSED,

    /**
     * Task가 검색 된 상태
     */
    TRIGGERED
}