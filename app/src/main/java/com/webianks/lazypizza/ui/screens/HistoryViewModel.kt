package com.webianks.lazypizza.ui.screens

import com.webianks.lazypizza.data.OrderSummary
import com.webianks.lazypizza.data.repository.OrdersRepository
import kotlinx.coroutines.flow.Flow

class HistoryViewModel(private val orders: OrdersRepository) {
    val history: Flow<List<OrderSummary>> = orders.history()
}