package com.android.ridehmi_android.presentation.screens.riderHMIScreen

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.with
import androidx.compose.foundation.clickable
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import com.android.ridehmi_android.R
import com.android.ridehmi_android.viewmodel.RHRideHMIViewModel

/**
 * A composable function that displays a toggleable text for the motor state.
 * The text animates between "MOTOR ON" and "MOTOR OFF" based on the state provided by the ViewModel.
 *
 * @param viewModel The ViewModel that provides the motor state and handles toggling logic.
 */
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun RHMotorToggleText(viewModel: RHRideHMIViewModel) {
    // Observe the state from the ViewModel
    val isMotorOn by viewModel.isMotorOn.collectAsState()

    // AnimatedContent to animate text changes
    AnimatedContent(
        targetState = isMotorOn,
        transitionSpec = {
            // Define the animation: fade out old text, fade in new text
            fadeIn(animationSpec = tween(500)) with fadeOut(animationSpec = tween(500))
        }, label = ""
    ) { targetState ->
        // Build the annotated string with dynamic colors
        val annotatedText = buildAnnotatedString {
            append(stringResource(id = R.string.motor_label)) // "MOTOR "
            append(" ")
            if (targetState) {
                withStyle(style = SpanStyle(color = Color.Green)) {
                    append(stringResource(id = R.string.motor_on)) // "ON"
                }
            } else {
                withStyle(style = SpanStyle(color = Color.Red)) {
                    append(stringResource(id = R.string.motor_off)) // "OFF"
                }
            }
        }
        Text(
            text = annotatedText,
            color = Color.White,
            letterSpacing = 2.sp,
            fontWeight = FontWeight.W900,
            fontSize = 20.sp,
            modifier = Modifier.clickable {
                // Call the ViewModel function to toggle the state
                viewModel.toggleMotor()
            }
        )
    }
}