package com.panchalamitr.sglivetraffic.respository

import com.haroldadmin.cnradapter.NetworkResponse
import com.panchalamitr.sglivetraffic.common.Constants
import com.panchalamitr.sglivetraffic.data.remote.TrafficImageService
import com.panchalamitr.sglivetraffic.model.ErrorTrafficImages
import com.panchalamitr.sglivetraffic.model.TrafficData
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DefaultTrafficImagesRepository @Inject constructor(private val trafficImageService: TrafficImageService) : TrafficImageRepository {

    override suspend fun getTrafficImages(): NetworkResponse<TrafficData, ErrorTrafficImages> {
        lateinit var response: NetworkResponse<TrafficData, ErrorTrafficImages>
        withContext(IO) {
            val currentTimestamp = Constants.getCurrentTime()
            response = trafficImageService.getTrafficImages(currentTimestamp)
        }
        return response
    }
}