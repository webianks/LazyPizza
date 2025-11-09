package com.webianks.lazypizza.data

import kotlinx.serialization.Serializable

/**
 * Cart item supports both simple and configurable menu items.
 */
@Serializable
data class CartLine(
    val item: MenuItem,
    val quantity: Int,
    val selections: List<SelectedModifier> = emptyList(),
) {
    init { require(quantity >= 1) }

    fun unitTotal(): Money = when (item) {
        is MenuItem.Simple -> item.basePrice
        is MenuItem.Configurable -> {
            val extras = selections.sumOf { it.subtotal().amount }
            Money(item.basePrice.amount + extras)
        }
    }

    fun lineTotal(): Money = unitTotal() * quantity

    /** identity so same base item with different selections make distinct entries */
    fun identityKey(): String = buildString {
        append(item.id)
        if (selections.isNotEmpty()) {
            append("#mods:")
            selections.sortedWith(compareBy({ it.groupId }, { it.option.id }))
                .forEach { append(it.groupId).append("/").append(it.option.id).append("x").append(it.quantity).append(",") }
        }
    }
}