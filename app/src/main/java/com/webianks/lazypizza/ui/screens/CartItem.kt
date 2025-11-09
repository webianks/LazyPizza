package com.webianks.lazypizza.ui.screens

data class CartItem(
    val id: String,
    val name: String,
    val image: String,
    val price: Double,
    var quantity: Int,
    var category: String,
    val toppings: List<String> = emptyList()
)