package com.webianks.lazypizza.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.webianks.lazypizza.data.Order
import com.webianks.lazypizza.data.OrderItem
import com.webianks.lazypizza.ui.theme.AppTextStyles
import com.webianks.lazypizza.ui.theme.Success
import com.webianks.lazypizza.ui.theme.Warning

@Composable
fun OrderHistoryCard(order: Order) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .dropShadow(
                shape = RoundedCornerShape(12.dp),
                shadow = Shadow(
                    radius = 16.dp,
                    spread = 0.dp,
                    offset = DpOffset(0.dp, 4.dp),
                    alpha = 0.06f,
                    color = MaterialTheme.colorScheme.scrim
                )
            ),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.surfaceVariant),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Order #${order.orderId}",
                    style = AppTextStyles.Title3,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
                OrderStatus(status = order.status)
            }
            Text(
                text = order.date,
                style = AppTextStyles.Body4Regular,
                color = MaterialTheme.colorScheme.secondary
            )
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                Column {
                    order.items.forEach {
                        Text(
                            text = "${it.quantity} x ${it.name}",
                            style = AppTextStyles.Body4Regular,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                }
                Column(
                    horizontalAlignment = Alignment.End,
                    verticalArrangement = Arrangement.Bottom
                ) {
                    Text(
                        "Total amount:",
                        style = AppTextStyles.Body4Regular,
                        color = MaterialTheme.colorScheme.secondary
                    )
                    Text(
                        text = "$${order.totalAmount}",
                        style = AppTextStyles.Title3,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }
        }
    }
}

@Composable
fun OrderStatus(status: String) {
    val backgroundColor = when (status) {
        "In Progress" -> Warning
        "Completed" -> Success
        "Canceled" -> Color.Red
        else -> Color.Gray
    }
    Text(
        text = status,
        color = MaterialTheme.colorScheme.onPrimary,
        modifier = Modifier
            .clip(RoundedCornerShape(100.dp))
            .background(backgroundColor)
            .padding(horizontal = 8.dp, vertical = 4.dp),
        style = AppTextStyles.Label3Medium
    )
}

@Preview
@Composable
fun OrderHistoryCardPreview() {
    OrderHistoryCard(
        Order(
            orderId = "12347",
            date = "September 25, 12:15",
            items = listOf(OrderItem(name = "Margherita", quantity = 1)),
            status = "In Progress",
            totalAmount = 8.99
        )
    )
}
