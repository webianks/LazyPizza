package com.webianks.lazypizza.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.webianks.lazypizza.ui.theme.AppTextStyles
import com.webianks.lazypizza.ui.theme.LazyPizzaTheme
import com.webianks.lazypizza.ui.theme.PrimaryGradientEnd
import com.webianks.lazypizza.ui.theme.PrimaryGradientStart

@Composable
fun PrimaryGradientButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit = {},
) {
    val gradient = Brush.horizontalGradient(listOf(PrimaryGradientEnd, PrimaryGradientStart))

    Box(
        modifier = modifier
            .dropShadow(
                shape = RoundedCornerShape(100.dp),
                shadow = Shadow(
                    radius = 6.dp,
                    spread = 0.dp,
                    offset = DpOffset(0.dp, (4).dp),
                    alpha = 0.25f,
                    color = PrimaryGradientEnd
                )
            )
            .clip(RoundedCornerShape(100.dp))
    ) {
        Button(
            modifier = modifier.background(gradient),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
            onClick = { onClick() },
        ) {
            Text(
                text = text, style = AppTextStyles.Title3,
                color = MaterialTheme.colorScheme.onPrimary
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

