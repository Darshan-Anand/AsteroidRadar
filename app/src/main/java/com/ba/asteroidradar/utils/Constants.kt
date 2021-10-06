package com.ba.asteroidradar.utils

import com.ba.asteroidradar.APIKeys.NeoWKey
import retrofit2.http.DELETE

object Constants {
    const val API_QUERY_DATE_FORMAT = "yyyy-MM-dd"
    const val DEFAULT_END_DATE_DAYS = 7
    const val BASE_URL = "https://api.nasa.gov/"
    const val NeoW_Key = NeoWKey.api_key
    const val IMAGE_MEDIA_TYPE = "image"
    const val DELETE_ASTEROIDS_WORK_NAME = "DeleteAsteroidsWork"
}