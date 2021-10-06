package com.ba.asteroidradar

import android.app.Application
import androidx.work.*
import com.ba.asteroidradar.utils.Constants
import com.ba.asteroidradar.work.DeleteAsteroidsWork
import com.ba.asteroidradar.work.RefreshAsteroidsWork
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.concurrent.TimeUnit

class AsteroidRadarApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        setupBackgroundWork()
    }

    private fun setupBackgroundWork() {
        val applicationScope = CoroutineScope(Dispatchers.Default)
        applicationScope.launch {
            val constraints = Constraints.Builder()
                .setRequiresBatteryNotLow(true)
                .setRequiresCharging(true)
                .setRequiredNetworkType(NetworkType.UNMETERED)
                .build()

            val deleteAsteroidWorkRequest =
                PeriodicWorkRequestBuilder<DeleteAsteroidsWork>(1, TimeUnit.DAYS)
                    .setConstraints(constraints)
                    .build()

            WorkManager.getInstance(applicationContext)
                .enqueueUniquePeriodicWork(
                    Constants.DELETE_ASTEROIDS_WORK_NAME,
                    ExistingPeriodicWorkPolicy.KEEP,
                    deleteAsteroidWorkRequest
                )

            val refreshAsteroidWorkRequest =
                PeriodicWorkRequestBuilder<RefreshAsteroidsWork>(1, TimeUnit.DAYS)
                    .setConstraints(constraints)
                    .build()


            WorkManager.getInstance(applicationContext)
                .enqueueUniquePeriodicWork(
                    Constants.DELETE_ASTEROIDS_WORK_NAME,
                    ExistingPeriodicWorkPolicy.KEEP,
                    refreshAsteroidWorkRequest
                )

        }
    }
}