package com.webianks.lazypizza.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.webianks.lazypizza.screens.MenuItem
import com.webianks.lazypizza.screens.Topping
import com.webianks.lazypizza.screens.sampleMenu
import com.webianks.lazypizza.screens.sampleToppings
import com.webianks.lazypizza.ui.theme.AppTextStyles
import com.webianks.lazypizza.ui.theme.LazyPizzaTheme

@Composable
fun PizzaItemCard(
    modifier: Modifier = Modifier,
    menuItem: MenuItem,
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
                Text(
                    text = menuItem.description,
                    style = AppTextStyles.Body3Regular,
                    color = MaterialTheme.colorScheme.secondary,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = menuItem.price,
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
    menuItem: MenuItem,
) {
    var quantity by remember { mutableIntStateOf(0) }
    val basePrice = remember(menuItem.price) {
        menuItem.price.removePrefix("$").toDoubleOrNull() ?: 0.0
    }

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
            Row {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(menuItem.imageUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = menuItem.name,
                    modifier = Modifier
                        .size(120.dp)
                        .background(color = MaterialTheme.colorScheme.inverseSurface)
                )
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
                                onClick = { quantity = 0 }
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
                                text = menuItem.price,
                                style = AppTextStyles.Title1SemiBold,
                                color = MaterialTheme.colorScheme.onSurface
                            )

                            PrimaryOutlineButton(
                                modifier = Modifier.height(40.dp),
                                text = "Add to Cart",
                                onClick = { quantity = 1 }
                            )

                        } else {
                            QuantityStepper(
                                quantity = quantity,
                                onIncrement = { quantity++ },
                                onDecrement = {
                                    if (quantity > 1) quantity-- else quantity = 0
                                }
                            )
                            Column(horizontalAlignment = Alignment.End) {
                                val totalPrice = basePrice * quantity
                                Text(
                                    text = "$${String.format("%.2f", totalPrice)}",
                                    style = AppTextStyles.Title1SemiBold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "$quantity x ${menuItem.price}",
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
            enabled = quantity > 0,
            shape = RoundedCornerShape(8.dp),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
        ) {
            Icon(
                Icons.Default.Remove,
                modifier = Modifier.size(14.dp),
                tint = if (quantity > 0) MaterialTheme.colorScheme.secondary
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
    topping: Topping,
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
                    maxQuantity = 3
                )
            } else {
                Text(
                    modifier = Modifier.padding(top = 8.dp),
                    text = topping.price,
                    style = AppTextStyles.Title2,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MenuItemCardPreview() {
    LazyPizzaTheme {
        PizzaItemCard(
            menuItem = MenuItem(
                id = "1",
                name = "Margherita",
                description = "Tomato sauce, mozzarella, fresh basil, olive oil",
                price = "$8.99",
                imageUrl = "", // Empty for preview, or I can use a placeholder URL.
                category = "Pizza"
            ),
            onClick = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
fun OtherItemCardPreview() {
    LazyPizzaTheme {
        OtherItemCard(
            menuItem = sampleMenu.first { it.category != "Pizza" }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ToppingCardPreview() {
    LazyPizzaTheme {
        Row {
            ToppingCard(
                topping = sampleToppings.first(),
                isSelected = false,
                quantity = 1,
                onIncrement = {},
                onDecrement = {},
                onClick = {},
            )
            Spacer(modifier = Modifier.width(16.dp))
            ToppingCard(
                topping = sampleToppings.first(),
                isSelected = true,
                quantity = 1,
                onIncrement = {},
                onDecrement = {},
                onClick = {},
            )
            Spacer(modifier = Modifier.width(16.dp))
            ToppingCard(
                topping = sampleToppings.first(),
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
fun QuanityStepperPreview() {
    LazyPizzaTheme {
        QuantityStepper(quantity = 1, onIncrement = {}, onDecrement = {})
    }
}

/*@Composable
fun SauceCard(
    modifier: Modifier = Modifier,
    imagePainter: Painter,
    name: String,
    price: String,
    onAdd: () -> Unit,
) {
    Card(
        modifier = modifier.width(130.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Image(
                painter = imagePainter,
                contentDescription = name,
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop,
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = name, style = MaterialTheme.typography.bodyLarge)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = price,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                )
                IconButton(onClick = onAdd) {
                    Icon(Icons.Default.Add, contentDescription = "Add", tint = Primary)
                }
            }
        }
    }
}*/