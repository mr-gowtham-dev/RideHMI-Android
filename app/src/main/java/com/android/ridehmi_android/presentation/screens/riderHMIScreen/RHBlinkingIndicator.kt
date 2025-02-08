package com.android.ridehmi_android.presentation.screens.riderHMIScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

/**
 * A composable function that displays a blinking indicator icon.
 *
 * @param icon The icon to be displayed, must be of type ImageVector.
 * @param contentDescription Description for accessibility purposes.
 * @param isBlinking Boolean flag to control blinking animation.
 * @param onClick Lambda function triggered when the icon is clicked.
 */
@Composable
fun RHBlinkingIndicator(
    icon: Any, // Can be ImageVector or Painter
    contentDescription: String,
    isBlinking: Boolean, // Whether this indicator is currently blinking
    onClick: () -> Unit
) {
    // State to control the blinking color
    var blinkColor by remember { mutableStateOf(Color.White.copy(alpha = 0.5f)) }

    // LaunchedEffect to handle continuous blinking
    LaunchedEffect(isBlinking) {
        while (isBlinking) {
            blinkColor = Color.Green // Green color
            delay(500) // 500ms delay
            blinkColor = Color.White.copy(alpha = 0.5f) // Semi-transparent white
            delay(500) // 500ms delay
        }
        // Reset to default color when blinking stops
        if (!isBlinking) {
            blinkColor = Color.White.copy(alpha = 0.5f)
        }
    }

    // Icon with clickable modifier and dynamic color
    Icon(
        imageVector = icon as? androidx.compose.ui.graphics.vector.ImageVector
            ?: throw IllegalArgumentException("Icon must be an ImageVector"),
        contentDescription = contentDescription,
        tint = blinkColor, // Dynamic color (green or semi-transparent white)
        modifier = Modifier
            .size(48.dp)
            .clickable {
                onClick() // Call the provided onClick callback
            }
    )
}