package com.thk.todo_clone.ui.screens.task

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.thk.data.models.Priority
import com.thk.todo_clone.ui.viewmodel.SharedViewModel
import com.thk.todo_clone.ui.viewmodel.ToDoViewModel
import com.thk.todo_clone.util.Action
import com.thk.todo_clone.util.UIEvent

@Composable
fun TaskScreen(
    toDoViewModel: ToDoViewModel,
    navigateToListScreen: (Action) -> Unit
) {
    val selectedTask by toDoViewModel.selectedTaskState.collectAsState()
    val buttonEnabled by remember {
        derivedStateOf { selectedTask.run { !(hasTitleError||hasDescriptionError) } }
    }

    Scaffold(
        topBar = {
           TaskAppBar(
               selectedTask = selectedTask,
               buttonEnabledProvider = { buttonEnabled },
               onBackClicked = { navigateToListScreen(Action.NO_ACTION) },
               onAddClicked = {
                   toDoViewModel.onEvent(UIEvent.AddTask)
                   navigateToListScreen(Action.NO_ACTION)
               },
               onUpdateClicked = {
                   toDoViewModel.onEvent(UIEvent.UpdateTask)
                   navigateToListScreen(Action.NO_ACTION)
               },
               onDeleteClicked = {
                   toDoViewModel.onEvent(UIEvent.DeleteTask)
                   navigateToListScreen(Action.NO_ACTION)
               }
           )
        },
        content = { paddingValues ->
            TaskContent(
                title = selectedTask.title,
                hasTitleError = selectedTask.hasTitleError,
                description = selectedTask.description,
                hasDescriptionError = selectedTask.hasDescriptionError,
                priority = selectedTask.priority,
                onEvent = toDoViewModel::onEvent,
                modifier = Modifier.padding(bottom = paddingValues.calculateBottomPadding())
            )
        }
    )
}

/*
@Composable
fun BackHandler(
    backDispatcher: OnBackPressedDispatcher? = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher,
    onBackPressed: () -> Unit
) {
    // onBackPressed 람다가 바뀌면,
    // currentOnBackPressed state의 값을 새로운 onBackPressed 람다로 업데이트 함
    val currentOnBackPressed by rememberUpdatedState(newValue = onBackPressed)

    val backCallback = remember {
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                currentOnBackPressed()
            }
        }
    }

    // 처음엔 초기화 로직만 호출됨
    DisposableEffect(key1 = backDispatcher) {
        // 키가 바뀌면 onDispose가 실행되고, 여기가 실행 됨
        backDispatcher?.addCallback(backCallback)

        // 키가 바뀌거나, 해당 컴포저블이 dispose되면 실행됨
        onDispose { backCallback.remove() }
    }
}
*/
