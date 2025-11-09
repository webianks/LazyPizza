package com.webianks.lazypizza.ui.screens

import androidx.lifecycle.ViewModel
import com.webianks.lazypizza.data.CartLine
import com.webianks.lazypizza.data.MenuItem
import com.webianks.lazypizza.data.Money
import com.webianks.lazypizza.data.repository.CartRepository
import com.webianks.lazypizza.data.repository.MenuRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest

class CartViewModel(
    private val cart: CartRepository,
    private val menu: MenuRepository,
) : ViewModel() {
    val lines: StateFlow<List<CartLine>> = cart.lines
    val total: Flow<Money> = cart.total()

    fun inc(identityKey: String, current: Int) = cart.updateQuantity(identityKey, current + 1)
    fun dec(identityKey: String, current: Int) = cart.updateQuantity(identityKey, current - 1)
    fun remove(identityKey: String) = cart.remove(identityKey)

    fun addSimple(item: MenuItem.Simple) = cart.add(CartLine(item = item, quantity = 1))

    @OptIn(ExperimentalCoroutinesApi::class)
    val addOns: Flow<List<MenuItem.Simple>> =
        combine(menu.items(), menu.recommendedCategories()) { all, cats ->
            all to cats
        }.flatMapLatest { (all, cats) -> cart.recommendedAddOns(all, cats) }
}
