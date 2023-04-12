package com.example.suggeterapp

import com.google.gson.annotations.SerializedName


data class Interval(
    @SerializedName("startTime")   val startTime: String,
    @SerializedName("values")  val values: WeatherValues,
)