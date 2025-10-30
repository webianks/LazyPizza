package com.webianks.lazypizza.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.webianks.lazypizza.data.CartLine
import com.webianks.lazypizza.data.Category
import com.webianks.lazypizza.data.MenuItem
import com.webianks.lazypizza.data.repository.CartRepository
import com.webianks.lazypizza.data.repository.MenuRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn

class MenuViewModel(
    private val menu: MenuRepository,
    private val cart: CartRepository,
) : ViewModel() {
    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    val all: StateFlow<List<MenuItem>> = menu.items()
        .onEach { _isLoading.value = false }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = emptyList()
        )

    fun search(query: String): Flow<List<MenuItem>> = menu.search(query)
    fun category(category: Category): Flow<List<MenuItem>> = menu.byCategory(category)

    fun addSimple(item: MenuItem.Simple) = cart.add(CartLine(item = item, quantity = 1))
}
