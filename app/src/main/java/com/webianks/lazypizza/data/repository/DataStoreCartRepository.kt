package com.webianks.lazypizza.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.webianks.lazypizza.data.CartLine
import com.webianks.lazypizza.data.MenuItem
import com.webianks.lazypizza.data.Money
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass
import kotlin.random.Random

class DataStoreCartRepository(
    private val dataStore: DataStore<Preferences>,
    private val scope: CoroutineScope,
) : CartRepository {

    private val KEY = stringPreferencesKey("cart")

    private val json = Json {
        serializersModule = SerializersModule {
            polymorphic(MenuItem::class) {
                subclass(MenuItem.Simple::class)
                subclass(MenuItem.Configurable::class)
            }
        }
    }

    override val lines: StateFlow<List<CartLine>> = dataStore.data.map { preferences ->
        val jsonString = preferences[KEY]
        if (jsonString == null) emptyList()
        else {
            json.decodeFromString<List<CartLine>>(jsonString)
        }
    }.stateIn(scope, SharingStarted.Eagerly, emptyList())

    override fun add(line: CartLine) {
        scope.launch {
            dataStore.edit {
                val current = json.decodeFromString<List<CartLine>>(it[KEY] ?: "[]")
                val key = line.identityKey()
                val existing = current.find { it.identityKey() == key }
                val newLine = if (existing == null) current + line else current.map {
                    if (it.identityKey() == key) it.copy(quantity = it.quantity + line.quantity) else it
                }
                it[KEY] = json.encodeToString(newLine)
            }
        }
    }

    override fun remove(identityKey: String) {
        scope.launch {
            dataStore.edit {
                val current = json.decodeFromString<List<CartLine>>(it[KEY] ?: "[]")
                val updated = current.filterNot { it.identityKey() == identityKey }
                it[KEY] = json.encodeToString(updated)
            }
        }
    }

    override fun updateQuantity(identityKey: String, quantity: Int) {
        if (quantity < 1) {
            remove(identityKey); return
        }
        scope.launch {
            dataStore.edit {
                val current = json.decodeFromString<List<CartLine>>(it[KEY] ?: "[]")
                val updated = current.map { if (it.identityKey() == identityKey) it.copy(quantity = quantity) else it }
                it[KEY] = json.encodeToString(updated)
            }
        }
    }

    override fun clear() {
        scope.launch {
            dataStore.edit { it.remove(KEY) }
        }
    }

    override fun total(): Flow<Money> =
        lines.map { list -> Money(list.sumOf { it.lineTotal().amount }) }

    override fun recommendedAddOns(
        all: List<MenuItem>,
        categories: Set<String>
    ): Flow<List<MenuItem.Simple>> {
        val pool = all.filterIsInstance<MenuItem.Simple>()
            .filter { p -> categories.any { c -> p.category.equals(c, true) } }
            .shuffled(Random(1L))

        return lines.map { cart ->
            val inCartIds = cart.map { it.item.id }.toSet()
            pool.filterNot { it.id in inCartIds }.take(10)
        }
    }
}