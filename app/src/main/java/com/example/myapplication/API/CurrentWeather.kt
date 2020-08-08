package com.example.myapplication.API

import com.squareup.moshi.Json


data class Forecast(val temp:Float)
data class  Coordinates(val lon: Float, val lat : Float)
data class CurrentWeather(
    val name : String,
    val coord : Coordinates,
    @field:Json(name = "main") val forecast : Forecast

)