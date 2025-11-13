package com.example.dialektapp.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.dialektapp.R

@Composable
fun UserAvatar(
    imageUrl: String?,
    contentDescription: String = "User Avatar",
    size: Dp,
    borderWidth: Dp = 0.dp,
    borderColor: Color = Color.Transparent,
    backgroundColor: Color = Color.Transparent,
    modifier: Modifier = Modifier,
) {
    val finalModifier = modifier
        .then(Modifier.size(size))
        .then(
            if (backgroundColor != Color.Transparent) {
                Modifier.background(backgroundColor, CircleShape)
            } else Modifier
        )
        .then(
            if (borderWidth > 0.dp) {
                Modifier.border(borderWidth, borderColor, CircleShape)
            } else Modifier
        )
        .clip(CircleShape)

    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageUrl)
            .crossfade(true)
            .build(),
        contentDescription = contentDescription,
        placeholder = painterResource(R.drawable.ic_profile_placeholder),
        error = painterResource(R.drawable.ic_profile_placeholder),
        contentScale = ContentScale.Crop,
        modifier = finalModifier
    )
}
