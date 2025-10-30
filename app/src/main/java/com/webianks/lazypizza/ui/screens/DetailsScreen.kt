package com.webianks.lazypizza.ui.screens

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
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.webianks.lazypizza.data.MenuItem
import com.webianks.lazypizza.data.ModifierGroup
import com.webianks.lazypizza.data.ModifierOption
import com.webianks.lazypizza.data.Money
import com.webianks.lazypizza.data.SelectedModifier
import com.webianks.lazypizza.ui.components.AddToCartBottomBar
import com.webianks.lazypizza.ui.components.SubLevelNavBar
import com.webianks.lazypizza.ui.components.ToppingCard
import com.webianks.lazypizza.ui.theme.AppTextStyles

@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalLayoutApi::class
)
@Composable
fun DetailsScreen(
    windowSizeClass: WindowSizeClass,
    menuItem: MenuItem.Configurable,
    onNavigateBack: () -> Unit,
    onAddToCart: () -> Unit,
    itemDetailsViewModel: ItemDetailsViewModel,
) {
    var selectedOptions by remember {
        mutableStateOf<Map<ModifierGroup, Map<ModifierOption, Int>>>(
            emptyMap()
        )
    }

    val totalPrice by remember(selectedOptions) {
        derivedStateOf {
            val optionsPrice = selectedOptions.values.flatMap { it.entries }
                .fold(Money(0.0)) { acc, (option, quantity) ->
                    acc + (option.price * quantity)
                }
            menuItem.basePrice + optionsPrice
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
        val onIncrement = { group: ModifierGroup, option: ModifierOption ->
            val newMap = selectedOptions.toMutableMap()
            val groupSelections = newMap[group]?.toMutableMap() ?: mutableMapOf()
            val currentQuantity = groupSelections[option] ?: 0

            if (currentQuantity < 3) {
                groupSelections[option] = currentQuantity + 1
                newMap[group] = groupSelections
                selectedOptions = newMap
            }
        }

        val onDecrement = { group: ModifierGroup, option: ModifierOption ->
            val newMap = selectedOptions.toMutableMap()
            val groupSelections = newMap[group]?.toMutableMap()
            if (groupSelections != null) {
                val currentQuantity = groupSelections[option] ?: 0

                if (currentQuantity > 0) {
                    // The number of *types* of toppings must be >= min
                    if (groupSelections.size >= group.min) {
                        groupSelections[option] = currentQuantity - 1
                        if (groupSelections[option] == 0) {
                            groupSelections.remove(option)
                        }
                        if (groupSelections.isEmpty()) {
                            newMap.remove(group)
                        } else {
                            newMap[group] = groupSelections
                        }
                        selectedOptions = newMap
                    }
                }
            }
        }

        val onRemove = { group: ModifierGroup, option: ModifierOption ->
            val newMap = selectedOptions.toMutableMap()
            val groupSelections = newMap[group]?.toMutableMap()
            if (groupSelections != null) {
                groupSelections.remove(option)
                if (groupSelections.isEmpty()) {
                    newMap.remove(group)
                } else {
                    newMap[group] = groupSelections
                }
                selectedOptions = newMap
            }
        }

        when (windowSizeClass.widthSizeClass) {
            WindowWidthSizeClass.Compact -> {
                SmallScreenLayout(
                    padding,
                    menuItem,
                    selectedOptions,
                    totalPrice,
                    onIncrement,
                    onDecrement,
                    onRemove,
                    onAddToCart = {
                        itemDetailsViewModel.addConfigurable(
                            item = menuItem,
                            baseQty = 1,
                            selections = selectedOptions.flatMap { (group, optionsWithQty) ->
                                optionsWithQty.map { (option, quantity) ->
                                    SelectedModifier(
                                        groupId = group.id,
                                        option = option,
                                        quantity = quantity
                                    )
                                }
                            }
                        )
                        onAddToCart()
                    }
                )
            }

            else -> {
                LargeScreenLayout(
                    padding,
                    menuItem,
                    selectedOptions,
                    totalPrice,
                    onIncrement,
                    onDecrement,
                    onRemove,
                    onAddToCart = {
                        itemDetailsViewModel.addConfigurable(
                            item = menuItem,
                            baseQty = 1,
                            selections = selectedOptions.flatMap { (group, optionsWithQty) ->
                                optionsWithQty.map { (option, quantity) ->
                                    SelectedModifier(
                                        groupId = group.id,
                                        option = option,
                                        quantity = quantity
                                    )
                                }
                            }
                        )
                        onAddToCart()
                    }
                )
            }
        }
    }
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
private fun ModifiersContent(
    modifier: Modifier = Modifier,
    groups: List<ModifierGroup>,
    selectedOptions: Map<ModifierGroup, Map<ModifierOption, Int>>,
    onIncrement: (ModifierGroup, ModifierOption) -> Unit,
    onDecrement: (ModifierGroup, ModifierOption) -> Unit,
    onRemove: (ModifierGroup, ModifierOption) -> Unit,
) {
    groups.forEach { group ->
        Text(
            modifier = modifier.padding(
                start = 16.dp,
                top = 16.dp
            ),
            text = group.title.uppercase(),
            style = AppTextStyles.Label2Semibold,
            color = MaterialTheme.colorScheme.secondary
        )

        FlowRow(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            maxItemsInEachRow = 3
        ) {
            group.options.forEach { option ->
                val currentSelections = selectedOptions[group].orEmpty()
                val quantity = currentSelections[option] ?: 0
                val isSelected = quantity > 0
                val simpleMenuItem = MenuItem.Simple(
                    id = option.id,
                    name = option.name,
                    imageUrl = option.imageUrl,
                    category = "", // Not needed for display
                    basePrice = option.price
                )
                ToppingCard(
                    modifier = Modifier
                        .weight(1f)
                        .height(142.dp),
                    topping = simpleMenuItem,
                    isSelected = isSelected,
                    quantity = quantity,
                    onIncrement = { onIncrement(group, option) },
                    onDecrement = { onDecrement(group, option) },
                    onClick = {
                        if (isSelected) onRemove(group, option) else onIncrement(
                            group,
                            option
                        )
                    }
                )
            }
        }
    }
}

@Composable
private fun SmallScreenLayout(
    padding: PaddingValues,
    menuItem: MenuItem.Configurable,
    selectedOptions: Map<ModifierGroup, Map<ModifierOption, Int>>,
    totalPrice: Money,
    onIncrement: (ModifierGroup, ModifierOption) -> Unit,
    onDecrement: (ModifierGroup, ModifierOption) -> Unit,
    onRemove: (ModifierGroup, ModifierOption) -> Unit,
    onAddToCart: () -> Unit
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

                    ModifiersContent(
                        groups = menuItem.groups,
                        selectedOptions = selectedOptions,
                        onIncrement = onIncrement,
                        onDecrement = onDecrement,
                        onRemove = onRemove
                    )
                    Spacer(modifier = Modifier.height(80.dp))
                }
            }
        }
        AddToCartBottomBar(
            modifier = Modifier.align(Alignment.BottomCenter),
            buttonModifier = Modifier.height(48.dp),
            text = "Add to Cart for ${totalPrice.format()}",
            onClick = onAddToCart,
        )
    }
}

