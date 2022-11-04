package com.thk.todo_clone.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable

object TodoTheme {
    val dimens: Dimensions
        @Composable
        @ReadOnlyComposable
        get() = LocalDimensions.current
}