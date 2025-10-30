package com.webianks.lazypizza.data

import android.annotation.SuppressLint
import kotlinx.serialization.Serializable

@Serializable
data class Money(val amount: Double) {
    @SuppressLint("DefaultLocale")
    fun format(): String = "$" + String.format("%.2f", amount)
    operator fun plus(other: Money) = Money(amount + other.amount)
    operator fun times(multi: Int) = Money(amount * multi)
}