package com.panchalamitr.sglivetraffic.repository

import com.google.gson.GsonBuilder
import com.haroldadmin.cnradapter.NetworkResponse
import com.panchalamitr.sglivetraffic.model.ErrorTrafficImages
import com.panchalamitr.sglivetraffic.model.TrafficData
import com.panchalamitr.sglivetraffic.respository.TrafficImageRepository
import com.panchalamitr.sglivetraffic.utils.FileUtils
import java.io.IOException

class FakeTrafficImagesRepository : TrafficImageRepository {

    private lateinit var networkResponse : NetworkResponse<TrafficData, ErrorTrafficImages>

    fun networkType(_networkResponse: NetworkResponse<TrafficData, ErrorTrafficImages>) {
        networkResponse = _networkResponse
    }

    override suspend fun getTrafficImages(): NetworkResponse<TrafficData, ErrorTrafficImages> {
        var response = ""
        return when(networkResponse){
            is NetworkResponse.Success -> {
                response = FileUtils.readTestResourceFile("success_response.json")
                val data = GsonBuilder().create().fromJson(response, TrafficData::class.java)
                NetworkResponse.Success(data, null,101)
            }
            is NetworkResponse.ServerError -> {
                response = FileUtils.readTestResourceFile("invalid_date_format_response.json")
                val data = ErrorTrafficImages(response)
                NetworkResponse.ServerError(data, 101)
            }
            is NetworkResponse.NetworkError -> {
                val ioException = IOException("No Internet Connection Found")
                NetworkResponse.NetworkError(ioException)
            }
            is NetworkResponse.UnknownError -> {
                val ioException = UnknownError("Unknown Error")
                NetworkResponse.UnknownError(ioException)
            }
        }
    }
}