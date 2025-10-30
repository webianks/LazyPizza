package com.webianks.lazypizza.data.repository

import com.webianks.lazypizza.data.OrderSummary
import kotlinx.coroutines.flow.Flow

interface OrdersRepository {
    fun history(): Flow<List<OrderSummary>>
}