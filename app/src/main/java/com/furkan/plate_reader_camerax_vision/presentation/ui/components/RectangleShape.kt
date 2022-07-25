package com.furkan.plate_reader_camerax_vision.presentation.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview
@Composable
fun RectangleShape() {

    Canvas(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val canvasWidth = size.width
        val canvasHeight = size.height

        with(drawContext.canvas.nativeCanvas) {
            val checkPoint = saveLayer(null, null)

            drawRect(Color.Black.copy(alpha = 0.6f))

            drawRect(
                color = Color.Transparent,
                blendMode = BlendMode.Clear,
                topLeft = Offset(x = 0F, y = canvasHeight / 2.2F),
                size = Size(canvasWidth, canvasHeight / 10F)
            )

            restoreToCount(checkPoint)
        }

        drawRect(
            color = Color.Green,
            topLeft = Offset(x = 0F, y = canvasHeight/2.2F),
            size = Size(canvasWidth, canvasHeight/10F),
            style = Stroke(width = 6.dp.toPx())
        )
    }

}

