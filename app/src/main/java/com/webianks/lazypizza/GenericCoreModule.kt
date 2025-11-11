package com.webianks.lazypizza

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.webianks.lazypizza.data.repository.CartRepository
import com.webianks.lazypizza.data.repository.DataStoreCartRepository
import com.webianks.lazypizza.data.repository.FirestoreMenuRepository
import com.webianks.lazypizza.data.repository.MenuRepository
import com.webianks.lazypizza.data.repository.OrdersRepository
import com.webianks.lazypizza.data.repository.PlaceholderOrdersRepository
import com.webianks.lazypizza.ui.screens.AuthViewModel
import com.webianks.lazypizza.ui.screens.CartViewModel
import com.webianks.lazypizza.ui.screens.HistoryViewModel
import com.webianks.lazypizza.ui.screens.ItemDetailsViewModel
import com.webianks.lazypizza.ui.screens.MenuViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

object GenericCoreModule {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "cart")

    // Use a single CoroutineScope for all repository instances
    private val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    private val menuRepo: MenuRepository by lazy { FirestoreMenuRepository() }
    private fun cartRepo(context: Context): CartRepository {
        return DataStoreCartRepository(context.dataStore, applicationScope, FirebaseAuth.getInstance())
    }
    private val ordersRepo: OrdersRepository by lazy { PlaceholderOrdersRepository() }

    fun factory(context: Context): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            val cart = cartRepo(context)
            return when {
                modelClass.isAssignableFrom(MenuViewModel::class.java) ->
                    MenuViewModel(menuRepo, cart) as T
                modelClass.isAssignableFrom(ItemDetailsViewModel::class.java) ->
                    ItemDetailsViewModel(menuRepo, cart) as T
                modelClass.isAssignableFrom(CartViewModel::class.java) ->
                    CartViewModel(cart, menuRepo) as T
                modelClass.isAssignableFrom(HistoryViewModel::class.java) ->
                    HistoryViewModel(ordersRepo) as T
                else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        }
    }

    class AuthViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
                val cartRepository = cartRepo(context) as DataStoreCartRepository
                return AuthViewModel(FirebaseAuth.getInstance(), cartRepository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}