package com.ba.asteroidradar.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ba.asteroidradar.PictureOfDay
import java.util.*

@Dao
interface AsteroidsDAO {

    @Query("select * from DatabaseAsteroid")
    fun getAllAsteroids() : List<DatabaseAsteroid>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg asteroids: DatabaseAsteroid)

    @Query("select * from DatabaseAsteroid where closeApproachDate >= :startDate and closeApproachDate <= :endDate order by closeApproachDate asc ")
    fun getAsteroidByCloseApproachDate(startDate : String, endDate: String) : List<DatabaseAsteroid>
}
