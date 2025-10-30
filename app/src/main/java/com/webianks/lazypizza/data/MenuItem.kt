package com.webianks.lazypizza.data

import kotlinx.serialization.Serializable

typealias Category = String // fully server-driven

/** A menu item that can be simple (no modifiers) or configurable (with modifier groups). */
@Serializable
sealed class MenuItem {
    abstract val id: String
    abstract val name: String
    abstract val imageUrl: String
    abstract val category: Category
    abstract val basePrice: Money

    /**
     * Simple line item (e.g., drink, sauce, ice-cream, sides, salads, etc.).
     */
    @Serializable
    data class Simple(
        override val id: String,
        override val name: String,
        override val imageUrl: String,
        override val category: Category,
        override val basePrice: Money,
    ) : MenuItem()

    /**
     * Configurable item using server-driven modifier groups (e.g., pizza with toppings).
     * Each group has independent min/max.
     */
    @Serializable
    data class Configurable(
        override val id: String,
        override val name: String,
        override val imageUrl: String,
        override val category: Category,
        override val basePrice: Money,
        val groups: List<ModifierGroup>, // e.g., [Toppings(min=1,max=3,...)]
        val description: String? = null,  // e.g., ingredients
    ) : MenuItem()
}