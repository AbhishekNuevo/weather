package com.example.myapplication

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.API.CurrentWeather
import com.example.myapplication.API.WeeklyWeather
import com.example.myapplication.API.createOpenWeatherMapService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.random.Random

class ForecastRepository {

    private val _weeklyForecast = MutableLiveData<WeeklyWeather>()
    val weeklyForecast: LiveData<WeeklyWeather> = _weeklyForecast

    private val _todayForecast = MutableLiveData<CurrentWeather>()
    val todayForecast: LiveData<CurrentWeather> = _todayForecast

    fun loadTodayForecast(zipcode: String) {



        val call = createOpenWeatherMapService().currentWeather(zipcode,"metric","1bf8b5e4d4dd1613e9d9667750f317f9")
        call.enqueue(object: Callback<CurrentWeather> {
            override fun onFailure(call: Call<CurrentWeather>, t: Throwable) {
                Log.e(ForecastRepository::class.java.simpleName,"error loading the current weather",t)
            }

            override fun onResponse(
                call: Call<CurrentWeather>,
                response: Response<CurrentWeather>
            ) {
                val weatherResponse = response.body()
                if(weatherResponse != null){
                    _todayForecast.value = weatherResponse

                }
                Log.i(ForecastRepository::class.java.simpleName,"success loading current weather $response ${response.body()}" )

            }

        })
    }




    fun loadWeeklyForecast(zipcode: String){
        val call = createOpenWeatherMapService().currentWeather(zipcode,"metric","1bf8b5e4d4dd1613e9d9667750f317f9")
        call.enqueue(object: Callback<CurrentWeather> {
            override fun onFailure(call: Call<CurrentWeather>, t: Throwable) {
                Log.e(ForecastRepository::class.java.simpleName,"error loading the loadWeeklyForecast2 weather",t)
            }

            override fun onResponse(
                call: Call<CurrentWeather>,
                response: Response<CurrentWeather>
            ) {
                Log.i(ForecastRepository::class.java.simpleName,"success loading loadWeeklyForecast2 weather $response ${response.body()}" )

                val weatherResponse = response.body()
                if(weatherResponse != null){
                   val lat  = weatherResponse.coord.lat
                    val lon = weatherResponse.coord.lon
                    val exclude = "current,minutely,hourly"
                    val units = "metric"
                    val appid = "1bf8b5e4d4dd1613e9d9667750f317f9"

                    val forecastCall = createOpenWeatherMapService().sevenDayForecast(lat,lon,exclude,units,appid)
                    forecastCall.enqueue(object:Callback<WeeklyWeather>{
                        override fun onFailure(call: Call<WeeklyWeather>, t: Throwable) {
                            Log.e(ForecastRepository::class.java.simpleName,"error loading the sevenDayForecast weather",t)
                        }

                        override fun onResponse(
                            call: Call<WeeklyWeather>,
                            response: Response<WeeklyWeather>
                        ) {
                            if(response.body() != null){
                                _weeklyForecast.value = response.body()

                            }
                        }

                    })
                }

            }

        })

    }

    private fun getDescription(temp: Float): String {
        return when (temp) {
            in Float.MIN_VALUE.rangeTo(0f) -> "Anything below does not make sense"
            in 0f.rangeTo(32f) -> "Way too cold"
            in 32f.rangeTo(55f) -> "Colder than I would prefer"
            in 55f.rangeTo(65f) -> "Getting better"
            in 65f.rangeTo(80f) -> "That's sweet spot"
            in 80f.rangeTo(90f) -> "Getting a little warm"
            in 90f.rangeTo(100f) -> "Where is the A/C?"
            in 100f.rangeTo(Float.MAX_VALUE) -> "What is this!"

            else -> "does not compute"
        }
    }
}