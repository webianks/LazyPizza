package com.webianks.lazypizza.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridState
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.LoadingIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.webianks.lazypizza.R
import com.webianks.lazypizza.data.MenuItem
import com.webianks.lazypizza.data.repository.FakeCartRepository
import com.webianks.lazypizza.data.repository.FakeMenuRepository
import com.webianks.lazypizza.ui.components.CategoryChip
import com.webianks.lazypizza.ui.components.OtherItemCard
import com.webianks.lazypizza.ui.components.PizzaItemCard
import com.webianks.lazypizza.ui.components.SearchBar
import com.webianks.lazypizza.ui.theme.AppTextStyles
import com.webianks.lazypizza.ui.theme.LazyPizzaTheme
import kotlinx.coroutines.launch

@OptIn(
    ExperimentalFoundationApi::class,
    ExperimentalMaterial3ExpressiveApi::class
)
@Composable
fun MenuScreen(
    windowSizeClass: WindowSizeClass,
    cartViewModel: CartViewModel,
    menuViewModel: MenuViewModel,
    gridState: LazyStaggeredGridState,
    onPizzaSelected: (menuItem: MenuItem.Configurable) -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()
    val menu by menuViewModel.all.collectAsState()
    val isLoading by menuViewModel.isLoading.collectAsState()
    val groupedMenu = remember(menu) { menu.groupBy { it.category } }
    val categories = remember(groupedMenu) { groupedMenu.keys.toList() }
    val cartLines by cartViewModel.lines.collectAsState()
    val haptic = LocalHapticFeedback.current

    val categoryHeaderIndices = remember(categories, groupedMenu) {
        val indices = mutableMapOf<String, Int>()
        var currentIndex = 3 // 0: Image, 1: SearchBar, 2: Chips sticky header
        categories.forEach { category ->
            indices[category] = currentIndex
            currentIndex += 1 + (groupedMenu[category]?.size ?: 0)
        }
        indices
    }

    val selectedCategory by remember {
        derivedStateOf {
            val firstVisible = gridState.firstVisibleItemIndex
            if (firstVisible < 3) null
            else {
                categoryHeaderIndices.entries
                    .findLast { it.value <= firstVisible }
                    ?.key
            }
        }
    }

    if (isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            LoadingIndicator()
        }
    } else {
        val columns = when (windowSizeClass.widthSizeClass) {
            WindowWidthSizeClass.Compact -> 1
            WindowWidthSizeClass.Medium -> 1
            WindowWidthSizeClass.Expanded -> 2
            else -> 1
        }
        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Fixed(columns),
            state = gridState,
            modifier = Modifier
                .fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalItemSpacing = 8.dp
        ) {
            item(span = StaggeredGridItemSpan.FullLine) {
                Image(
                    painter = painterResource(id = R.drawable.banner),
                    contentDescription = "Pizza Header",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .clip(shape = RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )
            }

            item(span = StaggeredGridItemSpan.FullLine) {
                SearchBar(
                    modifier = Modifier.padding(vertical = 8.dp),
                    searchQuery = searchQuery,
                    onQueryChanged = { searchQuery = it }
                )
            }

            item(span = StaggeredGridItemSpan.FullLine) {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                ) {
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(categories) { category ->
                            CategoryChip(
                                text = category.toTitleCase(),
                                isSelected = category == selectedCategory,
                                onSelected = {
                                    coroutineScope.launch {
                                        categoryHeaderIndices[category]?.let {
                                            gridState.animateScrollToItem(it)
                                        }
                                    }
                                }
                            )
                        }
                    }
                }
            }

            val isSearching = searchQuery.isNotBlank()
            if (isSearching) {
                val filteredItems =
                    menu.filter { it.name.contains(searchQuery, ignoreCase = true) }
                if (filteredItems.isEmpty()) {
                    item(span = StaggeredGridItemSpan.FullLine) {
                        Text(
                            text = "No results found for your query",
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            textAlign = TextAlign.Center
                        )
                    }
                } else {
                    val searchGrouped = filteredItems.groupBy { it.category }
                    searchGrouped.forEach { (category, items) ->
                        item(span = StaggeredGridItemSpan.FullLine) {
                            Text(
                                text = category.uppercase(),
                                style = AppTextStyles.Label2Semibold,
                                color = MaterialTheme.colorScheme.secondary,
                            )
                        }
                        items(items) { menuItem ->
                            when (menuItem) {
                                is MenuItem.Configurable -> {
                                    PizzaItemCard(
                                        modifier = Modifier,
                                        menuItem = menuItem,
                                        onClick = {
                                            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                            onPizzaSelected(menuItem)
                                        },
                                    )
                                }

                                is MenuItem.Simple -> {
                                    val cartItem = cartLines.find { it.item.id == menuItem.id }
                                    OtherItemCard(
                                        modifier = Modifier,
                                        menuItem = menuItem,
                                        quantity = cartItem?.quantity ?: 0,
                                        minQuantity = 0,
                                        onAddToCart = {
                                            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                            menuViewModel.addSimple(menuItem)
                                        },
                                        onIncrement = {
                                            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                            cartItem?.let {
                                                cartViewModel.inc(
                                                    it.identityKey(),
                                                    it.quantity
                                                )
                                            }
                                        },
                                        onDecrement = {
                                            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                            cartItem?.let {
                                                cartViewModel.dec(
                                                    it.identityKey(),
                                                    it.quantity
                                                )
                                            }
                                        },
                                        onRemove = { cartItem?.let { cartViewModel.remove(it.identityKey()) } }
                                    )
                                }
                            }
                        }
                    }
                }
            } else {
                groupedMenu.forEach { (category, items) ->
                    item(span = StaggeredGridItemSpan.FullLine) {
                        Text(
                            text = category.uppercase(),
                            style = AppTextStyles.Label2Semibold,
                            color = MaterialTheme.colorScheme.secondary,
                        )
                    }
                    items(items) { menuItem ->
                        when (menuItem) {
                            is MenuItem.Configurable -> {
                                PizzaItemCard(
                                    modifier = Modifier,
                                    menuItem = menuItem,
                                    onClick = { 
                                        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                        onPizzaSelected(menuItem) 
                                    }
                                )
                            }

                            is MenuItem.Simple -> {
                                val cartItem = cartLines.find { it.item.id == menuItem.id }
                                OtherItemCard(
                                    modifier = Modifier,
                                    menuItem = menuItem,
                                    quantity = cartItem?.quantity ?: 0,
                                    minQuantity = 0,
                                    onAddToCart = {
                                        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                        menuViewModel.addSimple(menuItem)
                                    },
                                    onIncrement = {
                                        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                        cartItem?.let {
                                            cartViewModel.inc(
                                                it.identityKey(),
                                                it.quantity
                                            )
                                        }
                                    },
                                    onDecrement = {
                                        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                        cartItem?.let {
                                            cartViewModel.dec(
                                                it.identityKey(),
                                                it.quantity
                                            )
                                        }
                                    },
                                    onRemove = { cartItem?.let { cartViewModel.remove(it.identityKey()) } }
                                )
                            }
                        }
                    }
                }
            }

            item(span = StaggeredGridItemSpan.FullLine) {
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

private object FakeViewModelFactory : ViewModelProvider.Factory {
    private val cartRepository by lazy { FakeCartRepository() }
    private val menuRepository by lazy { FakeMenuRepository() }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MenuViewModel::class.java) ->
                MenuViewModel(menuRepository, cartRepository) as T

            modelClass.isAssignableFrom(CartViewModel::class.java) ->
                CartViewModel(cartRepository, menuRepository) as T

            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}


