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

    private val _snackBarState = MutableStateFlow<SnackBarState?>(null)
    val snackBarState
        get() = _snackBarState.asStateFlow()

    fun onEvent(event: UIEvent) {
        logd(">> onEvent = $event")
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
                viewModelScope.launch {
                    toDoRepository.changeSort(event.sort)
                }
            }
            is UIEvent.SwipeToDeleteTask -> {
                _selectedTaskState.value = event.task.toToDoTaskState()
                deleteTask()
                emitSnackBarState(
                    SnackBarState.Delete(
                        title = _selectedTaskState.value.title,
                        block = {
                            // lazyColumn에서 key로 id를 사용하고 있기 때문에
                            // id를 그대로 다시 insert하면 swipe해서 dismiss된 item이 그대로 살아남
                            _selectedTaskState.update { it.copy(id = 0) }
                            addTask()
                        }
                    )
                )
            }
            is UIEvent.AddTask -> {
                addTask()
                _searchAppBarState.value = SearchAppBarState.CLOSED
                emitSnackBarState(SnackBarState.Add(_selectedTaskState.value.title))
            }
            is UIEvent.UpdateTask -> {
                updateTask()
                emitSnackBarState(SnackBarState.Update(_selectedTaskState.value.title))
            }
            is UIEvent.DeleteTask -> {
                deleteTask()
                emitSnackBarState(
                    SnackBarState.Delete(
                        title = _selectedTaskState.value.title,
                        block = {
                            _selectedTaskState.update { it.copy(id = 0) }
                            addTask()
                        }
                    )
                )
            }
            is UIEvent.DeleteAllTasks -> {
                deleteAllTasks()
                emitSnackBarState(SnackBarState.DeleteAll)
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
            is UIEvent.SnackBarDismissed -> {
                _snackBarState.value = null
            }
        }
    }

    private fun getSelectedTask(taskId: Int) = viewModelScope.launch {
        // collect를 하면 선택한 Task를 삭제했을 때 null이 collect된다 -> undo 불가능
        // 최초 한개만 collect하고 flow를 닫아서 null이 넘어오지 않게 함
        val task = toDoRepository.getSelectedTask(taskId).first()
        _selectedTaskState.value = task?.toToDoTaskState() ?: ToDoTaskState()
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

    private fun emitSnackBarState(state: SnackBarState) = viewModelScope.launch {
        _snackBarState.emit(state)
    }
}