package com.android.ridehmi_android.presentation.screens.riderHMIScreen

import android.content.pm.ActivityInfo
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.android.ridehmi_android.common.BlinkingIndicatorType
import com.android.ridehmi_android.presentation.ui_components.RHScreenOrientation
import com.android.ridehmi_android.ui.theme.HighSpeedOdometerColor
import com.android.ridehmi_android.ui.theme.LowSpeedOdometerColor
import com.android.ridehmi_android.ui.theme.MediumSpeedOdometerColor
import com.android.ridehmi_android.viewmodel.RHRideHMIViewModel
import kotlinx.coroutines.delay

/**
 * Composable function for the Rider HMI Dashboard Screen.
 * This screen displays the speedometer, motor toggle, indicators, ETA, and battery status.
 *
 * @param viewModel The ViewModel that provides data and handles business logic for the screen.
 */
@Composable
fun RHRideHMIDashboardScreen(viewModel: RHRideHMIViewModel = hiltViewModel()) {
    RHScreenOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)

    // Collect state from ViewModel
    val speed by viewModel.speed.collectAsState()
    val isAnimating by viewModel.isAnimating.collectAsState()
    val isMotorOn by viewModel.isMotorOn.collectAsState()
    val blinkingIndicator by viewModel.blinkingIndicator.collectAsState()

    // Dynamic background gradient based on speed
    val backgroundGradient = when (speed) {
        0f -> listOf(Color(0xFF1A1A1A), Color(0xFF000000)) // Dark gradient for zero speed
        in 1f..60f -> LowSpeedOdometerColor // Green gradient for low speed
        in 61f..100f -> MediumSpeedOdometerColor // Yellow gradient for medium speed
        else -> HighSpeedOdometerColor // Red gradient for high speed
    }

    // Animation logic for speedometer when motor is turned on
    LaunchedEffect(isMotorOn) {
        if (!isMotorOn) viewModel.setAnimatingState(true)
        if (isAnimating && isMotorOn) {
            // Animate from 0 to 199
            while (speed < 199f) {
                viewModel.startSpeedSet(speed + 1f)
                delay(10) // Adjust delay for smoother or faster animation
            }
            // Animate from 199 back to 0
            while (speed > 0f) {
                viewModel.startSpeedSet(speed - 1f)
                delay(10) // Adjust delay for smoother or faster animation
            }
            viewModel.setAnimatingState(false)
        }
    }

    // Main layout container
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = backgroundGradient,
                    tileMode = TileMode.Decal
                )
            )
            .padding(20.dp)
    ) {
        // Odometer centered in the Box
        Box(
            modifier = Modifier
                .align(Alignment.Center)
        ) {
            Odometer(speed)
        }

        // Top section containing motor toggle and indicator
        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 20.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // Motor toggle text
                RHMotorToggleText(viewModel)

                // Row for left and right indicators
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Left Indicator
                    RHBlinkingIndicator(
                        icon = Icons.Default.KeyboardArrowLeft,
                        contentDescription = "Left Indicator",
                        isBlinking = blinkingIndicator == BlinkingIndicatorType.LEFT,
                        onClick = {
                            // Notify the ViewModel about the left indicator click
                            viewModel.toggleIndicator(BlinkingIndicatorType.LEFT)
                        }
                    )

                    // Right Indicator
                    RHBlinkingIndicator(
                        icon = Icons.Default.KeyboardArrowRight,
                        contentDescription = "Right Indicator",
                        isBlinking = blinkingIndicator == BlinkingIndicatorType.RIGHT,
                        onClick = {
                            // Notify the ViewModel about the right indicator click
                            viewModel.toggleIndicator(BlinkingIndicatorType.RIGHT)
                        }
                    )
                }
            }
        }

        // ETA Text at Bottom Left
        Box(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = 20.dp, bottom = 20.dp)
        ) {
            RHEta(viewModel)
        }

        // Battery Percentage at Bottom Right
        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 20.dp, bottom = 20.dp)
        ) {
            RHBatteryStatus(viewModel)
        }
    }
}

/**
 * Preview function for the Rider HMI Dashboard Screen.
 * This function is used to preview the UI in Android Studio's Compose Preview.
 */
@Composable
@Preview(showBackground = true)
fun SpeedOdometerUIPreview() {
    RHRideHMIDashboardScreen()
}