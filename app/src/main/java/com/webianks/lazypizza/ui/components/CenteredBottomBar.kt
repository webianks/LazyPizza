package com.webianks.lazypizza.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.webianks.lazypizza.ui.screens.Destination
import com.webianks.lazypizza.ui.theme.AppTextStyles
import com.webianks.lazypizza.ui.theme.LazyPizzaTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CenteredBottomBar(
    modifier: Modifier = Modifier,
    items: List<Destination> = Destination.entries,
    selectedIndex: Int,
    onItemSelected: (Int) -> Unit,
    itemSpacing: Dp = 30.dp,
    cartItemCount: Int = 0
) {
    Surface(
        modifier = modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(
            topStart = 12.dp,
            topEnd = 12.dp
        ),
        tonalElevation = 3.dp,
        shadowElevation = 8.dp,
        color = MaterialTheme.colorScheme.surfaceVariant
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
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
                    false
                )
            }
        }
    }
}

@Composable
fun NavigationItemView(
    modifier: Modifier,
    destination: Destination,
    index: Int,
    selectedIndex: Int,
    onItemSelected: (Int) -> Unit,
    itemSpacing: Dp,
    cartItemCount: Int,
    isNavRail: Boolean
) {
    val selected = index == selectedIndex

    val iconColor by animateColorAsState(
        if (selected) MaterialTheme.colorScheme.primary
        else MaterialTheme.colorScheme.secondary,
    )
    val textColor by animateColorAsState(
        if (selected) MaterialTheme.colorScheme.onSurface
        else MaterialTheme.colorScheme.secondary,
    )

    val interactionSource = remember { MutableInteractionSource() }

    Box(
        modifier =
            Modifier
                .height(66.dp)
                .wrapContentSize(Alignment.Center)
    ) {
        Column(
            modifier = Modifier
                .indication(interactionSource, ripple(bounded = false))
                .clickable(
                    interactionSource = interactionSource,
                    indication = null
                ) { onItemSelected(index) }
                .padding(horizontal = itemSpacing)
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Box(
                modifier = Modifier
                    .size(28.dp)
                    .clip(CircleShape)
                    .background(
                        if (selectedIndex == index) MaterialTheme.colorScheme.primaryContainer else
                            if (isNavRail) MaterialTheme.colorScheme.surface
                            else MaterialTheme.colorScheme.surfaceVariant
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painterResource(destination.icon),
                    contentDescription = destination.label,
                    tint = iconColor,
                    modifier = Modifier
                        .size(16.dp)
                )
            }

            Text(
                modifier = Modifier.padding(top = 2.dp),
                text = destination.label,
                color = textColor,
                style = AppTextStyles.Title4
            )
        }

        if (destination == Destination.CART && cartItemCount > 0) {
            Box(
                modifier
                    .offset(12.dp, (-20).dp)
                    .defaultMinSize(16.dp, 16.dp)
                    .dropShadow(
                        shape = CircleShape,
                        shadow = Shadow(
                            radius = 6.dp,
                            spread = 0.dp,
                            offset = DpOffset(0.dp, 4.dp),
                            alpha = 0.25f,
                            color = MaterialTheme.colorScheme.primary
                        )
                    )
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary)
                    .align(Alignment.Center)
            ) {
                Text(
                    cartItemCount.toString(),
                    modifier = Modifier.align(Alignment.Center),
                    style = AppTextStyles.Title4,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }
}

@Preview
@Composable
fun CenteredBottomBarPreview() {
    LazyPizzaTheme {
        CenteredBottomBar(
            selectedIndex = 0,
            onItemSelected = {},
            cartItemCount = 2
        )
    }
}
