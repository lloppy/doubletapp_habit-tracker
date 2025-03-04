package com.example.habittracker.ui.screens.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import com.example.habittracker.R

@Composable
fun IconButtonBox(color: Color, onChangeRepeated: () -> Unit, icon: Any) {
    Box(
        modifier = Modifier
            .size(dimensionResource(R.dimen.emoji_size))
            .clip(CircleShape)
            .background(color.copy(alpha = 0.4f)),
        contentAlignment = Alignment.Center
    ) {
        IconButton(onClick = onChangeRepeated) {
            when (icon) {
                is Int -> Icon(
                    painter = painterResource(icon),
                    contentDescription = null,
                    modifier = Modifier.size(dimensionResource(R.dimen.emoji_size))
                )

                is ImageVector -> Icon(
                    imageVector = icon,
                    contentDescription = null,
                    modifier = Modifier.size(dimensionResource(R.dimen.emoji_size))
                )
            }
        }
    }
}