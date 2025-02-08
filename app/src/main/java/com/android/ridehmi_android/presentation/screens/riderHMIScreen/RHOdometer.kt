package com.android.ridehmi_android.presentation.screens.riderHMIScreen

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.android.ridehmi_android.ui.theme.Green
import com.android.ridehmi_android.ui.theme.Red
import com.android.ridehmi_android.ui.theme.Yellow

/**
 * A composable function that renders an animated odometer with concentric rings.
 * The rings dynamically change their appearance (color, stroke width, and spacing)
 * based on the provided speed value.
 *
 * @param speed The current speed in km/h, used to determine the appearance of the rings and text.
 */
@Composable
fun Odometer(speed: Float) {
    // Get the current screen density to convert dp to pixels
    val density = LocalDensity.current
    val baseStrokeWidth = with(density) { 2.dp.toPx() } // Base stroke width for rings
    val maxStrokeWidth = with(density) { 4.dp.toPx() } // Maximum stroke width for rings
    val baseSpacing = 30f // Base spacing between rings
    val reservedSpace = with(density) { 50.dp.toPx() } // Reserved space around the text

    // Canvas to draw the odometer
    Canvas(modifier = Modifier.fillMaxSize()) {
        val centerX = size.width / 2 // Center X of the canvas
        val centerY = size.height / 2 // Center Y of the canvas

        // Draw rings only if speed is greater than 0
        if (speed > 0) {
            for (i in 1..10) {
                val ringThreshold = i * 10f  // Threshold for each ring (e.g., 10, 20, 30, etc.)
                if (speed >= ringThreshold) { // Only draw visible rings
                    val alpha = if (speed >= 60) 1f else speed / (ringThreshold * 2)

                    // Increase stroke width and spacing dynamically with speed
                    val strokeWidth = if (speed > 60) {
                        baseStrokeWidth + ((speed - 60) / 14).coerceAtMost(maxStrokeWidth - baseStrokeWidth)
                    } else {
                        baseStrokeWidth
                    }

                    // Dynamically adjust spacing between rings based on speed
                    val extraSpacing =
                        if (speed > 60) ((speed - 60) / 8).coerceAtMost(50f) else 0f // Increased spacing rate
                    val width = (i * (baseSpacing + extraSpacing)) + reservedSpace
                    val height = width  // Keep circular aspect ratio

                    // Determine ring color based on speed range
                    val arcColor = when {
                        speed <= 60 -> Green // Green for low speed
                        speed in 61f..100f -> Yellow // Yellow for medium speed
                        else -> Red // Red for high speed
                    }

                    // Define the start angles for the arcs
                    val sections = listOf(-30f, 30f, 150f, 210f)

                    // Draw arcs for each section
                    sections.forEach { startAngle ->
                        drawArc(
                            color = arcColor.copy(alpha = alpha),
                            startAngle = startAngle,
                            sweepAngle = if (speed > 60) 90f else 60f,  // Adjust to join arcs at high speed
                            useCenter = false,
                            topLeft = Offset(centerX - width, centerY - height),
                            size = androidx.compose.ui.geometry.Size(width * 2, height * 2),
                            style = Stroke(width = strokeWidth)
                        )
                    }
                }
            }
        }

        // Draw Speed Text at Center with a spotty font effect and color change
        drawIntoCanvas { canvas ->
            val textPaint = android.graphics.Paint().apply {
                color =
                    if (speed == 0f) android.graphics.Color.WHITE else android.graphics.Color.BLACK
                textSize = 50f
                textAlign = android.graphics.Paint.Align.CENTER
                isAntiAlias = true
                typeface = android.graphics.Typeface.create(
                    android.graphics.Typeface.DEFAULT,
                    android.graphics.Typeface.BOLD
                )
            }
            canvas.nativeCanvas.drawText(
                "${speed.toInt()} km/h",
                centerX,
                centerY + 25,
                textPaint
            )
        }
    }
}