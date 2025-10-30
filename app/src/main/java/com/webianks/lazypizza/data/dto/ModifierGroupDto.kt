package com.webianks.lazypizza.data.dto

data class ModifierGroupDto(
    val id: String = "",
    val title: String = "",
    val min: Int = 0,
    val max: Int = 0,
    val options: List<ModifierOptionDto> = emptyList()
)