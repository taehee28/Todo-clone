package com.thk.todo_clone.ui.screens.task

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.thk.data.models.ToDoTask
import com.thk.todo_clone.util.Action
import kotlinx.coroutines.flow.StateFlow

@Composable
fun TaskScreen(
    selectedTaskFlow: StateFlow<ToDoTask?>,
    navigateToListScreen: (Action) -> Unit
) {
    val selectedTask by selectedTaskFlow.collectAsState()

    Scaffold(
        topBar = {
            TaskAppBar(
                selectedTask = selectedTask,
                navigateToListScreen = navigateToListScreen
            )
        },
        content = { it.toString() }
    )
}