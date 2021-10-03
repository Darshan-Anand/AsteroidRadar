package com.ba.asteroidradar.main

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ba.asteroidradar.Asteroid
import com.ba.asteroidradar.database.getDatabase
import com.ba.asteroidradar.repository.AsteroidRepository

class MainViewModel(application: Application) : ViewModel() {

    private val database = getDatabase(application)
    private val repository = AsteroidRepository(database)

    val asteroidsLists = repository.asteroids

    private var _navigateToAsteroidDetails = MutableLiveData<Asteroid?>()

    val navigateToAsteroidDetails : LiveData<Asteroid?>
    get() = _navigateToAsteroidDetails

    fun displayAsteroidDetails(asteroid: Asteroid){
        _navigateToAsteroidDetails.value = asteroid
    }

    fun displayAsteroidDetailsFinished(){
        _navigateToAsteroidDetails.value = null
    }
}