package com.webianks.lazypizza.data

import kotlinx.serialization.Serializable

/** A modifier choice group (e.g., "Toppings"). */
@Serializable
data class ModifierGroup(
    val id: String,
    val title: String,
    val min: Int = 0,
    val max: Int = 0,
    val options: List<ModifierOption>
)