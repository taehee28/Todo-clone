package com.thk.todo_clone.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import com.thk.data.models.Priority
import com.thk.todo_clone.R
import com.thk.todo_clone.ui.theme.TodoTheme
import com.thk.todo_clone.util.color

@Composable
fun PriorityDropDown(
    priority: Priority,
    onPrioritySelected: (Priority) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val angle by animateFloatAsState(targetValue = if (expanded) 180f else 0f)
    var rowSize by remember { mutableStateOf(Size.Zero) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(TodoTheme.dimens.priorityDropDownHeight)
            .clickable { expanded = true }
            .border(
                width = 1.dp,
                color = MaterialTheme.colors.onSurface.copy(alpha = ContentAlpha.disabled),
                shape = MaterialTheme.shapes.small
            )
            .onGloballyPositioned { rowSize = it.size.toSize() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Canvas(
            modifier = Modifier
                .size(TodoTheme.dimens.priorityIndicatorSize)
                .weight(1f)
        ) {
            drawCircle(color = priority.color)
        }
        Text(
            text = priority.name,
            style = MaterialTheme.typography.subtitle2,
            modifier = Modifier.weight(9f)
        )
        IconButton(
            onClick = { expanded = true },
            modifier = Modifier
                .alpha(ContentAlpha.medium)
                .rotate(angle)
        ) {
            Icon(
                imageVector = Icons.Filled.ArrowDropDown,
                contentDescription = stringResource(id = R.string.drop_down_arrow)
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.width(with(LocalDensity.current) { rowSize.width.toDp() })
        ) {
            DropdownMenuItem(onClick = {
                expanded = false
                onPrioritySelected(Priority.LOW)
            }) {
                PriorityItem(priority = Priority.LOW)
            }

            DropdownMenuItem(onClick = {
                expanded = false
                onPrioritySelected(Priority.MEDIUM)
            }) {
                PriorityItem(priority = Priority.MEDIUM)
            }

            DropdownMenuItem(onClick = {
                expanded = false
                onPrioritySelected(Priority.HIGH)
            }) {
                PriorityItem(priority = Priority.HIGH)
            }
        }
    }
}

@Composable
@Preview
fun PriorityDropDownPreview() {
    PriorityDropDown(priority = Priority.LOW, onPrioritySelected = {})
}