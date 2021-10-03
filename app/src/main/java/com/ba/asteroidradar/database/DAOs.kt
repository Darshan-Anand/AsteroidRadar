package com.ba.asteroidradar.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AsteroidsDAO {

    @Query("select * from DatabaseAsteroids")
    fun getAsteroid() : LiveData<List<DatabaseAsteroids>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg asteroids: DatabaseAsteroids)
}