package com.ba.asteroidradar.api

import com.ba.asteroidradar.utils.Constants
import com.ba.asteroidradar.PictureOfDay
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.ResponseBody
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET


private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(Constants.BASE_URL)
    .build()


interface NeoApiService {
    @GET("planetary/apod?api_key=${Constants.NeoW_Key}")
    fun getPhotoOfDayAsync(): Deferred<PictureOfDay>

    @GET("neo/rest/v1/feed?api_key=${Constants.NeoW_Key}")
    fun getAsteroidsAsync(): Deferred<ResponseBody>

}

object NeoWs {
    val retrofitService: NeoApiService by lazy { retrofit.create(NeoApiService::class.java) }
}