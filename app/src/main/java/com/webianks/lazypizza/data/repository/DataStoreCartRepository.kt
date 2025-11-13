package com.webianks.lazypizza.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.google.firebase.auth.FirebaseAuth
import com.webianks.lazypizza.data.CartLine
import com.webianks.lazypizza.data.MenuItem
import com.webianks.lazypizza.data.Money
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
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
    private val firebaseAuth: FirebaseAuth
) : CartRepository {

    private val GUEST_CART_KEY = "guest_cart"

    private val json = Json {
        serializersModule = SerializersModule {
            polymorphic(MenuItem::class) {
                subclass(MenuItem.Simple::class)
                subclass(MenuItem.Configurable::class)
            }
        }
    }

    private val cartKey: Flow<String> = firebaseAuth.authStateFlow().map { user ->
        user?.uid ?: GUEST_CART_KEY
    }

    override val lines: StateFlow<List<CartLine>> = cartKey.flatMapLatest { key ->
        dataStore.data.map { preferences ->
            val jsonString = preferences[stringPreferencesKey(key)]
            if (jsonString == null) emptyList()
            else {
                json.decodeFromString<List<CartLine>>(jsonString)
            }
        }
    }.stateIn(scope, SharingStarted.Eagerly, emptyList())

    private suspend fun getCurrentKey() = firebaseAuth.currentUser?.uid ?: GUEST_CART_KEY

    override fun add(line: CartLine) {
        scope.launch {
            val key = getCurrentKey()
            dataStore.edit {
                val prefKey = stringPreferencesKey(key)
                val current = json.decodeFromString<List<CartLine>>(it[prefKey] ?: "[]")
                val identityKey = line.identityKey()
                val existing = current.find { it.identityKey() == identityKey }
                val newLine = if (existing == null) current + line else current.map {
                    if (it.identityKey() == identityKey) it.copy(quantity = it.quantity + line.quantity) else it
                }
                it[prefKey] = json.encodeToString(newLine)
            }
        }
    }

    override fun remove(identityKey: String) {
        scope.launch {
            val key = getCurrentKey()
            dataStore.edit {
                val prefKey = stringPreferencesKey(key)
                val current = json.decodeFromString<List<CartLine>>(it[prefKey] ?: "[]")
                val updated = current.filterNot { it.identityKey() == identityKey }
                it[prefKey] = json.encodeToString(updated)
            }
        }
    }

    override fun updateQuantity(identityKey: String, quantity: Int) {
        if (quantity < 1) {
            remove(identityKey); return
        }
        scope.launch {
            val key = getCurrentKey()
            dataStore.edit {
                val prefKey = stringPreferencesKey(key)
                val current = json.decodeFromString<List<CartLine>>(it[prefKey] ?: "[]")
                val updated = current.map { if (it.identityKey() == identityKey) it.copy(quantity = quantity) else it }
                it[prefKey] = json.encodeToString(updated)
            }
        }
    }

    override fun clear() {
        scope.launch {
            val key = getCurrentKey()
            dataStore.edit { it.remove(stringPreferencesKey(key)) }
        }
    }

    suspend fun transferGuestCartToUser(uid: String) {
        val guestCartJson = dataStore.data.first()[stringPreferencesKey(GUEST_CART_KEY)]
        if (!guestCartJson.isNullOrEmpty()) {
            val guestCart = json.decodeFromString<List<CartLine>>(guestCartJson)
            if (guestCart.isNotEmpty()) {
                dataStore.edit {
                    it[stringPreferencesKey(uid)] = json.encodeToString(guestCart)
                    it.remove(stringPreferencesKey(GUEST_CART_KEY))
                }
            }
        }
    }

    suspend fun clearUserCart(uid: String) {
        dataStore.edit { it.remove(stringPreferencesKey(uid)) }
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

private fun FirebaseAuth.authStateFlow(): Flow<com.google.firebase.auth.FirebaseUser?> = kotlinx.coroutines.flow.callbackFlow {
    val listener = FirebaseAuth.AuthStateListener { auth ->
        trySend(auth.currentUser).isSuccess
    }
    addAuthStateListener(listener)
    awaitClose { removeAuthStateListener(listener) }
}