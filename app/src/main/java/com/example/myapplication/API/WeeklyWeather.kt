package com.example.myapplication.API

import com.squareup.moshi.Json


data class Weather(val main:String,val description:String, val icon:String)

 data class Temp (val max : Float, val min : Float)

 data class DailyWeather(
     @field:Json(name = "dt") val date : Long,
     val temp : Temp,
     val weather: List<Weather>
 )

 data class WeeklyWeather(val daily : List<DailyWeather>)