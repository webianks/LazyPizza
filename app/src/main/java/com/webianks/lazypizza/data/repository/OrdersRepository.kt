package com.webianks.lazypizza.data.repository

import com.webianks.lazypizza.data.Order
import kotlinx.coroutines.flow.Flow

interface OrdersRepository {
    fun getOrderHistory(): Flow<List<Order>>
}