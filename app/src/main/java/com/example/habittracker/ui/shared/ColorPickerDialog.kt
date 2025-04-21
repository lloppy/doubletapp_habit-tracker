package com.example.habittracker.ui.shared

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.habittracker.R
import com.example.model.toHsv
import com.example.model.toRgb
import com.example.habittracker.model.HabitEntity
import com.example.habittracker.ui.theme.Spacing

@Composable
fun ColorPickerDialog(
    onDismissRequest: () -> Unit,
    onColorSelected: (Color) -> Unit,
    habitEntity: HabitEntity,
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = { Text(stringResource(R.string.select_color)) },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(Spacing.medium)
            ) {
                ColorMapSelector(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(dimensionResource(R.dimen.color_selector_height)),
                    onColorSelected = {
                        onColorSelected(it)
                        onDismissRequest()
                    }
                )
                Text(habitEntity.color.toHsv().toString())
                Text(habitEntity.color.toRgb().toString())
            }
        },
        confirmButton = {},
        dismissButton = {}
    )
}

@Composable
private fun ColorMapSelector(
    modifier: Modifier = Modifier,
    onColorSelected: (Color) -> Unit = {},
) {
    val scrollState = rememberScrollState()

    val step = 20
    val colors = remember {
        (0..15).map { Color.hsv((it * step).toFloat(), 1f, 1f) }
    }

    Row(
        modifier = modifier
            .clip(RoundedCornerShape(Spacing.corner))
            .horizontalScroll(scrollState)
    ) {
        colors.forEachIndexed { index, color ->
            val previousIndex = if (index > 0) index - 1 else 0

            ColorSquare(
                index = index,
                modifier = Modifier
                    .fillMaxSize()
                    .drawWithCache {
                        onDrawBehind {
                            val brush = Brush.horizontalGradient(
                                colorStops = listOf(
                                    0f to colors[previousIndex],
                                    1f to color
                                ).toTypedArray()
                            )
                            drawRect(brush = brush)
                        }
                    },
                boxModifier = Modifier
                    .size(60.dp)
                    .clip(RoundedCornerShape(Spacing.corner))
                    .background(colors[previousIndex])
                    .clickable { onColorSelected(colors[previousIndex]) }
            )
        }
    }
}

@Composable
private fun ColorSquare(
    index: Int,
    modifier: Modifier = Modifier,
    boxModifier: Modifier = Modifier,
) {
    if (index != 0) {
        Box(
            modifier = modifier.padding(end = 40.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Box(
                modifier = boxModifier.border(
                    width = 2.dp,
                    color = Color.Black,
                    shape = RoundedCornerShape(Spacing.corner)
                )
            )
        }
    } else {
        Box(modifier = modifier.padding(end = 20.dp))
    }
}

