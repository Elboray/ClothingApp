package com.example.suggeterapp.data.domain

import com.google.gson.annotations.SerializedName

data class JsonData(@SerializedName("data") val weatherData: WeatherData)