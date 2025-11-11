package com.webianks.lazypizza.data

data class Order(
    val orderId: String,
    val date: String,
    val items: List<OrderItem>,
    val status: String,
    val totalAmount: Double
)

data class OrderItem(
    val name: String,
    val quantity: Int
)