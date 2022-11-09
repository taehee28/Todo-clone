@file:OptIn(ExperimentalCoroutinesApi::class)

package com.thk.todo_clone.ui.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thk.data.repository.ToDoRepository
import com.thk.todo_clone.util.RequestState
import com.thk.todo_clone.util.SearchAppBarState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val repository: ToDoRepository
) : ViewModel() {
    val tasks = repository
        .getAllTasks()
        .mapLatest {
            RequestState.Success(it)
        }
        .catch {
            RequestState.Error(it)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = RequestState.Idle
        )

    val searchAppBarState = mutableStateOf(SearchAppBarState.CLOSED)
    val searchTextState = mutableStateOf("")
}
