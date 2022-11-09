package com.thk.todo_clone.ui.screens.list

import androidx.compose.foundation.layout.padding
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.thk.todo_clone.R
import com.thk.todo_clone.ui.theme.fabBackgroundColor
import com.thk.todo_clone.ui.viewmodel.SharedViewModel

@Composable
fun ListScreen(
    navigateToTaskScreen: (Int) -> Unit,
    sharedViewModel: SharedViewModel
) {
    val searchAppBarState by sharedViewModel.searchAppBarState
    val searchTextState by sharedViewModel.searchTextState

    Scaffold(
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
                modifier = Modifier.padding(bottom = paddingValues.calculateBottomPadding())
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

/*
@Composable
@Preview
private fun ListScreenPreview() {
    ListScreen(navigateToTaskScreen = {})
}*/
