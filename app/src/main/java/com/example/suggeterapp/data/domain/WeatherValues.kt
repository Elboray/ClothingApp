package com.example.suggeterapp.data.domain

import com.google.gson.annotations.SerializedName

data class WeatherValues(
	@SerializedName("temperature")	val temperature: Double,
	@SerializedName("humidity")	val humidity: Double,
	@SerializedName("windSpeed")	val windSpeed: Double,
	@SerializedName("cloudCover")	val cloudCover: Double,
	@SerializedName("weatherCode")	val weatherCode: Int
)