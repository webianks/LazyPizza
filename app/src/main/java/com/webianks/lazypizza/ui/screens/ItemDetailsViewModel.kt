package com.webianks.lazypizza.ui.screens

import androidx.lifecycle.ViewModel
import com.webianks.lazypizza.data.CartLine
import com.webianks.lazypizza.data.MenuItem
import com.webianks.lazypizza.data.SelectedModifier
import com.webianks.lazypizza.data.repository.CartRepository
import com.webianks.lazypizza.data.repository.MenuRepository

class ItemDetailsViewModel(
    private val menu: MenuRepository,
    private val cart: CartRepository,
) : ViewModel() {
    suspend fun loadConfigurable(id: String): MenuItem.Configurable? =
        menu.getItem(id) as? MenuItem.Configurable

    fun addConfigurable(
        item: MenuItem.Configurable,
        baseQty: Int,
        selections: List<SelectedModifier>,
    ) {
        // Validation: enforce each group's min on the number of topping types
        item.groups.forEach { g ->
            val countInGroup = selections.filter { it.groupId == g.id }.size
            require(countInGroup >= g.min) {
                "Selection count for group ${g.title} must be at least ${g.min}"
            }
        }
        cart.add(CartLine(item = item, quantity = baseQty, selections = selections))
    }
}