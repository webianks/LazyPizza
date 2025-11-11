package com.webianks.lazypizza.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.webianks.lazypizza.ui.theme.LazyPizzaTheme
import java.text.NumberFormat
import java.util.Locale

@Composable
fun AddToCartBottomBar(
    modifier: Modifier = Modifier,
    buttonModifier: Modifier = Modifier.height(48.dp),
    text: String,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(
                Brush.verticalGradient(
                    listOf(
                        Color.Transparent,
                        MaterialTheme.colorScheme.surfaceVariant,
                    )
                )
            )
            .padding(top = 64.dp, bottom = 16.dp, start = 16.dp, end = 16.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        PrimaryGradientButton(
            buttonModifier = Modifier.fillMaxWidth(),
            text = text,
            onClick = onClick
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AddToCartBottomBarPreview() {
    LazyPizzaTheme {
        val currencyFormatter = remember {
            NumberFormat.getCurrencyInstance(Locale.US)
        }

        AddToCartBottomBar(
            text = "Add to Cart for ${currencyFormatter.format(12.5f)}",
            onClick = {}
        )
    }
}
