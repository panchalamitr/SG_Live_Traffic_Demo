package com.panchalamitr.sglivetraffic.common

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

object Constants {

    const val BASE_URL ="https://api.data.gov.sg/v1/"

    const val SG_LAT = 1.290270
    const val SG_LNG = 103.851959

    fun getCurrentTime(): String {
        val df: DateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        return df.format(Calendar.getInstance().time)
    }
}