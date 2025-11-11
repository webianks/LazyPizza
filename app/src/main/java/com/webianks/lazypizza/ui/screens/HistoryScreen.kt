package com.webianks.lazypizza.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.webianks.lazypizza.di.ViewModelFactory
import com.webianks.lazypizza.ui.components.PrimaryGradientButton
import com.webianks.lazypizza.ui.theme.AppTextStyles

@Composable
fun HistoryScreen(
    modifier: Modifier = Modifier,
    windowSizeClass: WindowSizeClass,
    onMenuClicked: () -> Unit = {},
    isUserLoggedIn: Boolean = false,
    onSignInClicked: () -> Unit = {}
) {
    if (isUserLoggedIn) {
        val viewModel: HistoryViewModel = viewModel(factory = ViewModelFactory())
        val uiState by viewModel.uiState.collectAsState()

        if (uiState.orders.isEmpty()) {
            EmptyState(onMenuClicked = onMenuClicked)
        } else {
            val useStaggeredGrid = windowSizeClass.widthSizeClass >= WindowWidthSizeClass.Medium
            if (useStaggeredGrid) {
                LazyVerticalStaggeredGrid(
                    columns = StaggeredGridCells.Fixed(2),
                    modifier = modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    verticalItemSpacing = 8.dp,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(uiState.orders) {
                        OrderHistoryCard(it)
                    }
                }
            } else {
                LazyVerticalStaggeredGrid(
                    columns = StaggeredGridCells.Fixed(1),
                    modifier = modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    verticalItemSpacing = 8.dp,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(uiState.orders) {
                        OrderHistoryCard(it)
                    }
                }
            }
        }
    } else {
        NonLoggedInState(onSignInClicked = onSignInClicked)
    }
}

@Composable
fun EmptyState(modifier: Modifier = Modifier, onMenuClicked: () -> Unit) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "No Orders Yet",
            style = AppTextStyles.Title1SemiBold.copy(fontWeight = FontWeight.Medium),
            color = MaterialTheme.colorScheme.onSurface
        )
        Text(
            text = "Your orders will appear here after your first purchase.",
            style = AppTextStyles.Body3Regular,
            color = MaterialTheme.colorScheme.secondary,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 8.dp)
        )
        Spacer(modifier = Modifier.height(32.dp))
        PrimaryGradientButton(
            text = "Go to Menu",
            onClick = onMenuClicked,
            buttonModifier = Modifier.height(40.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun EmptyStatePreview() {
    EmptyState(onMenuClicked = {})
}

@Preview
@Composable
fun NonLoggedInState(onSignInClicked: () -> Unit = {}) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 120.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Not Signed In",
            style = AppTextStyles.Title1SemiBold.copy(fontWeight = FontWeight.Medium),
            color = MaterialTheme.colorScheme.onSurface
        )
        Text(
            "Please sign in to view your order history.",
            style = AppTextStyles.Body3Regular,
            color = MaterialTheme.colorScheme.secondary,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 8.dp)
        )
        Spacer(modifier = Modifier.height(32.dp))
        PrimaryGradientButton(
            text = "Sign In",
            onClick = onSignInClicked,
            buttonModifier = Modifier.height(40.dp)
        )
    }
}
