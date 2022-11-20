package com.thk.todo_clone.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

val Dimension = Dimensions()

@Immutable
class Dimensions(
    val smallPadding: Dp = 6.dp,
    val mediumPadding: Dp = 8.dp,
    val largePadding: Dp = 12.dp,
    val priorityIndicatorSize: Dp = 16.dp,
    val topAppBarHeight: Dp = 56.dp,
    val taskItemElevation: Dp = 2.dp,
    val priorityDropDownHeight: Dp = 60.dp
) {
    fun copy(
        smallPadding: Dp = this.smallPadding,
        mediumPadding: Dp = this.mediumPadding,
        largePadding: Dp = this.largePadding,
        priorityIndicatorSize: Dp = this.priorityIndicatorSize,
        topAppBarHeight: Dp = this.topAppBarHeight,
        taskItemElevation: Dp = this.taskItemElevation,
        priorityDropDownHeight: Dp = this.priorityDropDownHeight
    ): Dimensions = Dimensions(
        smallPadding = smallPadding,
        mediumPadding = mediumPadding,
        largePadding = largePadding,
        priorityIndicatorSize = priorityIndicatorSize,
        topAppBarHeight = topAppBarHeight,
        taskItemElevation = taskItemElevation,
        priorityDropDownHeight = priorityDropDownHeight
    )

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Dimensions) return false

        if (smallPadding != other.smallPadding) return false
        if (mediumPadding != other.mediumPadding) return false
        if (largePadding != other.largePadding) return false
        if (priorityIndicatorSize != other.priorityIndicatorSize) return false
        if (topAppBarHeight != other.topAppBarHeight) return false
        if (taskItemElevation != other.taskItemElevation) return false
        if (priorityDropDownHeight != other.priorityDropDownHeight) return false

        return true
    }

    override fun hashCode(): Int {
        var result = smallPadding.hashCode()
        result = 31 * result + mediumPadding.hashCode()
        result = 31 * result + largePadding.hashCode()
        result = 31 * result + priorityIndicatorSize.hashCode()
        result = 31 * result + topAppBarHeight.hashCode()
        result = 31 * result + taskItemElevation.hashCode()
        result = 31 * result + priorityDropDownHeight.hashCode()
        return result
    }

    override fun toString(): String {
        return "Dimensions(" +
                "smallPadding=$smallPadding, " +
                "mediumPadding=$mediumPadding, " +
                "largePadding=$largePadding, " +
                "priorityIndicatorSize=$priorityIndicatorSize," +
                "topAppBarHeight=$topAppBarHeight," +
                "taskItemElevation=$taskItemElevation," +
                "priorityDropDownHeight=$priorityDropDownHeight" +
                ")"
    }
}

internal val LocalDimensions = staticCompositionLocalOf { Dimensions() }