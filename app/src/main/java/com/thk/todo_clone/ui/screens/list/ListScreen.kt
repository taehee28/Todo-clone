package com.thk.todo_clone.ui.screens.list

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.thk.todo_clone.R
import com.thk.todo_clone.ui.theme.fabBackgroundColor
import com.thk.todo_clone.ui.viewmodel.SharedViewModel
import com.thk.todo_clone.util.Action
import kotlinx.coroutines.launch

@Composable
fun ListScreen(
    navigateToTaskScreen: (Int) -> Unit,
    sharedViewModel: SharedViewModel
) {
    val searchAppBarState by sharedViewModel.searchAppBarState
    val searchTextState by sharedViewModel.searchTextState
    val allTasks by sharedViewModel.tasks.collectAsState()
    val searchedTasks by sharedViewModel.searchedTask.collectAsState()
    val action by sharedViewModel.action
    val sortState by sharedViewModel.sortState.collectAsState()
    val lowPriorityTasks by sharedViewModel.lowPriorityTasks.collectAsState()
    val highPriorityTasks by sharedViewModel.highPriorityTasks.collectAsState()

    val scaffoldState = rememberScaffoldState()

    DisplaySnackBar(
        scaffoldState = scaffoldState,
        handleDatabaseActions = { sharedViewModel.handleDatabaseActions(action) },
        onUndoClicked = {
            sharedViewModel.action.value = it
        },
        taskTitle = sharedViewModel.title.value,
        action = action
    )

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            ListAppBar(
                searchAppBarState = searchAppBarState,
                setAppBarState = { sharedViewModel.searchAppBarState.value = it },
                searchTextState = searchTextState,
                setSearchTextState = { sharedViewModel.searchTextState.value = it },
                searchDatabase = sharedViewModel::searchDatabase,
                setAction = { sharedViewModel.action.value = it },
                persistSortState = sharedViewModel::persistSortState
            )
        },
        floatingActionButton = { ListFab(onFabClicked = navigateToTaskScreen) },
        content = { paddingValues ->
            ListContent(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = paddingValues.calculateBottomPadding()),
                allTasks = allTasks,
                searchedTasks = searchedTasks,
                lowPriorityTasks = lowPriorityTasks,
                highPriorityTasks = highPriorityTasks,
                sortState = sortState,
                searchAppBarState = searchAppBarState,
                onSwipeToDelete = { action, task ->
                    sharedViewModel.action.value = action
                    sharedViewModel.updateTaskFields(selectedTask = task)
                },
                navigateToTaskScreen = navigateToTaskScreen
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
    handleDatabaseActions: () -> Unit,
    onUndoClicked: (Action) -> Unit,
    taskTitle: String,
    action: Action
) {
    handleDatabaseActions()

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

/*
@Composable
@Preview
private fun ListScreenPreview() {
    ListScreen(navigateToTaskScreen = {})
}*/
