package com.webianks.lazypizza.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.webianks.lazypizza.R
import com.webianks.lazypizza.data.CartLine
import com.webianks.lazypizza.data.MenuItem
import com.webianks.lazypizza.data.Money
import com.webianks.lazypizza.ui.components.AddToCartBottomBar
import com.webianks.lazypizza.ui.components.OtherItemCard
import com.webianks.lazypizza.ui.components.PrimaryGradientButton
import com.webianks.lazypizza.ui.components.QuantityStepper
import com.webianks.lazypizza.ui.components.RecommendationCard
import com.webianks.lazypizza.ui.theme.AppTextStyles
import com.webianks.lazypizza.ui.theme.LazyPizzaTheme

@Composable
fun CartScreen(
    windowSizeClass: WindowSizeClass,
    viewModel: CartViewModel,
    onNavigateToMenu: () -> Unit,
) {
    val cartLines by viewModel.lines.collectAsState()
    val recommendedAddOns by viewModel.addOns.collectAsState(initial = emptyList())
    val totalPrice by viewModel.total.collectAsState(initial = Money(0.0))

    if (cartLines.isEmpty()) {
        EmptyCartState(onNavigateToMenu)
    } else {
        when (windowSizeClass.widthSizeClass) {
            WindowWidthSizeClass.Compact -> {
                SmallScreenLayout(
                    cartLines = cartLines,
                    recommendedAddOns = recommendedAddOns,
                    totalPrice = totalPrice,
                    viewModel = viewModel,
                )
            }

            else -> {
                LargeScreenLayout(
                    cartLines = cartLines,
                    recommendedAddOns = recommendedAddOns,
                    totalPrice = totalPrice,
                    viewModel = viewModel,
                )
            }
        }
    }
}

@Composable
fun LargeScreenLayout(
    cartLines: List<CartLine>,
    recommendedAddOns: List<MenuItem.Simple>,
    totalPrice: Money,
    viewModel: CartViewModel
) {
    Row(
        modifier = Modifier.fillMaxSize(),
    ) {
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .background(MaterialTheme.colorScheme.surface),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(cartLines, key = { it.identityKey() }) {
                CartItem(it, viewModel)
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
        Column(
            modifier = Modifier
                .weight(1f)
                .dropShadow(
                    shape = RoundedCornerShape(16.dp),
                    shadow = Shadow(
                        radius = 16.dp,
                        spread = 0.dp,
                        offset = DpOffset(0.dp, (-4).dp),
                        alpha = 0.04f,
                        color = MaterialTheme.colorScheme.scrim
                    )
                )
                .background(
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    shape = RoundedCornerShape(
                        topStart = 16.dp,
                        bottomStart = 16.dp
                    )
                )
        ) {
            RecommendedAddOns(
                recommendedAddOns = recommendedAddOns,
                onAddToCart = { menuItem ->
                    viewModel.addSimple(menuItem)
                }
            )
            Box(modifier = Modifier.padding(16.dp)) {
                PrimaryGradientButton(
                    buttonModifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    text = "Proceed to Checkout (${totalPrice.format()})",
                    onClick = { }
                )
            }
        }
    }
}

@Composable
fun SmallScreenLayout(
    cartLines: List<CartLine>,
    recommendedAddOns: List<MenuItem.Simple>,
    totalPrice: Money,
    viewModel: CartViewModel
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.surface),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                items(cartLines, key = { it.identityKey() }) { line ->
                    CartItem(line, viewModel)
                }

                item {
                    RecommendedAddOns(
                        recommendedAddOns = recommendedAddOns,
                        onAddToCart = { menuItem ->
                            viewModel.addSimple(menuItem)
                        }
                    )
                }

                item {
                    Spacer(modifier = Modifier.height(80.dp))
                }
            }
        }

        AddToCartBottomBar(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .clip(RoundedCornerShape(bottomStart = 16.dp)),
            buttonModifier = Modifier.height(48.dp),
            text = "Proceed to Checkout (${totalPrice.format()})",
            onClick = { }
        )
    }
}

