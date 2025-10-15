package com.webianks.lazypizza.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.webianks.lazypizza.ui.components.AddToCartBottomBar
import com.webianks.lazypizza.ui.components.SubLevelNavBar
import com.webianks.lazypizza.ui.components.ToppingCard
import com.webianks.lazypizza.ui.theme.AppTextStyles
import com.webianks.lazypizza.ui.theme.LazyPizzaTheme

@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalLayoutApi::class
)
@Composable
fun DetailsScreen(
    windowSizeClass: WindowSizeClass,
    menuItem: MenuItem,
    onNavigateBack: () -> Unit
) {
    var selectedToppings by remember { mutableStateOf<Map<Topping, Int>>(emptyMap()) }
    val basePrice = remember { menuItem.price.removePrefix("$").toDoubleOrNull() ?: 0.0 }

    val totalPrice by remember(selectedToppings, basePrice) {
        derivedStateOf {
            val toppingsPrice = selectedToppings.entries.sumOf { (topping, quantity) ->
                (topping.price.removePrefix("$").toDoubleOrNull() ?: 0.0) * quantity
            }
            basePrice + toppingsPrice
        }
    }

    Scaffold(
        topBar = {
            SubLevelNavBar(
                onBackClicked = onNavigateBack,
                modifier = Modifier
                    .statusBarsPadding()
                    .height(64.dp)
            )
        },
        containerColor = MaterialTheme.colorScheme.surface
    ) { padding ->
        val onToppingUpdated: (Pair<Topping, UpdateType>) -> Unit = {
            val newMap = selectedToppings.toMutableMap()
            val quantity = newMap[it.first] ?: 0

            when (it.second) {
                UpdateType.INCREMENT -> newMap[it.first] = quantity + 1
                UpdateType.DECREMENT -> if (quantity > 1) newMap[it.first] =
                    quantity - 1 else newMap.remove(it.first)

                UpdateType.SELECT -> if (quantity > 0) newMap.remove(it.first) else newMap[it.first] =
                    1
            }
            selectedToppings = newMap
        }

        when (windowSizeClass.widthSizeClass) {
            WindowWidthSizeClass.Compact -> {
                SmallScreenLayout(
                    padding,
                    menuItem,
                    selectedToppings,
                    totalPrice,
                    onToppingUpdated
                )
            }

            else -> {
                LargeScreenLayout(
                    padding,
                    menuItem,
                    selectedToppings,
                    totalPrice,
                    onToppingUpdated
                )
            }
        }
    }
}

enum class UpdateType {
    INCREMENT, DECREMENT, SELECT
}

@Composable
private fun PizzaImage(
    modifier: Modifier = Modifier,
    menuItem: MenuItem
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.TopCenter
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(menuItem.imageUrl)
                .crossfade(true)
                .build(),
            contentDescription = menuItem.name,
            modifier = Modifier.size(240.dp)
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun ToppingsContent(
    modifier: Modifier = Modifier,
    selectedToppings: Map<Topping, Int>,
    onToppingUpdated: (Pair<Topping, UpdateType>) -> Unit
) {
    Text(
        modifier = modifier.padding(
            start = 16.dp,
            top = 16.dp
        ),
        text = "ADD EXTRA TOPPINGS",
        style = AppTextStyles.Label2Semibold,
        color = MaterialTheme.colorScheme.secondary
    )

    FlowRow(
        modifier = Modifier.padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        maxItemsInEachRow = 3
    ) {
        sampleToppings.forEach { topping ->
            val quantity = selectedToppings[topping] ?: 0
            ToppingCard(
                modifier = Modifier
                    .weight(1f)
                    .height(142.dp),
                topping = topping,
                isSelected = quantity > 0,
                quantity = quantity,
                onIncrement = { onToppingUpdated(topping to UpdateType.INCREMENT) },
                onDecrement = { onToppingUpdated(topping to UpdateType.DECREMENT) },
                onClick = { onToppingUpdated(topping to UpdateType.SELECT) }
            )
        }
    }
}

@Composable
private fun SmallScreenLayout(
    padding: PaddingValues,
    menuItem: MenuItem,
    selectedToppings: Map<Topping, Int>,
    totalPrice: Double,
    onToppingUpdated: (Pair<Topping, UpdateType>) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
    ) {
        LazyColumn {
            item {
                PizzaImage(
                    modifier = Modifier.fillMaxWidth(),
                    menuItem = menuItem
                )
            }
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
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
                            shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                        )
                ) {
                    Spacer(modifier = Modifier.height(16.dp))

                    PizzaDetails(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        menuItem = menuItem
                    )

                    ToppingsContent(
                        selectedToppings = selectedToppings,
                        onToppingUpdated = onToppingUpdated
                    )
                    Spacer(modifier = Modifier.height(80.dp))
                }
            }
        }
        AddToCartBottomBar(
            price = totalPrice,
            onAddToCart = { },
            mainModifier = Modifier.align(Alignment.BottomCenter),
            buttonModifier = Modifier.height(48.dp)
        )
    }
}

@Composable
private fun LargeScreenLayout(
    padding: PaddingValues,
    menuItem: MenuItem,
    selectedToppings: Map<Topping, Int>,
    totalPrice: Double,
    onToppingUpdated: (Pair<Topping, UpdateType>) -> Unit
) {
    Row(
        Modifier
            .fillMaxSize()
            .padding(padding)
    ) {
        Column(modifier = Modifier.weight(1f)) {
            PizzaImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                menuItem = menuItem
            )
            PizzaDetails(
                modifier = Modifier.padding(horizontal = 16.dp),
                menuItem = menuItem
            )
        }
        Box(
            modifier = Modifier
                .weight(1f)
                .padding(bottom = 16.dp)
                .fillMaxSize()
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
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
            ) {
                ToppingsContent(
                    selectedToppings = selectedToppings,
                    onToppingUpdated = onToppingUpdated
                )
                Spacer(modifier = Modifier.height(80.dp))
            }
            AddToCartBottomBar(
                mainModifier = Modifier
                    .align(Alignment.BottomCenter)
                    .clip(RoundedCornerShape(bottomStart = 16.dp)),
                buttonModifier = Modifier.height(48.dp),
                price = totalPrice,
                onAddToCart = {}
            )
        }
    }
}

@Composable
fun PizzaDetails(modifier: Modifier, menuItem: MenuItem) {
    Text(
        modifier = modifier,
        text = menuItem.name,
        style = AppTextStyles.Title1SemiBold,
        color = MaterialTheme.colorScheme.onSurface
    )
    Text(
        modifier = modifier,
        text = menuItem.description,
        style = AppTextStyles.Body3Regular,
        color = MaterialTheme.colorScheme.secondary
    )
}

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Preview(showBackground = true, widthDp = 1000)
@Composable
fun DetailsScreenPreviewWide() {
    LazyPizzaTheme {
        DetailsScreen(
            windowSizeClass = WindowSizeClass.calculateFromSize(
                DpSize(1000.dp, 800.dp)
            ),
            menuItem = sampleMenu.first(),
            onNavigateBack = {}
        )
    }
}

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Preview(showBackground = true)
@Composable
fun DetailsScreenPreview() {
    LazyPizzaTheme {
        DetailsScreen(
            windowSizeClass = WindowSizeClass.calculateFromSize(
                DpSize(411.dp, 891.dp)
            ),
            menuItem = sampleMenu.first(),
            onNavigateBack = {}
        )
    }
}
