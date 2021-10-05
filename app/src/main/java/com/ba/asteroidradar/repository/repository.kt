package com.ba.asteroidradar.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.ba.asteroidradar.Asteroid
import com.ba.asteroidradar.PictureOfDay
import com.ba.asteroidradar.api.NeoWs
import com.ba.asteroidradar.api.parseAsteroidsJsonResult
import com.ba.asteroidradar.database.AsteroidsDatabase
import com.ba.asteroidradar.database.asDatabaseModel
import com.ba.asteroidradar.database.asDomainModel
import com.ba.asteroidradar.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import org.json.JSONObject
import timber.log.Timber
import java.util.ArrayList

class AsteroidRepository(private val database: AsteroidsDatabase) {

    val asteroids: LiveData<List<Asteroid>> =
        Transformations.map(database.asteroidsDao.getAsteroid()) {
            it.asDomainModel()
        }

    suspend fun refreshAsteroids() {
        var asteroidList: ArrayList<Asteroid>
        withContext(Dispatchers.IO) {
            val asteroidResponseBody: ResponseBody =
                NeoWs.retrofitService.getAsteroidsAsync().await()
            asteroidList = parseAsteroidsJsonResult(JSONObject(asteroidResponseBody.string()))
            database.asteroidsDao.insertAll(*asteroidList.asDatabaseModel())
        }
    }

    suspend fun refreshPictureOfDay(): PictureOfDay? {
        var pictureOfDay: PictureOfDay
        withContext(Dispatchers.IO) {
            pictureOfDay = NeoWs.retrofitService.getPhotoOfDayAsync().await()
        }
        Timber.d("url: ${pictureOfDay.url}")
        return if (pictureOfDay.mediaType != Constants.IMAGE_MEDIA_TYPE)
            null
        else
            pictureOfDay
    }


}