@Composable
private fun CartItem(line: CartLine, viewModel: CartViewModel) {
    Box(modifier = Modifier.padding(horizontal = 16.dp)) {
        when (val item = line.item) {
            is MenuItem.Simple -> {
                OtherItemCard(
                    menuItem = item,
                    quantity = line.quantity,
                    onIncrement = {
                        viewModel.inc(
                            line.identityKey(),
                            line.quantity
                        )
                    },
                    onDecrement = {
                        viewModel.dec(
                            line.identityKey(),
                            line.quantity
                        )
                    },
                    minQuantity = 1,
                    onAddToCart = { },
                    onRemove = { viewModel.remove(line.identityKey()) }
                )
            }

            is MenuItem.Configurable -> {
                CartItemView(
                    item = line,
                    onIncrement = {
                        viewModel.inc(
                            line.identityKey(),
                            line.quantity
                        )
                    },
                    onDecrement = {
                        viewModel.dec(
                            line.identityKey(),
                            line.quantity
                        )
                    },
                    onRemoveItem = { viewModel.remove(line.identityKey()) }
                )
            }
        }
    }
}

@Composable
fun RecommendedAddOns(
    recommendedAddOns: List<MenuItem.Simple>,
    onAddToCart: (MenuItem.Simple) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
    ) {
        if (recommendedAddOns.isNotEmpty()) {
            Text(
                text = "RECOMMENDED TO ADD TO YOUR ORDER",
                style = AppTextStyles.Label2Semibold,
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            items(recommendedAddOns) { item ->
                RecommendationCard(
                    menuItem = item,
                    onAdd = { onAddToCart(item) }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Preview
@Composable
fun CartScreenPreview() {
    LazyPizzaTheme {
        CartScreen(
            viewModel = viewModel(),
            windowSizeClass = WindowSizeClass.calculateFromSize(
                DpSize(411.dp, 891.dp)
            ),
            onNavigateToMenu = {}
        )
    }
}

@Composable
fun EmptyCartState(
    onNavigateToMenu: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 120.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Your Cart Is Empty",
            style = AppTextStyles.Title1SemiBold.copy(fontWeight = FontWeight.Medium),
            color = MaterialTheme.colorScheme.onSurface
        )
        Text(
            "Head back to the menu and grab a pizza you love.",
            style = AppTextStyles.Body3Regular,
            color = MaterialTheme.colorScheme.secondary,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 8.dp)
        )
        Spacer(modifier = Modifier.height(32.dp))
        PrimaryGradientButton(
            text = "Back to Menu",
            onClick = onNavigateToMenu,
            buttonModifier = Modifier.height(40.dp)
        )
    }
}

@Preview
@Composable
fun EmptyCartStatePreview() {
    LazyPizzaTheme {
        EmptyCartState(onNavigateToMenu = {})
    }
}

@Composable
fun CartItemView(
    item: CartLine,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit,
    onRemoveItem: () -> Unit
) {
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
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
    ) {
        Box {
            Row(modifier = Modifier.height(IntrinsicSize.Min)) {
                Box(
                    modifier = Modifier
                        .width(106.dp)
                        .fillMaxHeight()
                        .background(color = MaterialTheme.colorScheme.inverseSurface),
                    contentAlignment = Alignment.Center
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(item.item.imageUrl)
                            .crossfade(true)
                            .build(),
                        contentDescription = item.item.name,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(88.dp)

                    )
                }

                Column(
                    modifier = Modifier.padding(16.dp)
                ) {

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Text(
                            modifier = Modifier.padding(bottom = 4.dp),
                            text = item.item.name,
                            style = AppTextStyles.Body1Medium,
                            color = MaterialTheme.colorScheme.onSurface
                        )

                        OutlinedIconButton(
                            modifier = Modifier.size(22.dp),
                            shape = RoundedCornerShape(8.dp),
                            border = BorderStroke(
                                1.dp,
                                MaterialTheme.colorScheme.outlineVariant
                            ),
                            onClick = onRemoveItem
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.ic_trash),
                                modifier = Modifier.size(14.dp),
                                contentDescription = "Delete",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }

                    if (item.selections.isNotEmpty()) {
                        item.selections.forEach { selection ->
                            Text(
                                "${selection.quantity} x ${selection.option.name}",
                                style = AppTextStyles.Body3Regular,
                                color = MaterialTheme.colorScheme.secondary
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        QuantityStepper(
                            quantity = item.quantity,
                            minQuantity = 1,
                            onIncrement = onIncrement,
                            onDecrement = onDecrement
                        )

                        Column(horizontalAlignment = Alignment.End) {
                            Text(
                                text = item.lineTotal().format(),
                                style = AppTextStyles.Title1SemiBold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "${item.quantity} × ${item.unitTotal().format()}",
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

@Preview
@Composable
fun CartItemViewPreview() {
    /* LazyPizzaTheme {
         CartItemView(
             item = sampleCart.first(),
             onIncrement = {},
             onDecrement = {},
             onRemoveItem = {}
         )
     }*/
}
