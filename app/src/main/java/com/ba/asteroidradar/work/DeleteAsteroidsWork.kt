package com.ba.asteroidradar.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.ba.asteroidradar.database.getDatabase
import com.ba.asteroidradar.repository.AsteroidRepository
import retrofit2.HttpException
import java.sql.SQLException

class DeleteAsteroidsWork(context: Context, workerParameters: WorkerParameters) :
    CoroutineWorker(context, workerParameters) {
    override suspend fun doWork(): Result {
        val dB = getDatabase(applicationContext)
        val repository = AsteroidRepository(dB)

        return try {
            repository.deletePreviousDayAsteroids()
            Result.success()
        } catch (e : SQLException){
            Result.retry()
        }
    }
}

class RefreshAsteroidsWork(context: Context, workerParameters: WorkerParameters) :
    CoroutineWorker(context, workerParameters) {
    override suspend fun doWork(): Result {
        val dB = getDatabase(applicationContext)
        val repository = AsteroidRepository(dB)

        return try {
            repository.refreshAsteroids()
            Result.success()
        } catch (e : HttpException){
            Result.retry()
        }
    }
}