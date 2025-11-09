package com.webianks.lazypizza.data.repository

import com.webianks.lazypizza.data.CartLine
import com.webianks.lazypizza.data.MenuItem
import com.webianks.lazypizza.data.Money
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlin.random.Random

class InMemoryCartRepository(
    private val scope: CoroutineScope,
) : CartRepository {
    private val _lines = MutableStateFlow<List<CartLine>>(emptyList())
    override val lines: StateFlow<List<CartLine>> = _lines.asStateFlow()

    override fun add(line: CartLine) {
        _lines.update { cur ->
            val key = line.identityKey()
            val existing = cur.find { it.identityKey() == key }
            if (existing == null) cur + line else cur.map {
                if (it.identityKey() == key) it.copy(quantity = it.quantity + line.quantity) else it
            }
        }
    }

    override fun remove(identityKey: String) {
        _lines.update { cur -> cur.filterNot { it.identityKey() == identityKey } }
    }

    override fun updateQuantity(identityKey: String, quantity: Int) {
        if (quantity < 1) {
            remove(identityKey); return
        }
        _lines.update { cur -> cur.map { if (it.identityKey() == identityKey) it.copy(quantity = quantity) else it } }
    }

    override fun clear() {
        _lines.value = emptyList()
    }

    override fun total(): Flow<Money> =
        lines.map { list -> Money(list.sumOf { it.lineTotal().amount }) }

    override fun recommendedAddOns(
        all: List<MenuItem>,
        categories: Set<String>
    ): Flow<List<MenuItem.Simple>> {
        val pool = all.filterIsInstance<MenuItem.Simple>()
            .filter { p -> categories.any { c -> p.category.equals(c, true) } }
            .shuffled(Random(System.currentTimeMillis()))

        return lines.map { cart ->
            val inCartIds = cart.map { it.item.id }.toSet()
            pool.filterNot { it.id in inCartIds }.take(10)
        }
    }
}