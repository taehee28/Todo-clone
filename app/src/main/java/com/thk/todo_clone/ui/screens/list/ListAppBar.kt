package com.thk.todo_clone.ui.screens.list

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import com.thk.data.models.Priority
import com.thk.todo_clone.ui.theme.topAppBarBackgroundColor
import com.thk.todo_clone.ui.theme.topAppBarContentColor
import com.thk.todo_clone.R
import com.thk.todo_clone.ui.components.DisplayAlertDialog
import com.thk.todo_clone.ui.components.PriorityItem
import com.thk.todo_clone.ui.theme.TodoTheme
import com.thk.todo_clone.ui.theme.Typography
import com.thk.todo_clone.model.SearchAppBarState
import com.thk.todo_clone.model.UIEvent

@Composable
fun ListAppBar(
    searchAppBarState: SearchAppBarState,
    onEvent: (UIEvent) -> Unit
) {
    when (searchAppBarState) {
        SearchAppBarState.CLOSED -> {
            DefaultListAppBar(
                onSearchClicked = { onEvent(UIEvent.OpenSearch) },
                onSortClicked = { onEvent(UIEvent.SortChanged(it)) },
                onDeleteAllClicked = { onEvent(UIEvent.DeleteAllTasks) }
            )
        }
        else -> {
            SearchAppBar(
                onCloseClicked = { onEvent(UIEvent.CloseSearch) },
                onSearchClicked = { onEvent(UIEvent.SearchTasks(it)) }
            )
        }
    }
}

@Composable
private fun DefaultListAppBar(
    onSearchClicked: () -> Unit,
    onSortClicked: (Priority) -> Unit,
    onDeleteAllClicked: () -> Unit
) {
    TopAppBar(
        title = {
            Text(
                text = stringResource(id = R.string.list_screen_title),
                color = MaterialTheme.colors.topAppBarContentColor
            )
        },
        actions = {
            ListAppbarActions(
                onSearchClicked = onSearchClicked,
                onSortClicked = onSortClicked,
                onDeleteAllClicked = onDeleteAllClicked
            )
        },
        backgroundColor = MaterialTheme.colors.topAppBarBackgroundColor
    )
}


@Composable
@Preview
private fun DefaultListAppBarPreview() {
    DefaultListAppBar(
        {},
        {},
        {}
    )
}

@Composable
private fun ListAppbarActions(
    onSearchClicked: () -> Unit,
    onSortClicked: (Priority) -> Unit,
    onDeleteAllClicked: () -> Unit
) {
    var openDialog by remember { mutableStateOf(false) }
    
    DisplayAlertDialog(
        title = stringResource(id = R.string.delete_all_tasks),
        message = stringResource(id = R.string.delete_all_tasks_confirmation),
        openDialog = openDialog,
        onCloseListener = { openDialog = false },
        onYesClicked = { onDeleteAllClicked() }
    )
    
    SearchAction(onSearchClicked = onSearchClicked)
    SortAction(onSortClicked = onSortClicked)
    DeleteAllAction(onDeleteAllClicked = { openDialog = true })
}

@Composable
private fun SearchAction(
    onSearchClicked: () -> Unit
) {
    IconButton(onClick = onSearchClicked) {
        Icon(
            imageVector = Icons.Filled.Search,
            contentDescription = stringResource(id = R.string.search_action),
            tint = MaterialTheme.colors.topAppBarContentColor
        )
    }
}

@Composable
private fun SortAction(
    onSortClicked: (Priority) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    IconButton(onClick = { expanded = true }) {
        Icon(
            painter = painterResource(id = R.drawable.ic_filter_list),
            contentDescription = stringResource(id = R.string.sort_action),
            tint = MaterialTheme.colors.topAppBarContentColor
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            Priority
                .values()
                .slice(setOf(0, 2, 3))
                .forEach { priority ->
                    DropdownMenuItem(
                        onClick = {
                            expanded = false
                            onSortClicked(priority)
                        }
                    ) {
                        PriorityItem(priority = priority)
                    }
                }
        }
    }
}

@Composable
private fun DeleteAllAction(
    onDeleteAllClicked: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    IconButton(onClick = { expanded = true }) {
        Icon(
            painter = painterResource(id = R.drawable.ic_more_vert),
            contentDescription = stringResource(id = R.string.delete_all_action),
            tint = MaterialTheme.colors.topAppBarContentColor
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                onClick = {
                    expanded = false
                    onDeleteAllClicked()
                }
            ) {
                Text(
                    text = stringResource(id = R.string.delete_all_action),
                    style = Typography.subtitle2,
                    modifier = Modifier.padding(start = TodoTheme.dimens.largePadding)
                )
            }
        }
    }
}

/**
 * state
 */
@Composable
private fun SearchAppBar(
    onCloseClicked: () -> Unit,
    onSearchClicked: (String) -> Unit
) {
    val (text, setText) = remember { mutableStateOf("") }

    SearchAppBar(
        text = text,
        onTextChange = setText,
        onCloseClicked = onCloseClicked,
        onSearchClicked = onSearchClicked
    )
}

/**
 * stateless
 */
@Composable
private fun SearchAppBar(
    text: String,
    onTextChange: (String) -> Unit,
    onCloseClicked: () -> Unit,
    onSearchClicked: (String) -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(TodoTheme.dimens.topAppBarHeight),
        elevation = AppBarDefaults.TopAppBarElevation,
        color = MaterialTheme.colors.topAppBarBackgroundColor
    ) {
        TextField(
            value = text,
            onValueChange = onTextChange,
            modifier = Modifier.fillMaxWidth(),
            placeholder = {
                Text(
                    text = stringResource(id = R.string.search_placeholder),
                    color = Color.White,
                    modifier = Modifier.alpha(ContentAlpha.medium)
                )
            },
            textStyle = TextStyle(
                color = MaterialTheme.colors.topAppBarContentColor,
                fontSize = MaterialTheme.typography.subtitle1.fontSize
            ),
            singleLine = true,
            leadingIcon = {
                IconButton(
                    onClick = {
                        if (text.isNotBlank()) onSearchClicked(text)
                    },
                    modifier = Modifier.alpha(ContentAlpha.disabled)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = stringResource(id = R.string.search_icon),
                        tint = MaterialTheme.colors.topAppBarContentColor
                    )
                }
            },
            trailingIcon = {
                IconButton(
                    onClick = {
                        if (text.isNotEmpty()) {
                            onTextChange("")
                        } else {
                            onCloseClicked()
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = stringResource(id = R.string.close_icon),
                        tint = MaterialTheme.colors.topAppBarContentColor
                    )
                }
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = { onSearchClicked(text) }
            ),
            colors = TextFieldDefaults.textFieldColors(
                cursorColor = MaterialTheme.colors.topAppBarContentColor,
                focusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                backgroundColor = Color.Transparent
            )
        )
    }
}

@Composable
@Preview
fun SearchAppBarPreview() {
    SearchAppBar(
        text = "",
        onTextChange = {},
        onCloseClicked = { /*TODO*/ },
        onSearchClicked = {}
    )
}