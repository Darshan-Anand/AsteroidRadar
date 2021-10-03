package com.ba.asteroidradar.main

import android.app.Application
import androidx.lifecycle.ViewModel
import com.ba.asteroidradar.database.getDatabase
import com.ba.asteroidradar.repository.AsteroidRepository

class MainViewModel(application: Application) : ViewModel() {

    private val database = getDatabase(application)
    private val repository = AsteroidRepository(database)

    val asteroidsLists = repository.asteroids

}