package com.android.ridehmi_android.presentation.screens.riderHMIScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.android.ridehmi_android.R
import com.android.ridehmi_android.ui.theme.DarkGray
import com.android.ridehmi_android.viewmodel.RHRideHMIViewModel

/**
 * Displays the battery status, including an icon and percentage text.
 *
 * @param viewModel The ViewModel providing battery percentage data.
 */
@Composable
fun RHBatteryStatus(viewModel: RHRideHMIViewModel) {
    // Collect state from ViewModel
    val batteryPercentage by viewModel.batteryPercentage.collectAsState()

    // Determine battery color based on percentage level
    val batteryColor = when (batteryPercentage) {
        in 80..100 -> Color.Green // High battery level
        in 40..79 -> Color.Yellow // Medium battery level
        else -> Color.Red // Low battery level
    }

    Row(verticalAlignment = Alignment.CenterVertically) {
        // Battery Icon
        Image(
            painter = painterResource(id = R.drawable.svg_battery_icon),
            contentDescription = "Battery",
            modifier = Modifier.size(24.dp)
        )

        Spacer(modifier = Modifier.width(4.dp))

        // Battery Percentage Text
        Text(
            text = stringResource(id = R.string.battery_percentage, batteryPercentage),
            style = TextStyle(
                color = batteryColor,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
        )
    }
}

/**
 * Displays the estimated time of arrival (ETA) in minutes.
 *
 * @param viewModel The ViewModel providing ETA data.
 */
@Composable
fun RHEta(viewModel: RHRideHMIViewModel) {
    // Collect state from ViewModel
    val etaMinutes by viewModel.etaMinutes.collectAsState()

    // Display ETA text
    Text(
        text = stringResource(id = R.string.eta_minutes, etaMinutes),
        style = TextStyle(
            color = DarkGray,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )
    )
}