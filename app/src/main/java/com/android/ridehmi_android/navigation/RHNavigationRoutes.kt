package com.android.ridehmi_android.navigation

import com.android.ridehmi_android.common.RHRoute

/**
 * Defines the navigation routes for the application.
 *
 * @param route The route string for navigation.
 *
 */
sealed class RHNavigationRoutes(val route: String) {
    data object RideHMIScreen : RHNavigationRoutes(
        route = RHRoute.RideHMIScreen.route
    )
}