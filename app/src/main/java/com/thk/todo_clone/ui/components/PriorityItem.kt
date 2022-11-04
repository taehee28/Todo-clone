package com.thk.todo_clone.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.thk.data.models.Priority
import com.thk.todo_clone.ui.theme.TodoTheme
import com.thk.todo_clone.ui.theme.Typography
import com.thk.todo_clone.util.color

@Composable
fun PriorityItem(priority: Priority) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Canvas(modifier = Modifier.size(TodoTheme.dimens.priorityIndicatorSize)) {
            drawCircle(color = priority.color)
        }
        Text(
            text = priority.name,
            style = Typography.subtitle1,
            color = MaterialTheme.colors.onSurface,
            modifier = Modifier.padding(start = TodoTheme.dimens.largePadding)
        )
    }
}

@Composable
@Preview
private fun PriorityItemPreview() {
    Column {
        PriorityItem(priority = Priority.LOW)
        PriorityItem(priority = Priority.MEDIUM)
        PriorityItem(priority = Priority.HIGH)
    }
}