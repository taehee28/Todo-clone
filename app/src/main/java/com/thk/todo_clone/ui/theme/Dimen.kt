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
    val priorityIndicatorSize: Dp = 16.dp
) {
    fun copy(
        smallPadding: Dp = this.smallPadding,
        mediumPadding: Dp = this.mediumPadding,
        largePadding: Dp = this.largePadding,
        priorityIndicatorSize: Dp = this.priorityIndicatorSize
    ): Dimensions = Dimensions(
        smallPadding = smallPadding,
        mediumPadding = mediumPadding,
        largePadding = largePadding,
        priorityIndicatorSize = priorityIndicatorSize
    )

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Dimensions) return false

        if (smallPadding != other.smallPadding) return false
        if (mediumPadding != other.mediumPadding) return false
        if (largePadding != other.largePadding) return false
        if (priorityIndicatorSize != other.priorityIndicatorSize) return false

        return true
    }

    override fun hashCode(): Int {
        var result = smallPadding.hashCode()
        result = 31 * result + mediumPadding.hashCode()
        result = 31 * result + largePadding.hashCode()
        result = 31 * result + priorityIndicatorSize.hashCode()
        return result
    }

    override fun toString(): String {
        return "Dimensions(" +
                "smallPadding=$smallPadding, " +
                "mediumPadding=$mediumPadding, " +
                "largePadding=$largePadding, " +
                "priorityIndicatorSize=$priorityIndicatorSize" +
                ")"
    }
}

internal val LocalDimensions = staticCompositionLocalOf { Dimensions() }