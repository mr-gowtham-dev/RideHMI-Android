package com.android.ridehmi_android.common

/**
 * Enum class representing navigation routes.
 *
 * @param route The route string for navigation.
 */
enum class RHRoute(val route: String) {
    RideHMIScreen("ride_hmi_screen")
}

/**
 * Enum to represent which indicator is blinking
 */
enum class BlinkingIndicatorType {
    LEFT, RIGHT
}