private fun String.toTitleCase(): String =
    this.split(" ").joinToString(" ") { word ->
        word.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
    }

@OptIn(
    ExperimentalMaterial3WindowSizeClassApi::class,
    ExperimentalFoundationApi::class
)
@Preview(showBackground = true)
@Composable
fun MenuScreenPreviewPreview() {
    LazyPizzaTheme {
        MenuScreen(
            windowSizeClass = WindowSizeClass.calculateFromSize(
                DpSize(411.dp, 891.dp)
            ),
            cartViewModel = viewModel(factory = FakeViewModelFactory),
            menuViewModel = viewModel(factory = FakeViewModelFactory),
            gridState = rememberLazyStaggeredGridState(),
            onPizzaSelected = {}
        )
    }
}

@OptIn(
    ExperimentalMaterial3WindowSizeClassApi::class,
    ExperimentalFoundationApi::class
)
@Preview(showBackground = true, widthDp = 1000)
@Composable
fun MenuScreenPreviewWide() {
    LazyPizzaTheme {
        MenuScreen(
            windowSizeClass = WindowSizeClass.calculateFromSize(
                DpSize(1000.dp, 800.dp)
            ),
            cartViewModel = viewModel(factory = FakeViewModelFactory),
            menuViewModel = viewModel(factory = FakeViewModelFactory),
            gridState = rememberLazyStaggeredGridState(),
            onPizzaSelected = {}
        )
    }
}
