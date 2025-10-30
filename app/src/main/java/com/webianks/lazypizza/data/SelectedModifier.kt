package com.webianks.lazypizza.data

import kotlinx.serialization.Serializable

/** A selection of one option with a quantity (for max>1 scenarios). */
@Serializable
data class SelectedModifier(
    val groupId: String,
    val option: ModifierOption,
    val quantity: Int = 1,
) {
    init { require(quantity >= 0) }
    fun subtotal(): Money = option.price * quantity
}