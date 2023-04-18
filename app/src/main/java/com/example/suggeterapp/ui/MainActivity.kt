package com.example.suggeterapp.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import com.example.suggeterapp.ui.adapter.ClothAdapter
import com.example.suggeterapp.data.domain.Cloth
import com.example.suggeterapp.data.DataSource
import com.example.suggeterapp.data.domain.JsonData
import com.example.suggeterapp.data.WeatherRange
import com.example.suggeterapp.databinding.ActivityMainBinding
import com.example.suggeterapp.util.APIRequest
import com.example.suggeterapp.util.PrefsUtil
import com.google.gson.Gson
import okhttp3.Response
import kotlin.math.roundToInt
import kotlin.properties.Delegates

class MainActivity : AppCompatActivity() {
    private val dataSource by lazy { DataSource() }
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.switchDarkMode.setOnClickListener {
            darkModeTheme()
        }
        setup()
    }



private fun darkModeTheme(){
  var nightMode by Delegates.notNull<Boolean>()
    var sharedPreferences:SharedPreferences
     var editor:SharedPreferences.Editor
    sharedPreferences=getSharedPreferences("MODE",Context.MODE_PRIVATE)
    nightMode=sharedPreferences.getBoolean("nightMode",false)

    if (nightMode) {
        binding.switchDarkMode.isChecked
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

    }
    if (nightMode) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        editor = sharedPreferences.edit()
        editor.putBoolean("nightMode", false)
    } else {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        editor = sharedPreferences.edit()
        editor.putBoolean("nightMode", true)
    }
    editor.apply()
}

    private fun updateRecycler(temperature: Double, currentDate: String) {

        val selectedClothAdapter = when  {

            currentDate == PrefsUtil.date -> {
                val id = PrefsUtil.clothesId
                val clothes = getClothesById(id)
                ClothAdapter(clothes)
            }
            temperature   in 10.0..15.0 -> {
                if (PrefsUtil.clothesId == 1 && currentDate == PrefsUtil.date) {
                    ClothAdapter(dataSource.clothListFour)
                } else {
                    PrefsUtil.clothesId = 1
                    ClothAdapter(dataSource.clothListOne)
                }
            }
            temperature   in 15.0..25.0 -> {
                if (PrefsUtil.clothesId == 2 && currentDate == PrefsUtil.date) {
                    ClothAdapter(dataSource.clothListFour)
                } else {
                    PrefsUtil.clothesId = 2
                    ClothAdapter(dataSource.clothListTwo)
                }

            }
            temperature   in 25.0..50.0 -> {
                if (PrefsUtil.clothesId == 3 && currentDate == PrefsUtil.date) {
                    ClothAdapter(dataSource.clothListFour)
                } else {
                    PrefsUtil.clothesId = 3
                    ClothAdapter(dataSource.clothListThree)
                }

            }

            else -> {
                if (PrefsUtil.clothesId == 4 && currentDate == PrefsUtil.date) {
                    ClothAdapter(dataSource.clothListFour)
                } else {
                    PrefsUtil.clothesId = 4
                    ClothAdapter(dataSource.clothListFour)
                }
            }
        }

        binding.TeShirtRecycler.adapter = selectedClothAdapter
    }


    private fun getClothesById(id: Int): List<Cloth> {
        return when (id) {
            1 -> dataSource.clothListOne
            2 -> dataSource.clothListTwo
            3 -> dataSource.clothListThree
            else -> dataSource.clothListFour
        }
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
        APIRequest.getApiRequest(::onResponse)
    }

    private fun initializePrefsUtil() {
        PrefsUtil.initPrefsUtil(this)
    }






}