package com.webianks.lazypizza.ui.screens

import androidx.annotation.DrawableRes
import com.webianks.lazypizza.R

enum class Destination(
    val route: String,
    val label: String,
    @DrawableRes val icon: Int,
    val contentDescription: String
) {
    MENU("menu", "Menu", R.drawable.ic_menu, "Menu"),
    CART("cart", "Cart", R.drawable.ic_cart, "Cart"),
    HISTORY("history", "History", R.drawable.ic_history, "History")
}
