package com.panchalamitr.sglivetraffic.data.remote

import com.haroldadmin.cnradapter.NetworkResponse
import com.panchalamitr.sglivetraffic.model.TrafficData
import com.panchalamitr.sglivetraffic.model.ErrorTrafficImages
import retrofit2.http.GET
import retrofit2.http.Query

interface TrafficImageService {

    @GET("transport/traffic-images")
    suspend fun getTrafficImages(@Query("date_time") dateTime: String):
            NetworkResponse<TrafficData, ErrorTrafficImages>

}