package com.ba.asteroidradar.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.ba.asteroidradar.Asteroid
import com.ba.asteroidradar.database.AsteroidsDatabase
import com.ba.asteroidradar.database.asDomainModel

class Repository(private val database: AsteroidsDatabase) {

    val asteroids : LiveData<List<Asteroid>> =
        Transformations.map(database.asteroidsDao.getAsteroid()){
            it.asDomainModel()
        }

    fun refreshAsteroids(){
        //ToDo: Write behaviour during retrofit implementation
    }
}