@file:OptIn(ExperimentalCoroutinesApi::class)

package com.thk.todo_clone.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thk.data.models.Priority
import com.thk.data.models.ToDoTask
import com.thk.data.repository.DataStoreRepository
import com.thk.data.repository.ToDoRepository
import com.thk.todo_clone.util.Action
import com.thk.todo_clone.util.RequestState
import com.thk.todo_clone.util.SearchAppBarState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val toDoRepository: ToDoRepository,
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {
    val tasks = toDoRepository
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
    private val _selectedTask = MutableStateFlow<ToDoTask?>(null)
    val selectedTask
        get() = _selectedTask.asStateFlow()

    private val _searchedTask = MutableStateFlow<RequestState<List<ToDoTask>>>(RequestState.Idle)
    val searchedTask
        get() = _searchedTask.asStateFlow()

    val lowPriorityTasks = toDoRepository.getTasksSortByLowPriority()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val highPriorityTasks = toDoRepository.getTasksSortByHighPriority()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val searchAppBarState = mutableStateOf(SearchAppBarState.CLOSED)
    val searchTextState = mutableStateOf("")

    val id = mutableStateOf(0)
    val title = mutableStateOf("")
    val description = mutableStateOf("")
    val priority = mutableStateOf(Priority.LOW)

    val action = mutableStateOf(Action.NO_ACTION)

    val sortState = dataStoreRepository
        .readSortState()
        .mapLatest {
            RequestState.Success(Priority.valueOf(it))
        }
        .catch {
            RequestState.Error(it)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = RequestState.Idle
        )

    fun getSelectedTask(taskId: Int) = viewModelScope.launch {
        Log.d("TAG", "getSelectedTask: taskId = $taskId")
        toDoRepository.getSelectedTask(taskId).collectLatest {
            Log.d("TAG", "getSelectedTask: $it")
            _selectedTask.value = it
        }
    }

    fun searchDatabase(searchQuery: String) {
        _searchedTask.value = RequestState.Loading
        viewModelScope.launch {
            toDoRepository
                .searchDatabase("%$searchQuery%")
                .catch { _searchedTask.value = RequestState.Error(it) }
                .collectLatest {
                    _searchedTask.value = RequestState.Success(it)
                }
        }
        searchAppBarState.value = SearchAppBarState.TRIGGERED
    }

    fun updateTaskFields(selectedTask: ToDoTask?) {
        if (selectedTask != null) {
            id.value = selectedTask.id
            title.value = selectedTask.title
            description.value = selectedTask.description
            priority.value = selectedTask.priority
        } else {
            id.value = 0
            title.value = ""
            description.value = ""
            priority.value = Priority.LOW
        }
    }

    fun validateFields(): Boolean =
        title.value.isNotBlank() and description.value.isNotBlank()

    fun updateTitle(value: String) {
        if (value.length <= 20) {
            title.value = value
        }
    }

    fun handleDatabaseActions(action: Action) {
        when (action) {
            Action.ADD, Action.UNDO -> addTask()
            Action.UPDATE -> updateTask()
            Action.DELETE -> deleteTask()
            Action.DELETE_ALL -> deleteAllTask()
        }
    }

    private fun addTask() = viewModelScope.launch(Dispatchers.IO) {
        val task = ToDoTask(
            title = title.value,
            description = description.value,
            priority = priority.value
        )

        toDoRepository.addTask(task)
        searchAppBarState.value = SearchAppBarState.CLOSED
    }

    private fun updateTask() = viewModelScope.launch(Dispatchers.IO) {
        val task = ToDoTask(
            id = id.value,
            title = title.value,
            description = description.value,
            priority = priority.value
        )

        toDoRepository.updateTask(task)
    }

    private fun deleteTask() = viewModelScope.launch(Dispatchers.IO) {
        val task = ToDoTask(
            id = id.value,
            title = title.value,
            description = description.value,
            priority = priority.value
        )

        toDoRepository.deleteTask(task)
    }

    private fun deleteAllTask() = viewModelScope.launch(Dispatchers.IO) {
        toDoRepository.deleteAllTasks()
    }

    fun persistSortState(priority: Priority) = viewModelScope.launch(Dispatchers.IO) {
        dataStoreRepository.persistSortState(priority = priority)
    }
}
