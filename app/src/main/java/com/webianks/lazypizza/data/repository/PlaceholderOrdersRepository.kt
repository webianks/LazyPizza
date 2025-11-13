package com.webianks.lazypizza.data.repository

import com.webianks.lazypizza.data.Order
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class PlaceholderOrdersRepository : OrdersRepository {
    override fun getOrderHistory(): Flow<List<Order>> = flowOf(emptyList())
}
