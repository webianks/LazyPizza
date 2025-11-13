package com.webianks.lazypizza.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.webianks.lazypizza.data.Order
import com.webianks.lazypizza.data.repository.OrdersRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

data class HistoryUiState(
    val orders: List<Order> = emptyList(),
    val isLoading: Boolean = false,
)

class HistoryViewModel(private val ordersRepository: OrdersRepository) : ViewModel() {

    val uiState: StateFlow<HistoryUiState> =
        ordersRepository.getOrderHistory()
            .map { HistoryUiState(orders = it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = HistoryUiState(isLoading = true)
            )

}