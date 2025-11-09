package com.webianks.lazypizza.ui.components

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.webianks.lazypizza.R
import com.webianks.lazypizza.data.MenuItem
import com.webianks.lazypizza.data.Money
import com.webianks.lazypizza.ui.screens.sampleTopping
import com.webianks.lazypizza.ui.screens.simpleItem
import com.webianks.lazypizza.ui.theme.AppTextStyles
import com.webianks.lazypizza.ui.theme.LazyPizzaTheme

@SuppressLint("DefaultLocale")
@Composable
fun PizzaItemCard(
    modifier: Modifier = Modifier,
    menuItem: MenuItem.Configurable,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = modifier
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
        onClick = onClick,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.surfaceVariant),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(menuItem.imageUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = menuItem.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(120.dp)
                    .background(color = MaterialTheme.colorScheme.inverseSurface),
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                Text(
                    text = menuItem.name,
                    style = AppTextStyles.Body1Medium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                menuItem.description?.let {
                    Text(
                        text = it,
                        style = AppTextStyles.Body3Regular,
                        color = MaterialTheme.colorScheme.secondary,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = menuItem.basePrice.format(),
                    style = AppTextStyles.Title1SemiBold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}

@Composable
fun OtherItemCard(
    modifier: Modifier = Modifier,
    menuItem: MenuItem.Simple,
    quantity: Int,
    minQuantity: Int,
    onAddToCart: () -> Unit,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit,
    onRemove: () -> Unit,
) {
    val basePrice = menuItem.basePrice

    Card(
        modifier = modifier
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
        Box {
            Row(modifier = Modifier.height(IntrinsicSize.Min)) {
                Box(
                    modifier = Modifier
                        .width(if (minQuantity == 1) 106.dp else 120.dp)
                        .fillMaxHeight()
                        .background(color = MaterialTheme.colorScheme.inverseSurface),
                    contentAlignment = Alignment.Center
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(menuItem.imageUrl)
                            .crossfade(true)
                            .build(),
                        contentDescription = menuItem.name,
                        modifier = Modifier
                            .size(if (minQuantity == 1) 88.dp else 108.dp) // 1 -> Cart
                            .background(color = MaterialTheme.colorScheme.inverseSurface)
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(vertical = 16.dp)
                        .height(88.dp),
                    verticalArrangement = Arrangement.SpaceBetween,
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            modifier = Modifier.padding(end = 16.dp),
                            text = menuItem.name,
                            style = AppTextStyles.Body1Medium,
                            color = MaterialTheme.colorScheme.onSurface
                        )

                        if (quantity > 0) {
                            OutlinedIconButton(
                                modifier = Modifier
                                    .size(22.dp),
                                shape = RoundedCornerShape(8.dp),
                                border = BorderStroke(
                                    1.dp,
                                    MaterialTheme.colorScheme.outlineVariant
                                ),
                                onClick = onRemove
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.ic_trash),
                                    modifier = Modifier.size(14.dp),
                                    contentDescription = "Delete",
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        if (quantity == 0) {
                            Text(
                                text = basePrice.format(),
                                style = AppTextStyles.Title1SemiBold,
                                color = MaterialTheme.colorScheme.onSurface
                            )

                            PrimaryOutlineButton(
                                modifier = Modifier.height(40.dp),
                                text = "Add to Cart",
                                onClick = onAddToCart
                            )

                        } else {
                            QuantityStepper(
                                quantity = quantity,
                                onIncrement = onIncrement,
                                onDecrement = onDecrement,
                                minQuantity = minQuantity
                            )
                            Column(horizontalAlignment = Alignment.End) {
                                val totalPrice = basePrice.times(quantity)
                                Text(
                                    text = totalPrice.format(),
                                    style = AppTextStyles.Title1SemiBold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "$quantity x ${basePrice.format()}",
                                    style = AppTextStyles.Body4Regular,
                                    color = MaterialTheme.colorScheme.secondary,
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun QuantityStepper(
    quantity: Int,
    minQuantity: Int,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit,
    modifier: Modifier = Modifier,
    maxQuantity: Int = Int.MAX_VALUE
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedIconButton(
            modifier = Modifier.size(22.dp),
            onClick = onDecrement,
            enabled = quantity > minQuantity,
            shape = RoundedCornerShape(8.dp),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
        ) {
            Icon(
                Icons.Default.Remove,
                modifier = Modifier.size(14.dp),
                tint = if (quantity > minQuantity) MaterialTheme.colorScheme.secondary
                else MaterialTheme.colorScheme.outline,
                contentDescription = "Remove"
            )
        }
        Text(
            text = quantity.toString(),
            style = AppTextStyles.Title2,
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
        OutlinedIconButton(
            modifier = Modifier.size(22.dp),
            onClick = onIncrement, enabled = quantity < maxQuantity,
            shape = RoundedCornerShape(8.dp),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
        ) {
            Icon(
                Icons.Default.Add,
                modifier = Modifier.size(14.dp),
                tint = if (quantity < maxQuantity) MaterialTheme.colorScheme.secondary
                else MaterialTheme.colorScheme.outline,
                contentDescription = "Add"
            )
        }
    }
}

@Composable
fun ToppingCard(
    modifier: Modifier = Modifier,
    topping: MenuItem.Simple,
    isSelected: Boolean,
    quantity: Int,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit,
    onClick: () -> Unit,
) {
    Card(
        modifier = modifier
            .dropShadow(
                shape = RoundedCornerShape(12.dp),
                shadow = Shadow(
                    radius = if (isSelected) 6.dp else 16.dp,
                    spread = 0.dp,
                    offset = DpOffset(0.dp, 4.dp),
                    alpha = if (isSelected) 0.10f else 0.04f,
                    color = if (isSelected) MaterialTheme.colorScheme.primary
                    else MaterialTheme.colorScheme.scrim
                )
            )
            .border(
                width = 1.dp,
                color = if (isSelected) MaterialTheme.colorScheme.primary
                else MaterialTheme.colorScheme.outline,
                shape = RoundedCornerShape(12.dp),
            ),
        onClick = onClick,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .background(
                        color = MaterialTheme.colorScheme.primaryContainer,
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(topping.imageUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = topping.name,
                    modifier = Modifier.size(56.dp),
                    contentScale = ContentScale.Crop,
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = topping.name,
                style = AppTextStyles.Body3Regular,
                color = MaterialTheme.colorScheme.secondary
            )
            if (isSelected) {
                QuantityStepper(
                    modifier = modifier.padding(top = 8.dp),
                    quantity = quantity,
                    onIncrement = onIncrement,
                    onDecrement = onDecrement,
                    minQuantity = 0,
                    maxQuantity = 3
                )
            } else {
                Text(
                    modifier = Modifier.padding(top = 8.dp),
                    text = topping.basePrice.format(),
                    style = AppTextStyles.Title2,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}

@Composable
fun RecommendationCard(
    modifier: Modifier = Modifier,
    menuItem: MenuItem,
    onAdd: () -> Unit,
) {
    Card(
        modifier = modifier
            .width(160.dp)
            .height(202.dp)
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
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(menuItem.imageUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = menuItem.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .background(color = MaterialTheme.colorScheme.inverseSurface)
            )

            Column(modifier = Modifier.padding(12.dp)) {
                Text(
                    text = menuItem.name,
                    style = AppTextStyles.Body1Regular,
                    color = MaterialTheme.colorScheme.secondary
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = menuItem.basePrice.format(),
                        style = AppTextStyles.Title1SemiBold,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    OutlinedIconButton(
                        modifier = Modifier.size(22.dp),
                        onClick = onAdd,
                        shape = RoundedCornerShape(8.dp),
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
                    ) {
                        Icon(
                            Icons.Default.Add,
                            modifier = Modifier.size(14.dp),
                            tint = MaterialTheme.colorScheme.primary,
                            contentDescription = "Add"
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MenuItemCardPreview() {
    LazyPizzaTheme {
        PizzaItemCard(
            menuItem = MenuItem.Configurable(
                id = "1",
                name = "Margherita",
                description = "Tomato sauce, mozzarella, fresh basil, olive oil",
                basePrice = Money(8.99),
                imageUrl = "", // Empty for preview, or I can use a placeholder URL.
                category = "Pizza",
                groups = emptyList()
            ),
            onClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun OtherItemCardPreview() {
    LazyPizzaTheme {
        OtherItemCard(
            menuItem = simpleItem,
            quantity = 1,
            minQuantity = 0,
            onAddToCart = {},
            onIncrement = {},
            onDecrement = {},
            onRemove = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ToppingCardPreview() {
    LazyPizzaTheme {
        Row {
            ToppingCard(
                topping = sampleTopping,
                isSelected = false,
                quantity = 1,
                onIncrement = {},
                onDecrement = {},
                onClick = {},
            )
            Spacer(modifier = Modifier.width(16.dp))
            ToppingCard(
                topping = sampleTopping,
                isSelected = true,
                quantity = 1,
                onIncrement = {},
                onDecrement = {},
                onClick = { },
            )
            Spacer(modifier = Modifier.width(16.dp))
            ToppingCard(
                topping = sampleTopping,
                isSelected = true,
                quantity = 3,
                onIncrement = {},
                onDecrement = {},
                onClick = {},
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun QuantityStepperPreview() {
    LazyPizzaTheme {
        QuantityStepper(
            quantity = 1,
            minQuantity = 0,
            onIncrement = {},
            onDecrement = {})
    }
}

@Preview(showBackground = true)
@Composable
fun RecommendationCardPreview() {
    LazyPizzaTheme {
        RecommendationCard(
            menuItem = simpleItem,
            onAdd = {})
    }
}