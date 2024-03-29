package com.example.suggeterapp.util

import okhttp3.*
import java.io.IOException

object APIRequest {
    fun getApiRequest(onResponse: (response: Response) -> Unit) {
        val client = OkHttpClient()
        val url = HttpUrl.Builder()
            .scheme("https")
            .host("api.tomorrow.io")
            .addPathSegment("v4")
            .addPathSegment("timelines")
            .addQueryParameter("location", "30.0444,31.2357")
            .addQueryParameter("fields", "temperature,humidity,windSpeed,cloudCover,weatherCode")
            .addQueryParameter("timesteps", "current")
            .addQueryParameter("units", "metric")
            .addQueryParameter("apikey", "0ZGMag5N2NzRAPps9WFmetlBbQXZTaNg")
            .build()

        val request = Request.Builder().url(url).build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Error("onFailure")
            }

            override fun onResponse(call: Call, response: Response) {
                onResponse(response)
            }
        })
    }



}