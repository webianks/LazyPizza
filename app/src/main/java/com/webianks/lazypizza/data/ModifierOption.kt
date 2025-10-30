package com.webianks.lazypizza.data

import kotlinx.serialization.Serializable

/** A single selectable option in a group (e.g., "Bacon" +$1.00). */
@Serializable
data class ModifierOption(
    val id: String,
    val name: String,
    val price: Money,
    val imageUrl: String
)