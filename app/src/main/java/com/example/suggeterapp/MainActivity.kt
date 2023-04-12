package com.example.suggeterapp

import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.util.rangeTo
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
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



    private fun updateRecycler(temperature: Double, CurrentData: String) {
        if (temperature in 10.0..35.0 && CurrentData != PrefsUtil.date && PrefsUtil.TeShirt != 1) {
            val ClothListOne: ClothAdapter = ClothAdapter(dataSource.TeShirt)
            binding.TeShirtRecycler.adapter = ClothListOne
            PrefsUtil.TeShirt = 1

        } else if (temperature in 35.0..40.0 && CurrentData != PrefsUtil.date && PrefsUtil.TeShirt != 2) {
            val ClothListTwo: ClothAdapter = ClothAdapter(dataSource.Pantalon)
            binding.TeShirtRecycler.adapter = ClothListTwo
            PrefsUtil.TeShirt = 2

        } else if (temperature in 35.0..40.0 && CurrentData != PrefsUtil.date && PrefsUtil.TeShirt != 3) {
            val ClothListThree: ClothAdapter = ClothAdapter(dataSource.Shoes)
            binding.TeShirtRecycler.adapter = ClothListThree
            PrefsUtil.TeShirt = 3
        }
        else{
            val default = ClothAdapter(dataSource.Shoes)
            binding.TeShirtRecycler.adapter = default
        }
    }


    private fun onResponse(response: Response) {
        response.body?.string()?.let { JsonStrin ->
            val result = Gson().fromJson(JsonStrin, JsonData::class.java)
            val currentDate = result.weatherData.getStartTime().dateOnly()
            val temperature = result.weatherData.getTemperature()
            val humidity = result.weatherData.getHumidity()
            val windSpeed = result.weatherData.getWindSpeed()
            val cloudCover = result.weatherData.getCloudCover()
            val weatherCode = result.weatherData.getWeatherCode()

            runOnUiThread {
                updateUIData(temperature, humidity, windSpeed, cloudCover, weatherCode)
                updateRecycler(temperature, currentDate)
                PrefsUtil.date = currentDate

            }

        }

    }

    private fun onFailure() {
        if (!isNetworkAvailable()) {
            binding.noInternet.visibility = android.view.View.VISIBLE
        }
    }

    private fun updateUIData(
        temperature: Double,
        humidity: Double,
        windSpeed: Double,
        cloudCover: Double,
        weatherRange: Int
    ) {
        binding.temperature.text = temperature.roundToInt().toString().plus(" °")
        binding.humidityValue.text = humidity.toString().plus(" %")
        binding.windValue.text = windSpeed.toString().plus(" M/S")
        binding.cloudCoverValue.text = cloudCover.toString().plus(" %")
        binding.state.text = WeatherRange.weatherrange[weatherRange]

    }

    private fun String.dateOnly(): String {
        return this.substring(0, 10).trim()
    }


    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = getSystemService(ConnectivityManager::class.java)
        val currentNetwork = connectivityManager.activeNetwork
        val capabilities = connectivityManager.getNetworkCapabilities(currentNetwork)
        return capabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true
    }

    private fun setup() {
        API_Request.Get_API_Request(::onResponse, ::onFailure)
        PrefsUtil.initPrefsUtil(this)
    }
}