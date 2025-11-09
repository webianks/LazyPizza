package com.webianks.lazypizza.data.repository

import com.webianks.lazypizza.data.CartLine
import com.webianks.lazypizza.data.MenuItem
import com.webianks.lazypizza.data.Money
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class FakeCartRepository : CartRepository {
    private val _lines = MutableStateFlow<List<CartLine>>(emptyList())

    override val lines: StateFlow<List<CartLine>>
        get() = _lines

    override fun add(line: CartLine) {
        TODO("Not yet implemented")
    }

    override fun remove(identityKey: String) {
        TODO("Not yet implemented")
    }

    override fun updateQuantity(identityKey: String, quantity: Int) {
        TODO("Not yet implemented")
    }

    override fun clear() {
        TODO("Not yet implemented")
    }

    override fun total(): Flow<Money> {
        TODO("Not yet implemented")
    }

    override fun recommendedAddOns(
        all: List<MenuItem>,
        categories: Set<String>
    ): Flow<List<MenuItem.Simple>> {
        TODO("Not yet implemented")
    }
}
