package com.thk.todo_clone.ui.screens.list

import android.util.Log
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
    val tasks by sharedViewModel.tasks.collectAsState()
    val action by sharedViewModel.action

    val scaffoldState = rememberScaffoldState()

    DisplaySnackBar(
        scaffoldState = scaffoldState,
        handleDatabaseActions = { sharedViewModel.handleDatabaseActions(action) },
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
                setSearchTextState = { sharedViewModel.searchTextState.value = it }
            )
        },
        floatingActionButton = { ListFab(onFabClicked = navigateToTaskScreen) },
        content = { paddingValues ->
            ListContent(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = paddingValues.calculateBottomPadding()),
                tasks = tasks,
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
    taskTitle: String,
    action: Action
) {
    handleDatabaseActions()

    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = action) {
        if (action != Action.NO_ACTION) {
            scope.launch {
                val snackbarResult = scaffoldState.snackbarHostState.showSnackbar(
                    message = "${action.name}: $taskTitle",
                    actionLabel = "OK"
                )
            }
        }
    }
}

/*
@Composable
@Preview
private fun ListScreenPreview() {
    ListScreen(navigateToTaskScreen = {})
}*/
