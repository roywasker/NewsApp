package com.roy.newsapp.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.roy.newsapp.utils.Constants

@Composable
fun ErrorCard(
    modifier: Modifier = Modifier,
    message: String,
    buttonText: String = Constants.Text.TryAgainButton,
    onClick: () -> Unit
){

    Column (
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){

        Text(
            text = message,
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Red,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(10.dp))

        TextButton(
            onClick = onClick
        ) {
            Text(
                text = buttonText,
                fontSize = 16.sp,
                color = Color.Black.copy(0.8f)
            )
        }
    }
}