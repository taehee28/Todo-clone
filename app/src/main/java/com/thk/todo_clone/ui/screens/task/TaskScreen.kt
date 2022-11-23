package com.thk.todo_clone.ui.screens.task

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.thk.todo_clone.ui.viewmodel.SharedViewModel
import com.thk.todo_clone.util.Action

@Composable
fun TaskScreen(
    sharedViewModel: SharedViewModel,
    navigateToListScreen: (Action) -> Unit
) {
    val selectedTask by sharedViewModel.selectedTask.collectAsState()

    LaunchedEffect(key1 = selectedTask) {
        sharedViewModel.updateTaskFields(selectedTask)
    }

    val title by sharedViewModel.title
    val description by sharedViewModel.description
    val priority by sharedViewModel.priority

    Scaffold(
        topBar = {
            TaskAppBar(
                selectedTask = selectedTask,
                navigateToListScreen = navigateToListScreen
            )
        },
        content = { paddingValues ->
            TaskContent(
                modifier = Modifier.padding(bottom = paddingValues.calculateBottomPadding()),
                title = title,
                onTitleChange = {
                    sharedViewModel.title.value = it
                },
                description = description,
                onDescriptionChange = {
                    sharedViewModel.description.value = it
                },
                priority = priority,
                onPriorityChange = {
                    sharedViewModel.priority.value = it
                }
            )
        }
    )
}