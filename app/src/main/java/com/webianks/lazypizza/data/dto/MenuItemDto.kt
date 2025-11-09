package com.webianks.lazypizza.data.dto

import com.google.firebase.firestore.DocumentId

/**
 * type: "simple" | "configurable"
 * category: any string, server-defined ("pizza","drink","sauce", or future ones)
 */
data class MenuItemDto(
    @DocumentId val id: String = "",
    val name: String = "",
    val imageUrl: String = "",
    val category: String = "",
    val price: Double = 0.0,
    val type: String = "simple",
    val description: String? = null,
    val groups: List<ModifierGroupDto>? = null,
    val sortIndex: Int? = null,
)