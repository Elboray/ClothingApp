package com.example.suggeterapp

import okhttp3.*
import java.io.IOException

object API_Request {
    fun Get_API_Request(onResponse: (response: Response) -> Unit,
                        onFailure: () -> Unit,){
        val client=OkHttpClient()
        val url=HttpUrl.Builder().scheme("https")
            .host("api.tomorrow.io")
            .addPathSegment("v4")
            .addPathSegment("timelines")
            .addQueryParameter("location", "30.0444,31.2357")
            .addQueryParameter("fields", "temperature,humidity,windSpeed,cloudCover,weatherCode")
            .addQueryParameter("timesteps", "current")
            .addQueryParameter("units", "metric")
            .addQueryParameter("apikey", "Wk299fLoNufHCZG1reJeBSmaG29zdGEJ")
            .build()

        val request=Request.Builder().url(url).build()
        client.newCall(request).enqueue(object :Callback{
            override fun onFailure(call: Call, e: IOException) =  onFailure()

            override fun onResponse(call: Call, response: Response) = onResponse(response)


        })
    }


}