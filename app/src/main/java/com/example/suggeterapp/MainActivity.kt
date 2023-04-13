package com.example.suggeterapp

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.suggeterapp.databinding.ActivityMainBinding
import com.google.gson.Gson
import okhttp3.Response
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity() {
    private val dataSource by lazy { DataSource() }

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setup()
    }





    private fun updateRecycler(temperature: Double, currentDate: String) {
        if (currentDate == PrefsUtil.date) {
            return
        }

        val selectedClothAdapter = when (temperature) {
            in 10.0..35.0 -> {
                if (PrefsUtil.TeShirt == 1) {
                    ClothAdapter(dataSource.Shoes)
                } else {
                    PrefsUtil.TeShirt = 1
                    ClothAdapter(dataSource.TeShirt)
                }
            }
            in 35.0..40.0 -> {
                if (PrefsUtil.TeShirt == 2) {
                    ClothAdapter(dataSource.Shoes)
                } else {
                    PrefsUtil.TeShirt = 2
                    ClothAdapter(dataSource.Pantalon)
                }
            }
            else -> {
                if (PrefsUtil.TeShirt == 3) {
                    ClothAdapter(dataSource.Shoes)
                } else {
                    PrefsUtil.TeShirt = 3
                    ClothAdapter(dataSource.Shoes)
                }
            }
        }

        binding.TeShirtRecycler.adapter = selectedClothAdapter
    }





    private fun onResponse(response: Response) {
        response.body?.string()?.let { jsonString ->
            val result = Gson().fromJson(jsonString, JsonData::class.java)
            with(result.weatherData) {
                val currentDate = getStartTime().getDateOnly()
                val temperature = getTemperature()
                val humidity = getHumidity()
                val windSpeed = getWindSpeed()
                val cloudCover = getCloudCover()
                val weatherCode = getWeatherCode()

                runOnUiThread {
                    updateUIData(temperature, humidity, windSpeed, cloudCover, weatherCode)
                    updateRecycler(temperature, currentDate)
                    PrefsUtil.date = currentDate
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun updateUIData(
        temperature: Double,
        humidity: Double,
        windSpeed: Double,
        cloudCover: Double,
        weatherRange: Int
    ) {
        val roundedTemperature = temperature.roundToInt()
        binding.temperature.text = "$roundedTemperature °"
        binding.humidityValue.text = "$humidity %"
        binding.windValue.text = "$windSpeed M/S"
        binding.cloudCoverValue.text = "$cloudCover %"
        binding.state.text = WeatherRange.weatherrange[weatherRange]
    }



    private fun String.getDateOnly(): String {
        val dateLength = 10
        return this.take(dateLength).trim()
    }









    private fun setup() {
        initializeAPIRequest()
        initializePrefsUtil()
    }

    private fun initializeAPIRequest() {
        API_Request.Get_API_Request(::onResponse)
    }

    private fun initializePrefsUtil() {
        PrefsUtil.initPrefsUtil(this)
    }

}