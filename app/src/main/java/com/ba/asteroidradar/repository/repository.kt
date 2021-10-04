package com.ba.asteroidradar.repository

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.ba.asteroidradar.Asteroid
import com.ba.asteroidradar.api.NeoWs
import com.ba.asteroidradar.api.parseAsteroidsJsonResult
import com.ba.asteroidradar.database.AsteroidsDatabase
import com.ba.asteroidradar.database.asDatabaseModel
import com.ba.asteroidradar.database.asDomainModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import org.json.JSONObject
import java.util.ArrayList

class AsteroidRepository(private val database: AsteroidsDatabase) {

    val asteroids : LiveData<List<Asteroid>> =
        Transformations.map(database.asteroidsDao.getAsteroid()){
            it.asDomainModel()
        }

    suspend fun refreshAsteroids(){
        var asteroidList: ArrayList<Asteroid>
        withContext(Dispatchers.IO) {
            val asteroidResponseBody: ResponseBody = NeoWs.retrofitService.getAsteroids().await()
            asteroidList = parseAsteroidsJsonResult(JSONObject(asteroidResponseBody.string()))
            database.asteroidsDao.insertAll(*asteroidList.asDatabaseModel())
        }
    }


}