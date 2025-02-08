package com.android.ridehmi_android.di

import android.app.Application
import com.android.ridehmi_android.common.RHApplication
import com.android.ridehmi_android.model.repository.RHRideHMIDashboardRepository
import com.android.ridehmi_android.model.repository.RHRideHMIDashboardRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Dagger Hilt module for providing application-wide dependencies.
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideBatteryRepository(app: Application): RHRideHMIDashboardRepository {
        return RHRideHMIDashboardRepositoryImpl(app)
    }
}
