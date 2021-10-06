package com.ba.asteroidradar.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

@Database(entities = [DatabaseAsteroid::class], version = 1)
abstract class AsteroidsDatabase : RoomDatabase() {
    abstract val asteroidsDao: AsteroidsDAO
}

private lateinit var INSTANCE: AsteroidsDatabase

fun getDatabase(context: Context): AsteroidsDatabase {
    synchronized(AsteroidsDatabase::class) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                AsteroidsDatabase::class.java, "AsteroidDatabase"
            ).addCallback(roomCallback)
                .build()
        }
    }
    return INSTANCE
}

private val roomCallback = object : RoomDatabase.Callback() {
    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)
        Timber.i("database callback called")
        GlobalScope.launch {
            addDummyAsteroids()
        }
    }
}

suspend fun addDummyAsteroids() {
    withContext(Dispatchers.IO) {
        INSTANCE.asteroidsDao.insertAll(*getDummyAsteroidList().toTypedArray())
        Timber.i(INSTANCE.asteroidsDao.getAllAsteroids().toString())
    }
}


private fun getDummyAsteroidList(): ArrayList<DatabaseAsteroid> {
    val asteroidA = DatabaseAsteroid(
        id = 2465633,
        codeName = "465633 (2009 JR5)",
        closeApproachDate = "2015-09-08",
        absoluteMagnitude = 20.36,
        estimatedDiameter = 0.5035469604,
        relativeVelocity = 18.1279547773,
        distanceFromEarth = 0.3027478814,
        isPotentiallyHazardous = true,
    )

    val asteroidB = DatabaseAsteroid(
        id = 3426410,
        codeName = "(2008 QV11)",
        closeApproachDate = "2015-09-08",
        absoluteMagnitude = 20.36,
        estimatedDiameter = 0.320656449,
        relativeVelocity = 19.7498128142,
        distanceFromEarth = 0.2591250701,
        isPotentiallyHazardous = false,
    )
    return arrayListOf(asteroidA, asteroidB)
}