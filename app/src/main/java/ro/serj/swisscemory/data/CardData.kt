package ro.serj.swisscemory.data

import androidx.compose.ui.graphics.Color

data class CardData(
    val id: Int,
    val color: Color,
    var isFlipped: Boolean = false,
    var show: Boolean = true
)