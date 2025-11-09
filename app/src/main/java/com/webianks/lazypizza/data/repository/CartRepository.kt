package com.webianks.lazypizza.data.repository

import com.webianks.lazypizza.data.CartLine
import com.webianks.lazypizza.data.MenuItem
import com.webianks.lazypizza.data.Money
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface CartRepository {
    val lines: StateFlow<List<CartLine>>
    fun add(line: CartLine)
    fun remove(identityKey: String)
    fun updateQuantity(identityKey: String, quantity: Int)
    fun clear()
    fun total(): Flow<Money>

    /**
     * Compute recommended add-ons by filtering menu items using server-provided categories.
     */
    fun recommendedAddOns(all: List<MenuItem>, categories: Set<String>): Flow<List<MenuItem.Simple>>
}