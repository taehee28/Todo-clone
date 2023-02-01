package com.thk.todo_clone.ui.screens.list

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.thk.data.models.ToDoTask
import com.thk.todo_clone.R
import com.thk.todo_clone.ui.theme.fabBackgroundColor
import com.thk.todo_clone.ui.viewmodel.SharedViewModel
import com.thk.todo_clone.ui.viewmodel.ToDoViewModel
import com.thk.todo_clone.util.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * state
 */
@Composable
fun ListScreen(
    navigateToTaskScreen: (Int) -> Unit,
    toDoViewModel: ToDoViewModel
) {
    val taskList by toDoViewModel.taskList.collectAsState()
    val searchedTaskList by toDoViewModel.searchedTaskList.collectAsState()
    val searchAppBarState by toDoViewModel.searchAppBarState
    val snackBarState by toDoViewModel.snackBarState.collectAsState()
    
    val scaffoldState = rememberScaffoldState()
    
    LaunchedEffect(key1 = snackBarState) {
        snackBarState?.also {
            val snackBarResult = scaffoldState.snackbarHostState.showSnackbar(
                message = it.message,
                actionLabel = it.actionLabel
            )

            if (snackBarResult == SnackbarResult.ActionPerformed) {
                it.action?.invoke()
            }

            toDoViewModel.onEvent(UIEvent.SnackBarDismissed)
        }
    }

    ListScreen(
        taskList = taskList,
        searchedTaskList = searchedTaskList,
        searchAppBarState = searchAppBarState,
        scaffoldState = scaffoldState,
        navigateToTaskScreen = navigateToTaskScreen,
        onEvent = toDoViewModel::onEvent
    )
}

/**
 * stateless
 */
@Composable
private fun ListScreen(
    taskList: RequestState<List<ToDoTask>>,
    searchedTaskList: RequestState<List<ToDoTask>>,
    searchAppBarState: SearchAppBarState,
    scaffoldState: ScaffoldState,
    navigateToTaskScreen: (Int) -> Unit,
    onEvent: (UIEvent) -> Unit
) {
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            ListAppBar(
                searchAppBarState = searchAppBarState,
                onEvent = onEvent
            )
        },
        floatingActionButton = { ListFab(onFabClicked = navigateToTaskScreen) },
        content = { paddingValues ->
            ListContent(
                taskList = taskList,
                searchedTaskList = searchedTaskList,
                searchAppBarState = searchAppBarState,
                onSwipeToDelete = onEvent,
                navigateToTaskScreen = navigateToTaskScreen,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = paddingValues.calculateBottomPadding())
            )
        }
    )
}

@Composable
private fun ListFab(
    onFabClicked: (Int) -> Unit
) = FloatingActionButton(
    onClick = { onFabClicked(-1) },
    backgroundColor = MaterialTheme.colors.fabBackgroundColor
) {
    Icon(
        imageVector = Icons.Filled.Add,
        contentDescription = stringResource(id = R.string.add_button),
        tint = Color.White
    )
}

@Composable
fun DisplaySnackBar(
    scaffoldState: ScaffoldState,
    onComplete: (Action) -> Unit,
    onUndoClicked: (Action) -> Unit,
    taskTitle: String,
    action: Action
) {
    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = action) {
        if (action != Action.NO_ACTION) {
            scope.launch {
                val snackBarResult = scaffoldState.snackbarHostState.showSnackbar(
                    message = setMessage(action, taskTitle),
                    actionLabel = if (action == Action.DELETE) "UNDO" else "OK"
                )

                undoDeleteTask(
                    action = action,
                    snackBarResult = snackBarResult,
                    onUndoClicked = onUndoClicked
                )
            }

            onComplete(Action.NO_ACTION)
        }
    }
}

private fun setMessage(
    action: Action,
    taskTitle: String
): String = when(action) {
    Action.DELETE_ALL -> "All Tasks Removed."
    else -> "${action.name}: $taskTitle"
}

private fun undoDeleteTask(
    action: Action,
    snackBarResult: SnackbarResult,
    onUndoClicked: (Action) -> Unit
) {
    if (snackBarResult == SnackbarResult.ActionPerformed &&
            action == Action.DELETE) {
        onUndoClicked(Action.UNDO)
    }
}
