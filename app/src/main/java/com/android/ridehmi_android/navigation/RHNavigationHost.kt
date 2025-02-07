package com.android.ridehmi_android.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

/**
 * Navigation host for the application.
 *
 * @param navController    The navigation controller to manage app navigation.
 * @param startDestination The start destination route.
 *
 * Created by Gowthamchandran R on 07-02-2024.
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

    }
}