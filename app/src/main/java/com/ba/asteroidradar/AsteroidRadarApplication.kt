package com.ba.asteroidradar

import android.app.Application
import timber.log.Timber

class AsteroidRadarApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}