@Composable
private fun LargeScreenLayout(
    padding: PaddingValues,
    menuItem: MenuItem.Configurable,
    selectedOptions: Map<ModifierGroup, Map<ModifierOption, Int>>,
    totalPrice: Money,
    onIncrement: (ModifierGroup, ModifierOption) -> Unit,
    onDecrement: (ModifierGroup, ModifierOption) -> Unit,
    onRemove: (ModifierGroup, ModifierOption) -> Unit,
    onAddToCart: () -> Unit
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
                ModifiersContent(
                    groups = menuItem.groups,
                    selectedOptions = selectedOptions,
                    onIncrement = onIncrement,
                    onDecrement = onDecrement,
                    onRemove = onRemove
                )
                Spacer(modifier = Modifier.height(80.dp))
            }
            AddToCartBottomBar(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .clip(RoundedCornerShape(bottomStart = 16.dp)),
                buttonModifier = Modifier.height(48.dp),
                text = "Add to Cart for ${totalPrice.format()}",
                onClick = onAddToCart
            )
        }
    }
}

@Composable
fun PizzaDetails(modifier: Modifier, menuItem: MenuItem.Configurable) {
    Text(
        modifier = modifier,
        text = menuItem.name,
        style = AppTextStyles.Title1SemiBold,
        color = MaterialTheme.colorScheme.onSurface
    )
    menuItem.description?.let {
        Text(
            modifier = modifier,
            text = it,
            style = AppTextStyles.Body3Regular,
            color = MaterialTheme.colorScheme.secondary
        )
    }
}

// Previews are disabled as they require a valid ViewModel factory and a sample configurable item.
/*
@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Preview(showBackground = true, widthDp = 1000)
@Composable
fun DetailsScreenPreviewWide() {
    LazyPizzaTheme {
        DetailsScreen(
            windowSizeClass = WindowSizeClass.calculateFromSize(
                DpSize(1000.dp, 800.dp)
            ),
            menuItem = sampleItem, // Needs to be a valid MenuItem.Configurable
            onNavigateBack = {},
            onGoToCart = {},
            cartViewModel = viewModel() // Needs factory
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
            menuItem = sampleItem, // Needs to be a valid MenuItem.Configurable
            onNavigateBack = {},
            onGoToCart = {},
            cartViewModel = viewModel() // Needs factory
        )
    }
}
*/
