package com.android.ridehmi_android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.android.ridehmi_android.ui.theme.RideHMIAndroidTheme

/**
 * Main activity of the application.
 * Responsible for initializing the UI and setting up the theme.
 *
 * Created by Gowthamchandran R on 07-02-2024.
 */
class RHMainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RideHMIAndroidTheme {

            }
        }
    }
}