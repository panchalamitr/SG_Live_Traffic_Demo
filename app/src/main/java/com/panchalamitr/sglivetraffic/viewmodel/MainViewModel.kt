package com.panchalamitr.sglivetraffic.viewmodel

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.haroldadmin.cnradapter.NetworkResponse
import com.panchalamitr.sglivetraffic.model.Camera
import com.panchalamitr.sglivetraffic.respository.DefaultTrafficImagesRepository
import com.panchalamitr.sglivetraffic.respository.TrafficImageRepository
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.*

class MainViewModel @ViewModelInject constructor(
    private val trafficImagesRepository: TrafficImageRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private var syncTimer: Timer? = null
    private val trafficCameras = MutableLiveData<List<Camera>>()
    private val errorResponse = MutableLiveData<String>()
    private val repeatTime = 1 * 60 * 1000L //every 1 min

    fun syncStart() {

        syncTimer = Timer()

        val timerTaskObj: TimerTask = object : TimerTask() {
            override fun run() {
                fetchTrafficData()
            }
        }
        syncTimer!!.schedule(timerTaskObj, 0, repeatTime)
        Timber.d("Start Sync Timer")
    }

    fun syncStop() {
        syncTimer?.cancel()
        Timber.d("Stop Sync Timer")
    }

    fun fetchTrafficData() {
        Timber.d("fetchTrafficData")

        /**
         * Used viewModelScope, so this scope will be cancelled when ViewModel will be cleared
         * Dispatchers.Main is set as the default CoroutineDispatcher for viewModelScope.
         **/
        viewModelScope.launch {
            when (val result = trafficImagesRepository.getTrafficImages()) {

                is NetworkResponse.Success -> {
                    result?.let {
                        if (it.body.apiInfo.status == "healthy") {
                            trafficCameras.postValue(it.body?.items[0]?.cameras)
                        }
                    }
                }

                is NetworkResponse.ServerError -> {
                    result?.let {
                        errorResponse.postValue(it.body?.message)
                    }
                }

                /** When no internet connection **/
                is NetworkResponse.NetworkError -> {
                    result?.let {
                        errorResponse.postValue(it.error.message)
                    }
                }

                /** When no internet connection **/
                is NetworkResponse.UnknownError -> {
                    result?.let {
                        errorResponse.postValue(it.error.message)
                    }
                }
            }
        }
    }

    fun observeTrafficCameras(): LiveData<List<Camera>> {
        return trafficCameras
    }

    fun observeErrorResponse(): LiveData<String> {
        return errorResponse
    }

}