package com.thk.data.models

enum class Priority(val color: Long) {
    HIGH(0xFFFF4646),
    MEDIUM(0xFFFFC114),
    LOW(0xFF00C980),
    NONE(0xFFFFFFFF),
}