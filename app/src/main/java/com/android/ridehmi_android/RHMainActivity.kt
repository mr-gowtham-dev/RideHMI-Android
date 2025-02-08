package com.android.ridehmi_android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.android.ridehmi_android.common.RHRoute
import com.android.ridehmi_android.navigation.RHNavigationHost
import com.android.ridehmi_android.ui.theme.RideHMIAndroidTheme
import dagger.hilt.android.AndroidEntryPoint

/**
 * Main activity of the application.
 * Responsible for initializing the UI and setting up the theme.
 *
 * Created by Gowthamchandran R on 07-02-2024.
 */
@AndroidEntryPoint
class RHMainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RideHMIAndroidTheme {
                val navController = rememberNavController()
                // Call the navigation host
                RHNavigationHost(
                    navController = navController,
                    startDestination = RHRoute.RideHMIScreen.route
                )
            }
        }
    }
}