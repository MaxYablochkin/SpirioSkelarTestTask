@file:OptIn(ExperimentalMaterial3ExpressiveApi::class)

package com.devgroup.spirioskelar.ui.components

import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign

@Composable
fun LabelText(modifier: Modifier = Modifier, text: String) {
    Text(
        modifier = modifier,
        text = text,
        color = Color.White,
        textAlign = TextAlign.Start,
        style = MaterialTheme.typography.labelLargeEmphasized
    )
}