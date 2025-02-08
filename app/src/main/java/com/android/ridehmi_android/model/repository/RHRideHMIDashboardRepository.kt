package com.android.ridehmi_android.model.repository

import android.app.Application
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import com.android.ridehmi_android.common.RHApplication
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

/**
 * Repository interface for fetching ride-related data, such as battery percentage.
 */
interface RHRideHMIDashboardRepository {
    fun getBatteryPercentage(): Flow<Int>
}

/**
 * Implementation of [RHRideHMIDashboardRepository] that listens for battery percentage updates.
 *
 * @param application The application context required for registering a broadcast receiver.
 */
class RHRideHMIDashboardRepositoryImpl @Inject constructor(
    private val application: Application
) : RHRideHMIDashboardRepository {

    override fun getBatteryPercentage(): Flow<Int> = callbackFlow {
        val batteryReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                val level = intent?.getIntExtra(BatteryManager.EXTRA_LEVEL, -1) ?: -1
                trySend(level)  // Send battery level to flow
            }
        }

        val intentFilter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
        // Register the broadcast receiver to listen for battery changes
        application.registerReceiver(batteryReceiver, intentFilter)

        // Cleanup when the Flow collection is stopped
        awaitClose {
            application.unregisterReceiver(batteryReceiver)
        }
    }
}
