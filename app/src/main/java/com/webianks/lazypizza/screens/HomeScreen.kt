package com.webianks.lazypizza.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.webianks.lazypizza.R
import com.webianks.lazypizza.ui.components.CategoryChip
import com.webianks.lazypizza.ui.components.OtherItemCard
import com.webianks.lazypizza.ui.components.PizzaItemCard
import com.webianks.lazypizza.ui.components.SearchBar
import com.webianks.lazypizza.ui.components.TopLevelNavBar
import com.webianks.lazypizza.ui.theme.AppTextStyles
import com.webianks.lazypizza.ui.theme.LazyPizzaTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    windowSizeClass: WindowSizeClass,
    onPizzaSelected: (menuItem: MenuItem) -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    val lazyStaggeredGridState = rememberLazyStaggeredGridState()
    val coroutineScope = rememberCoroutineScope()
    val groupedMenu = remember { sampleMenu.groupBy { it.category } }
    val categories = remember { groupedMenu.keys.toList() }

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
            val firstVisible = lazyStaggeredGridState.firstVisibleItemIndex
            if (firstVisible < 3) null
            else {
                categoryHeaderIndices.entries
                    .findLast { it.value <= firstVisible }
                    ?.key
            }
        }
    }

    Scaffold(
        topBar = {
            TopLevelNavBar(
                modifier = Modifier
                    .statusBarsPadding()
                    .height(64.dp)
            )
        }
    ) { padding ->
        val columns = when (windowSizeClass.widthSizeClass) {
            WindowWidthSizeClass.Compact -> 1
            WindowWidthSizeClass.Medium -> 1
            WindowWidthSizeClass.Expanded -> 2
            else -> 1
        }
        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Fixed(columns),
            state = lazyStaggeredGridState,
            modifier = Modifier
                .fillMaxWidth()
                .padding(padding),
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
                                text = category,
                                isSelected = category == selectedCategory,
                                onSelected = {
                                    coroutineScope.launch {
                                        categoryHeaderIndices[category]?.let {
                                            lazyStaggeredGridState.animateScrollToItem(it)
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
                    sampleMenu.filter { it.name.contains(searchQuery, ignoreCase = true) }
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
                            PizzaItemCard(
                                modifier = Modifier,
                                menuItem = menuItem,
                                onClick = {
                                    if (menuItem.category == "Pizza") {
                                        onPizzaSelected(menuItem)
                                    }
                                }
                            )
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
                        if (menuItem.category == "Pizza") {
                            PizzaItemCard(
                                modifier = Modifier,
                                menuItem = menuItem,
                                onClick = {
                                    if (menuItem.category == "Pizza") {
                                        onPizzaSelected(menuItem)
                                    }
                                }
                            )
                        } else {
                            OtherItemCard(
                                modifier = Modifier,
                                menuItem = menuItem
                            )
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


@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Preview(showBackground = true, widthDp = 1000)
@Composable
fun HomeScreenPreviewWide() {
    LazyPizzaTheme {
        HomeScreen(
            windowSizeClass = WindowSizeClass.calculateFromSize(
                DpSize(1000.dp, 800.dp)
            ),
            onPizzaSelected = {}
        )
    }
}

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Preview(showBackground = true)
@Composable
fun HomeScreenPreviewPreview() {
    LazyPizzaTheme {
        HomeScreen(
            windowSizeClass = WindowSizeClass.calculateFromSize(
                DpSize(411.dp, 891.dp)
            ),
            onPizzaSelected = {}
        )
    }
}