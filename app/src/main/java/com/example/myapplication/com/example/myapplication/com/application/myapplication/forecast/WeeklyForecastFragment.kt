package com.example.myapplication.com.example.myapplication.com.application.myapplication.forecast

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.*
import com.example.myapplication.API.DailyWeather
import com.example.myapplication.API.WeeklyWeather
import com.google.android.material.floatingactionbutton.FloatingActionButton


class WeeklyForecastFragment : Fragment() {

    val forecastRepository = ForecastRepository()
    private lateinit var forecastAdapter: ForecastAdapter
    private lateinit var tempDisplaySettingManager: TempDisplaySettingManager
    private lateinit var locationRepository: LocationRepository


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_weekly_forecast, container, false)

        val rcDailyForecast = view.findViewById<RecyclerView>(R.id.rc_dailyForecast)
        val fab_location = view.findViewById<FloatingActionButton>(R.id.fab_location)

        tempDisplaySettingManager = TempDisplaySettingManager(requireContext())
        rcDailyForecast.layoutManager = LinearLayoutManager(requireContext())
        locationRepository = LocationRepository(requireContext())

        forecastAdapter = ForecastAdapter(::viewClickEvent, tempDisplaySettingManager)
        rcDailyForecast.adapter = forecastAdapter;

        val forecastObserver = Observer<WeeklyWeather> {weather ->

            forecastAdapter.submitList(weather.daily)
            Toast.makeText(requireContext(), "item loaded ", Toast.LENGTH_SHORT).show();
        }

        forecastRepository.weeklyForecast.observe(viewLifecycleOwner, forecastObserver)

        val locationObserver = Observer<ZIPCODE> {
            forecastRepository.loadWeeklyForecast(it.zipcode)
        }
        locationRepository.savedLocation.observe(viewLifecycleOwner, locationObserver)



        fab_location.setOnClickListener {
           findNavController().navigate(R.id.action_weeklyForecastFragment_to_locationEntryFragment)
        }
        return view
    }


    fun viewClickEvent(dailyWeather: DailyWeather): Unit {

        val action =
            WeeklyForecastFragmentDirections.actionWeeklyForecastFragmentToForecastDetailFragment(
                dailyWeather.temp.max,
                dailyWeather.weather[0].description,
                dailyWeather.weather[0].icon,
                dailyWeather.date

            )
        findNavController().navigate(action)
    }

    companion object {
        const val KEY_ZIPCODE = "key_zipcode"
        fun newInstance(zipcode: String): WeeklyForecastFragment {
            val fragment = WeeklyForecastFragment()
            val args = Bundle()
            args.putString(KEY_ZIPCODE, zipcode)
            fragment.arguments = args
            return fragment
        }


    }

}
