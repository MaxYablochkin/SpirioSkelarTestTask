package com.devgroup.spirioskelar.ui.components


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.devgroup.spirioskelar.R

@Composable
fun UserAvatar(modifier: Modifier = Modifier) {
    val avatarBackgroundGradient = Brush.radialGradient(
        colors = listOf(Color(0xFF4A178D), Color(0xFF2D0C63))
    )

    Box(
        modifier = modifier
            .size(96.dp)
            .clip(CircleShape)
            .background(avatarBackgroundGradient, CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(R.drawable.avatarillustration),
            contentDescription = "user avatar",
        )
    }
}

@Preview
@Composable
private fun UserAvatarPreview() = UserAvatar()