package com.ba.asteroidradar.main

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ba.asteroidradar.Asteroid
import com.ba.asteroidradar.PictureOfDay
import com.ba.asteroidradar.database.getDatabase
import com.ba.asteroidradar.repository.AsteroidRepository
import kotlinx.coroutines.launch

class MainViewModel(val application: Application) : ViewModel() {

    private val database = getDatabase(application)
    private val repository = AsteroidRepository(database)

    private var _asteroidsLists = MutableLiveData<List<Asteroid>>()
    val asteroidsLists: LiveData<List<Asteroid>>
        get() = _asteroidsLists

    private var _showNoNetworkSnackbar = MutableLiveData<Boolean>()
    val showNoNetworkSnackbar: LiveData<Boolean>
        get() = _showNoNetworkSnackbar

    private val _connectedToNetwork = MutableLiveData<Boolean>()
    val connectedToNetwork: LiveData<Boolean>
        get() = _connectedToNetwork

    private var _navigateToAsteroidDetails = MutableLiveData<Asteroid?>()
    val navigateToAsteroidDetails: LiveData<Asteroid?>
        get() = _navigateToAsteroidDetails

    private val _pictureOfDay = MutableLiveData<PictureOfDay>()
    val pictureOfDay: LiveData<PictureOfDay>
        get() = _pictureOfDay

    fun displayAsteroidDetails(asteroid: Asteroid) {
        _navigateToAsteroidDetails.value = asteroid
    }

    fun displayAsteroidDetailsFinished() {
        _navigateToAsteroidDetails.value = null
    }

    fun checkNetworkAndRefresh() {
        _connectedToNetwork.value = false
        refreshAsteroids()
        refreshPictureOfDay()
    }

    private fun refreshAsteroids() {
        checkNetworkConnection()
        if (connectedToNetwork.value == true) {
            viewModelScope.launch {
                repository.refreshAsteroids()
            }
        } else
            _showNoNetworkSnackbar.value = true
    }


    private fun refreshPictureOfDay() {
        checkNetworkConnection()
        if (connectedToNetwork.value == true) {
            viewModelScope.launch {
                _pictureOfDay.value = repository.refreshPictureOfDay()
            }
        }
    }

    fun checkNetworkConnection() {
        val connMgr =
            application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo: NetworkInfo? = connMgr.activeNetworkInfo
        _connectedToNetwork.value = networkInfo?.isConnected
    }

    fun getAsteroidsByWeek() {
        viewModelScope.launch {
            _asteroidsLists.value = repository.getAsteroidsByWeek()
        }
    }

    fun getAsteroidsByToday() {
        viewModelScope.launch {
            _asteroidsLists.value = repository.getAsteroidsByToday()
        }
    }

    fun getSavedAsteroids() {
        viewModelScope.launch {
            _asteroidsLists.value = repository.getSavedAsteroids()
        }
    }

    init {
        viewModelScope.launch {
            _asteroidsLists.value = repository.getAsteroidsByWeek()
        }
        refreshAsteroids()
        refreshPictureOfDay()
    }

}