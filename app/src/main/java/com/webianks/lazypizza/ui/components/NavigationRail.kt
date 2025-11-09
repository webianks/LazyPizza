package com.webianks.lazypizza.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.webianks.lazypizza.ui.screens.Destination
import com.webianks.lazypizza.ui.theme.LazyPizzaTheme

@Composable
fun NavigationRail(
    modifier: Modifier = Modifier,
    items: List<Destination> = Destination.entries,
    selectedIndex: Int,
    onItemSelected: (Int) -> Unit,
    itemSpacing: Dp = 20.dp,
    cartItemCount: Int = 0
) {
    Row {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .background(MaterialTheme.colorScheme.surface),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            items.forEachIndexed { index, destination ->
                NavigationItemView(
                    modifier,
                    destination,
                    index,
                    selectedIndex,
                    onItemSelected,
                    itemSpacing,
                    cartItemCount,
                    true
                )
            }
        }
        VerticalDivider(
            modifier = Modifier
                .statusBarsPadding()
                .navigationBarsPadding()
                .background(MaterialTheme.colorScheme.outline)
        )
    }
}

@Preview
@Composable
fun NavigationRailPreview() {
    LazyPizzaTheme {
        NavigationRail(
            selectedIndex = 0,
            onItemSelected = {},
            cartItemCount = 2
        )
    }
}