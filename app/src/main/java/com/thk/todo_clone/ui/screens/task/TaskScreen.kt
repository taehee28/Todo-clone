package com.thk.todo_clone.ui.screens.task

import android.widget.Toast
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.thk.todo_clone.ui.viewmodel.SharedViewModel
import com.thk.todo_clone.util.Action

@Composable
fun TaskScreen(
    sharedViewModel: SharedViewModel,
    navigateToListScreen: (Action) -> Unit
) {
    val selectedTask by sharedViewModel.selectedTask.collectAsState()

    val title by sharedViewModel.title
    val description by sharedViewModel.description
    val priority by sharedViewModel.priority

    val context = LocalContext.current

    Scaffold(
        topBar = {
            TaskAppBar(
                selectedTask = selectedTask,
                navigateToListScreen = { action ->
                    when (action) {
                        Action.NO_ACTION -> navigateToListScreen(action)
                        else -> {
                            if (sharedViewModel.validateFields()) {
                                navigateToListScreen(action)
                            } else {
                                Toast.makeText(
                                    context,
                                    "Empty fields",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                }
            )
        },
        content = { paddingValues ->
            TaskContent(
                modifier = Modifier.padding(bottom = paddingValues.calculateBottomPadding()),
                title = title,
                onTitleChange = {
                    sharedViewModel.updateTitle(it)
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