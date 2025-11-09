package com.webianks.lazypizza.data

data class OrderSummary(
    val id: String,
    val createdAtMillis: Long,
    val total: Money
)