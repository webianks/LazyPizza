package com.webianks.lazypizza.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.webianks.lazypizza.ui.theme.AppTextStyles
import com.webianks.lazypizza.ui.theme.LazyPizzaTheme
import com.webianks.lazypizza.ui.theme.PrimaryGradientEnd
import com.webianks.lazypizza.ui.theme.PrimaryGradientStart
import com.webianks.lazypizza.ui.theme.TextPrimary8

@Composable
fun PrimaryGradientButton(
    modifier: Modifier = Modifier,
    text: String,
    enabled: Boolean = true,
    onClick: () -> Unit = {},
    buttonModifier: Modifier = Modifier,           // caller controls Button sizing (CHANGED: usage)
    minHeight: Dp = 48.dp,
    minWidth: Dp = 64.dp
) {
    val gradient = Brush.horizontalGradient(listOf(PrimaryGradientEnd, PrimaryGradientStart))

    val shadowModifier = if (enabled) {
        Modifier.dropShadow(
            shape = CircleShape,
            shadow = Shadow(
                radius = 6.dp,
                spread = 0.dp,
                offset = DpOffset(0.dp, 4.dp),
                alpha = 0.25f,
                color = PrimaryGradientEnd
            )
        )
    } else Modifier

    val outerPadding = if (enabled) 1.dp else 0.dp

    /* CHANGED: Box no longer forces any size (no fillMaxWidth/height here).
       It wraps its content so the Button decides the size. */
    Box(
        modifier = modifier
            .then(shadowModifier)                 // shadow outside the pill
            .clip(CircleShape)
            .background(if (enabled) gradient else SolidColor(TextPrimary8))
            .padding(outerPadding)
        // NOTE: Do NOT set .fillMaxWidth() or .height(...) on the Box here.
        // The Button inside will determine sizing.
    ) {
        Button(
            onClick = onClick,
            enabled = enabled,

            /* CHANGED: Button modifier uses buttonModifier provided by caller and ensures sensible defaults
               with defaultMinSize(minWidth, minHeight). This makes the Button decide size; Box wraps to it. */
            modifier = buttonModifier.defaultMinSize(minWidth = minWidth, minHeight = minHeight),

            shape = CircleShape,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
                disabledContainerColor = Color.Transparent
            ),

            /* CHANGED: reasonable horizontal padding so the pill looks correct */
            contentPadding = PaddingValues(horizontal = 24.dp, vertical = 0.dp),

            elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp)
        ) {
            Text(
                text = text,
                style = AppTextStyles.Title3,
                color = if (enabled) MaterialTheme.colorScheme.onPrimary
                else MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun PrimaryOutlineButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit = {}
) {
    OutlinedButton(
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            contentColor = MaterialTheme.colorScheme.primary
        ),
        border = BorderStroke(
            1.dp,
            MaterialTheme.colorScheme.primaryContainer
        ),
        onClick = onClick
    ) {
        Text(
            text = text,
            style = AppTextStyles.Title3
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PrimaryGradientButtonPreview() {
    LazyPizzaTheme {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            PrimaryGradientButton(text = "Label")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PrimaryOutlineButtonPreview() {
    LazyPizzaTheme {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            PrimaryOutlineButton(text = "Label")
        }
    }
}