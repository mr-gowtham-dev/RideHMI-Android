package com.android.ridehmi_android.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.ridehmi_android.common.BlinkingIndicatorType
import com.android.ridehmi_android.model.repository.RHRideHMIDashboardRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for managing Ride HMI (Human-Machine Interface) logic.
 * This ViewModel handles battery percentage, motor state, speed, ETA, and blinking indicators.
 *
 * @property batteryRepository Repository to fetch battery percentage updates.
 */
@HiltViewModel
class RHRideHMIViewModel @Inject constructor(
    private val batteryRepository: RHRideHMIDashboardRepository
) : ViewModel() {
    // Use MutableStateFlow to hold the state
    private val _isMotorOn = MutableStateFlow(false)
    val isMotorOn: StateFlow<Boolean> get() = _isMotorOn

    // State to track which indicator is blinking
    private var _blinkingIndicator = MutableStateFlow<BlinkingIndicatorType?>(null)
    val blinkingIndicator: StateFlow<BlinkingIndicatorType?> get() = _blinkingIndicator

    // State to track battery percentage
    private val _batteryPercentage = MutableStateFlow(0)
    val batteryPercentage: StateFlow<Int> = _batteryPercentage.asStateFlow()

    // State for speed
    private val _speed = MutableStateFlow(0f)
    val speed: StateFlow<Float> = _speed.asStateFlow()

    // State for animation status
    private val _isAnimating = MutableStateFlow(true)
    val isAnimating: StateFlow<Boolean> get() = _isMotorOn

    // State for ETA
    private val _etaMinutes = MutableStateFlow(0)
    val etaMinutes: StateFlow<Int> = _etaMinutes.asStateFlow()

    /**
     * Initializes the ViewModel by collecting battery percentage updates.
     */
    init {
        viewModelScope.launch {
            batteryRepository.getBatteryPercentage()
                .collect { level ->
                    _batteryPercentage.value = level
                }
        }
    }

    /**
     * Toggles the motor state between ON and OFF.
     * If turned OFF, it stops the blinking indicator and resets speed & ETA.
     * If turned ON, it starts the ETA countdown.
     */
    fun toggleMotor() {
        viewModelScope.launch {
            _isMotorOn.value = !_isMotorOn.value
            if (!_isMotorOn.value) {
                _blinkingIndicator.value = null
                resetSpeedAndETA()
            } else {
                startETACountdown()
            }
        }
    }

    /**
     * Toggles the blinking state of an indicator.
     * The indicator blinks only if the motor is ON.
     *
     * @param indicator The indicator to toggle.
     */
    fun toggleIndicator(indicator: BlinkingIndicatorType) {
        if (!_isMotorOn.value) return
        _blinkingIndicator.value = if (_blinkingIndicator.value == indicator) {
            null // Stop blinking if the same indicator is clicked again
        } else {
            indicator // Start blinking the clicked indicator
        }
    }

    /**
     * Sets the current speed.
     *
     * @param speed The speed value to set.
     */
    fun startSpeedSet(speed: Float) {
        _speed.value = speed
    }

    /**
     * Starts a countdown for ETA (Estimated Time of Arrival).
     * The countdown decreases ETA by 1 minute every 10 seconds.
     */
    private fun startETACountdown() {
        viewModelScope.launch {
            _etaMinutes.value = 30
            while (_isMotorOn.value && _etaMinutes.value > 0) {
                delay(10000) // Wait for 10 seconds
                if (_etaMinutes.value > 0) _etaMinutes.value -= 1
            }
        }
    }

    /**
     * Resets speed and ETA values to their initial states.
     */
    private fun resetSpeedAndETA() {
        _speed.value = 0f
        _etaMinutes.value = 0
        _isAnimating.value = true
    }

    /**
     * Sets the animation state.
     *
     * @param isAnimating Boolean indicating whether the animation is active.
     */
    fun setAnimatingState(isAnimating: Boolean) {
        _isAnimating.value = isAnimating
    }
}