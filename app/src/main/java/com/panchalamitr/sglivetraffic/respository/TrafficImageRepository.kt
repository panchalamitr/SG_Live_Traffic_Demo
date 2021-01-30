package com.panchalamitr.sglivetraffic.respository

import com.haroldadmin.cnradapter.NetworkResponse
import com.panchalamitr.sglivetraffic.model.ErrorTrafficImages
import com.panchalamitr.sglivetraffic.model.TrafficData

interface TrafficImageRepository {

    suspend fun getTrafficImages() : NetworkResponse<TrafficData, ErrorTrafficImages>
}