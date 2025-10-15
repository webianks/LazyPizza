package com.webianks.lazypizza.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.webianks.lazypizza.R
import com.webianks.lazypizza.ui.theme.AppTextStyles
import com.webianks.lazypizza.ui.theme.LazyPizzaTheme

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    searchQuery: String,
    searchLabel: String = "Search for delicious food...",
    onQueryChanged: (String) -> Unit
) {
    TextField(
        value = searchQuery,
        onValueChange = onQueryChanged,
        placeholder = {
            Text(
                searchLabel,
                style = AppTextStyles.Body1Regular,
                color = MaterialTheme.colorScheme.secondary,
            )
        },
        leadingIcon = {
            Icon(
                modifier = Modifier
                    .size(20.dp)
                    .dropShadow(
                        shape = RoundedCornerShape(100.dp),
                        shadow = Shadow(
                            radius = 6.dp,
                            spread = 0.dp,
                            offset = DpOffset(0.dp, 4.dp),
                            alpha = 0.25f,
                            color = MaterialTheme.colorScheme.primary
                        )
                    ),
                painter = painterResource(R.drawable.ic_search),
                contentDescription = "Search",
                tint = MaterialTheme.colorScheme.primary
            )
        },
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 2.dp, bottom = 2.dp)
            .height(55.dp)
            .dropShadow(
                shape = RoundedCornerShape(28.dp),
                shadow = Shadow(
                    radius = 16.dp,
                    spread = 0.dp,
                    offset = DpOffset(0.dp, 4.dp),
                    alpha = 0.04f,
                    color = MaterialTheme.colorScheme.scrim
                )
            ),
        shape = RoundedCornerShape(28.dp),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
            unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent
        )
    )
}

@Preview(showBackground = true)
@Composable
fun SearchBarPreview() {
    LazyPizzaTheme {
        SearchBar(
            searchQuery = "", onQueryChanged = {})
    }
}
