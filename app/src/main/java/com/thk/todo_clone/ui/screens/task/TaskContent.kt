package com.thk.todo_clone.ui.screens.task

import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.thk.data.models.Priority
import com.thk.todo_clone.R
import com.thk.todo_clone.ui.components.PriorityDropDown
import com.thk.todo_clone.ui.theme.TodoTheme
import com.thk.todo_clone.util.UIEvent

@Composable
fun TaskContent(
    title: String,
    hasTitleError: Boolean,
    description: String,
    hasDescriptionError: Boolean,
    priority: Priority,
    onEvent: (UIEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(all = TodoTheme.dimens.largePadding)
    ) {
        OutlinedTextField(
            value = title,
            onValueChange = {
                if (it.length <= 20) onEvent(UIEvent.TitleChanged(it))
            },
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = stringResource(id = R.string.title)) },
            textStyle = MaterialTheme.typography.body1,
            singleLine = true,
            isError = hasTitleError
        )
        Spacer(
            modifier = Modifier.height(TodoTheme.dimens.mediumPadding)
        )
        PriorityDropDown(
            priority = priority,
            onPrioritySelected = { onEvent(UIEvent.PriorityChanged(it)) }
        )
        OutlinedTextField(
            value = description,
            onValueChange = { onEvent(UIEvent.DescriptionChanged(it)) },
            modifier = Modifier.fillMaxSize(),
            label = { Text(text = stringResource(id = R.string.description)) },
            textStyle = MaterialTheme.typography.body1,
            isError = hasDescriptionError
        )
    }
}

@Composable
fun TaskContent(
    title: String,
    onTitleChange: (String) -> Unit,
    description: String,
    onDescriptionChange: (String) -> Unit,
    priority: Priority,
    onPriorityChange: (Priority) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(all = TodoTheme.dimens.largePadding)
    ) {
        OutlinedTextField(
            value = title,
            onValueChange = onTitleChange,
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = stringResource(id = R.string.title)) },
            textStyle = MaterialTheme.typography.body1,
            singleLine = true
        )
        Spacer(
            modifier = Modifier.height(TodoTheme.dimens.mediumPadding)
        )
        PriorityDropDown(
            priority = priority,
            onPrioritySelected = onPriorityChange
        )
        OutlinedTextField(
            value = description,
            onValueChange = onDescriptionChange,
            modifier = Modifier.fillMaxSize(),
            label = { Text(text = stringResource(id = R.string.description)) },
            textStyle = MaterialTheme.typography.body1
        )
    }
}

@Composable
@Preview
fun TaskContentPreview() {
    TaskContent(
        title = "this is title",
        onTitleChange = {},
        description = "this is description",
        onDescriptionChange = {},
        priority = Priority.LOW,
        onPriorityChange = {}
    )
}