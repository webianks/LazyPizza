package com.webianks.lazypizza.data.repository

import com.webianks.lazypizza.data.Order
import com.webianks.lazypizza.data.OrderItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeOrdersRepository : OrdersRepository {

    override fun getOrderHistory(): Flow<List<Order>> {
        return flowOf(
            listOf(
                Order(
                    orderId = "12347",
                    date = "September 25, 12:15",
                    items = listOf(OrderItem(name = "Margherita", quantity = 1)),
                    status = "In Progress",
                    totalAmount = 8.99
                ),
                Order(
                    orderId = "12346",
                    date = "September 25, 12:15",
                    items = listOf(
                        OrderItem(name = "Margherita", quantity = 1),
                        OrderItem(name = "Pepsi", quantity = 2),
                        OrderItem(name = "Cookies Ice Cream", quantity = 2)
                    ),
                    status = "Completed",
                    totalAmount = 25.45
                ),
                Order(
                    orderId = "12345",
                    date = "September 25, 12:15",
                    items = listOf(
                        OrderItem(name = "Margherita", quantity = 1),
                        OrderItem(name = "Cookies Ice Cream", quantity = 2)
                    ),
                    status = "Completed",
                    totalAmount = 11.78
                ),
                 /*Order(
                    orderId = "12344",
                    date = "September 24, 10:05",
                    items = listOf(
                        OrderItem(name = "Margherita", quantity = 1),
                    ),
                    status = "Canceled",
                    totalAmount = 8.99
                )*/
            )
        )
    }
}
