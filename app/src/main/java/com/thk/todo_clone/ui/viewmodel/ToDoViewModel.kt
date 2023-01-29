package com.thk.todo_clone.ui.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thk.data.models.Priority
import com.thk.data.models.ToDoTask
import com.thk.data.repository.ToDoRepository
import com.thk.todo_clone.util.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ToDoViewModel @Inject constructor(
    private val toDoRepository: ToDoRepository
) : ViewModel() {
    val taskList = toDoRepository
        .getSortedTasks()
        .catch {
            RequestState.Error(it)
        }
        .mapLatest {
            RequestState.Success(it)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = RequestState.Idle
        )

    private val _searchedTaskList = MutableStateFlow<RequestState<List<ToDoTask>>>(RequestState.Idle)
    val searchedTaskList
        get() = _searchedTaskList.asStateFlow()

    private val _selectedTaskState = MutableStateFlow<ToDoTaskState>(ToDoTaskState())
    val selectedTaskState
        get() = _selectedTaskState.asStateFlow()

    private val _searchAppBarState = mutableStateOf(SearchAppBarState.CLOSED)
    val searchAppBarState: State<SearchAppBarState>
        get() = _searchAppBarState

    // TODO: Composable에 전달 할 SanckBar와 Toast 표시를 나타내는 이벤트 변수 정의

    fun onEvent(event: UIEvent) {
        when (event) {
            is UIEvent.SelectTask -> {
                getSelectedTask(event.id)
            }
            is UIEvent.TitleChanged -> {
                _selectedTaskState.update {
                    it.copy(
                        title = event.title,
                        hasTitleError = event.title.isBlank()
                    )
                }
            }
            is UIEvent.DescriptionChanged -> {
                _selectedTaskState.update {
                    it.copy(
                        description = event.description,
                        hasDescriptionError = event.description.isBlank()
                    )
                }
            }
            is UIEvent.PriorityChanged -> {
                _selectedTaskState.update {
                    it.copy(priority = event.priority)
                }
            }
            is UIEvent.SortChanged -> {
                // TODO: repository에 sort 저장하는 함수 만들어야 함
            }
            is UIEvent.SwipeToDeleteTask -> {
                _selectedTaskState.value = event.task.toToDoTaskState()
                deleteTask()
            }
            is UIEvent.AddTask -> {
                addTask()
                _searchAppBarState.value = SearchAppBarState.CLOSED
            }
            is UIEvent.UpdateTask -> {
                updateTask()
            }
            is UIEvent.DeleteTask -> {
                deleteTask()
            }
            is UIEvent.DeleteAllTasks -> {
                deleteAllTasks()
            }
            is UIEvent.Undo -> {}
            is UIEvent.SearchTasks -> {
                searchDatabase(event.keyword)
                _searchAppBarState.value = SearchAppBarState.TRIGGERED
            }
            is UIEvent.OpenSearch -> {
                _searchAppBarState.value = SearchAppBarState.OPENED
            }
            is UIEvent.CloseSearch -> {
                _searchAppBarState.value = SearchAppBarState.CLOSED
            }
        }
    }

    private fun getSelectedTask(taskId: Int) = viewModelScope.launch {
        toDoRepository.getSelectedTask(taskId).collectLatest {
            // TODO: -1이 오면 null? nullable로 바꾸기
            _selectedTaskState.value = if (it == null) {
                ToDoTaskState()
            } else {
                it.toToDoTaskState()
            }
        }
    }

    private fun addTask() = viewModelScope.launch {
        toDoRepository.addTask(_selectedTaskState.value.toToDoTask())
    }

    private fun updateTask() = viewModelScope.launch {
        toDoRepository.updateTask(_selectedTaskState.value.toToDoTask())
    }

    private fun deleteTask() = viewModelScope.launch {
        toDoRepository.deleteTask(_selectedTaskState.value.toToDoTask())
    }

    private fun deleteAllTasks() = viewModelScope.launch {
        toDoRepository.deleteAllTasks()
    }

    private fun searchDatabase(keyword: String) = viewModelScope.launch {
        toDoRepository
            .searchDatabase("%$keyword%")
            .onStart {
                _searchedTaskList.value = RequestState.Loading
            }
            .catch {
                _searchedTaskList.value = RequestState.Error(it)
            }
            .collectLatest {
                _searchedTaskList.value = RequestState.Success(it)
            }
    }
}