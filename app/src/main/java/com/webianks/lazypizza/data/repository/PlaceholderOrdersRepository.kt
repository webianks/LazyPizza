package com.webianks.lazypizza.data.repository

import com.webianks.lazypizza.data.OrderSummary
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class PlaceholderOrdersRepository : OrdersRepository {
    override fun history(): Flow<List<OrderSummary>> = flowOf(emptyList())
}