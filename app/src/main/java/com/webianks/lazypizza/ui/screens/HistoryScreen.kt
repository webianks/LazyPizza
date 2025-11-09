package com.webianks.lazypizza.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.webianks.lazypizza.ui.components.PrimaryGradientButton
import com.webianks.lazypizza.ui.theme.AppTextStyles

@Composable
fun HistoryScreen() {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        NonLoggedInState()
    }
}

@Preview
@Composable
fun NonLoggedInState() {
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
            onClick = { }
        )
    }
}