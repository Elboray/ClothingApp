package com.example.suggeterapp

import com.google.gson.annotations.SerializedName

data class JsonData(@SerializedName("data") val weatherData: WeatherData)