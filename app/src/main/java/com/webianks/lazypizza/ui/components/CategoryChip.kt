package com.webianks.lazypizza.ui.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.webianks.lazypizza.ui.theme.AppTextStyles
import com.webianks.lazypizza.ui.theme.LazyPizzaTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryChip(
    modifier: Modifier = Modifier,
    text: String,
    isSelected: Boolean,
    onSelected: (String) -> Unit
) {
    FilterChip(
        modifier = modifier.height(32.dp),
        selected = isSelected,
        onClick = { onSelected(text) },
        label = {
            Text(
                text = text,
                style = AppTextStyles.Body3Medium,
                color = MaterialTheme.colorScheme.onSurface
            )
        },
        shape = RoundedCornerShape(8.dp),
        colors = FilterChipDefaults.filterChipColors(
            containerColor = MaterialTheme.colorScheme.surface,
            labelColor = MaterialTheme.colorScheme.onSurface,
            selectedContainerColor = MaterialTheme.colorScheme.primary,
            selectedLabelColor = MaterialTheme.colorScheme.onPrimary
        ),
        border = FilterChipDefaults.filterChipBorder(
            borderColor = MaterialTheme.colorScheme.outline,
            borderWidth = 1.dp,
            enabled = true,
            selected = false,
            selectedBorderColor = MaterialTheme.colorScheme.primary
        ),
    )
}

@Preview(showBackground = true)
@Composable
fun CategoryChipPreview() {
    LazyPizzaTheme {
        CategoryChip(text = "Pizza", isSelected = false, onSelected = {})
    }
}

@Preview(showBackground = true)
@Composable
fun CategoryChipSelectedPreview() {
    LazyPizzaTheme {
        CategoryChip(text = "Pizza", isSelected = true, onSelected = {})
    }
}