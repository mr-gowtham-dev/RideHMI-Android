package com.android.ridehmi_android.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.android.ridehmi_android.presentation.screens.riderHMIScreen.RHRideHMIDashboardScreen

/**
 * Navigation host for the application.
 *
 * @param navController    The navigation controller to manage app navigation.
 * @param startDestination The start destination route.
 *
 */
@Composable
fun RHNavigationHost(
    navController: NavHostController,
    startDestination: String,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(route = RHNavigationRoutes.RideHMIScreen.route) {
            RHRideHMIDashboardScreen()
        }
    }
}