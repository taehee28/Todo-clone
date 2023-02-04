package com.thk.todo_clone.ui.screens.task

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.thk.data.models.Priority
import com.thk.data.models.ToDoTask
import com.thk.todo_clone.ui.theme.topAppBarBackgroundColor
import com.thk.todo_clone.ui.theme.topAppBarContentColor
import com.thk.todo_clone.util.Action
import com.thk.todo_clone.R
import com.thk.todo_clone.ui.components.DisplayAlertDialog
import com.thk.todo_clone.util.ToDoTaskState
import com.thk.todo_clone.util.UIEvent

@Composable
fun TaskAppBar(
    selectedTask: ToDoTaskState,
    buttonEnabledProvider: () -> Boolean,
    onBackClicked: () -> Unit,
    onAddClicked: () -> Unit,
    onUpdateClicked: () -> Unit,
    onDeleteClicked: () -> Unit
) {
    if (selectedTask.id == 0) {     // id가 0인 task는 없음
        NewTaskAppBar(
            buttonEnabledProvider = buttonEnabledProvider,
            onAddClicked = onAddClicked,
            onBackClicked = onBackClicked
        )
    } else {
        ExistingTaskAppBar(
            selectedTask = selectedTask,
            buttonEnabledProvider = buttonEnabledProvider,
            onUpdateClicked = onUpdateClicked,
            onDeleteClicked = onDeleteClicked,
            onBackClicked = onBackClicked
        )
    }
}

@Composable
fun NewTaskAppBar(
    buttonEnabledProvider: () -> Boolean,
    onAddClicked: () -> Unit,
    onBackClicked: () -> Unit
) {
    TopAppBar(
        navigationIcon = {
            BackAction(onBackClicked = onBackClicked)
        },
        title = {
            Text(text = stringResource(id = R.string.add_task), color = MaterialTheme.colors.topAppBarContentColor)
        },
        backgroundColor = MaterialTheme.colors.topAppBarBackgroundColor,
        actions = {
            AddAction(
                enabled = buttonEnabledProvider(),
                onAddClicked = onAddClicked
            )
        }
    )
}

@Composable
fun BackAction(
    onBackClicked: () -> Unit
) = IconButton(onClick = { onBackClicked() }) {
    Icon(
        imageVector = Icons.Filled.ArrowBack,
        contentDescription = stringResource(id = R.string.back_arrow),
        tint = MaterialTheme.colors.topAppBarContentColor
    )
}

@Composable
fun AddAction(
    enabled: Boolean = true,
    onAddClicked: () -> Unit
) = IconButton(
    onClick = { onAddClicked() },
    enabled = enabled
) {
    Icon(
        imageVector = Icons.Filled.Check,
        contentDescription = stringResource(id = R.string.add_task),
        tint = MaterialTheme.colors.topAppBarContentColor,
        modifier = Modifier.alpha(if (enabled) ContentAlpha.high else ContentAlpha.disabled)
    )
}

@Composable
fun ExistingTaskAppBar(
    selectedTask: ToDoTaskState,
    buttonEnabledProvider: () -> Boolean,
    onUpdateClicked: () -> Unit,
    onDeleteClicked: () -> Unit,
    onBackClicked: () -> Unit
) {
    TopAppBar(
        navigationIcon = {
            CloseAction(onCloseClicked = onBackClicked)
        },
        title = {
            Text(
                text = selectedTask.title,
                color = MaterialTheme.colors.topAppBarContentColor,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        backgroundColor = MaterialTheme.colors.topAppBarBackgroundColor,
        actions = {
            ExistingTaskAppBarAction(
                selectedTask = selectedTask,
                buttonEnabledProvider = buttonEnabledProvider,
                onUpdateClicked = onUpdateClicked,
                onDeleteClicked = onDeleteClicked,
                onBackClicked = onBackClicked
            )
        }
    )
}

@Composable
fun ExistingTaskAppBarAction(
    selectedTask: ToDoTaskState,
    buttonEnabledProvider: () -> Boolean,
    onUpdateClicked: () -> Unit,
    onDeleteClicked: () -> Unit,
    onBackClicked: () -> Unit
) {
    var openDialog by remember { mutableStateOf(false) }

    DisplayAlertDialog(
        title = stringResource(id = R.string.delete_task, selectedTask.title),
        message = stringResource(id = R.string.delete_task_confirmation, selectedTask.title),
        openDialog = openDialog,
        onCloseListener = { openDialog = false },
        onYesClicked = {
            onDeleteClicked()
            onBackClicked()
        }
    )


    DeleteAction(onDeleteClicked = { openDialog = true })
    UpdateAction(
        enabled = buttonEnabledProvider(),
        onUpdateClicked = onUpdateClicked
    )
}



@Composable
fun CloseAction(
    onCloseClicked: () -> Unit
) = IconButton(onClick = { onCloseClicked() }) {
    Icon(
        imageVector = Icons.Filled.Close,
        contentDescription = stringResource(id = R.string.close_icon),
        tint = MaterialTheme.colors.topAppBarContentColor
    )
}

@Composable
fun DeleteAction(
    onDeleteClicked: () -> Unit
) = IconButton(onClick = { onDeleteClicked() }) {
    Icon(
        imageVector = Icons.Filled.Delete,
        contentDescription = stringResource(id = R.string.delete_icon),
        tint = MaterialTheme.colors.topAppBarContentColor
    )
}

@Composable
fun UpdateAction(
    enabled: Boolean = true,
    onUpdateClicked: () -> Unit
) = IconButton(
    onClick = { onUpdateClicked() },
    enabled = enabled
) {
    Icon(
        imageVector = Icons.Filled.Check,
        contentDescription = stringResource(id = R.string.update_icon),
        tint = MaterialTheme.colors.topAppBarContentColor,
        modifier = Modifier.alpha(if (enabled) ContentAlpha.high else ContentAlpha.disabled)
    )
}

@Composable
@Preview
fun NewTaskAppBarPreview() {
    NewTaskAppBar(
        buttonEnabledProvider = { true },
        onAddClicked = {},
        onBackClicked = {}
    )
}

@Composable
@Preview
fun ExistingTaskAppBarPreview() {
    ExistingTaskAppBar(
        selectedTask = ToDoTaskState(),
        buttonEnabledProvider = { true },
        onUpdateClicked = { /*TODO*/ },
        onDeleteClicked = { /*TODO*/ },
        onBackClicked = {}
    )
}
