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
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.ListPopupWindowCompat
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.*
import com.example.myapplication.API.CurrentWeather
import com.google.android.material.floatingactionbutton.FloatingActionButton


class ForecastFragment : Fragment() {

    val forecastRepository = ForecastRepository()
    private lateinit var tempDisplaySettingManager: TempDisplaySettingManager
    private lateinit var locationRepository: LocationRepository


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_forecast, container, false)


        val fab_location = view.findViewById<FloatingActionButton>(R.id.fab_location)
        val tv_place_name = view.findViewById<TextView>(R.id.tv_place_name)
        val tv_temp = view.findViewById<TextView>(R.id.tv_temp)

        tempDisplaySettingManager = TempDisplaySettingManager(requireContext())

        locationRepository = LocationRepository(requireContext())




        val forecastObserver = Observer<CurrentWeather> {currentWeather ->
            val temp = formateTempForDisplay(currentWeather.forecast.temp,tempDisplaySettingManager.getTempDisplaySetting())
            val city = currentWeather.name
            tv_temp.text = temp; tv_place_name.text = city

            Toast.makeText(requireContext(), "item loaded ", Toast.LENGTH_SHORT).show()
        }
        forecastRepository.todayForecast.observe(viewLifecycleOwner, forecastObserver)

        val locationObserver = Observer<ZIPCODE> {
            forecastRepository.loadTodayForecast(it.zipcode)
        }
        locationRepository.savedLocation.observe(viewLifecycleOwner, locationObserver)



        fab_location.setOnClickListener {
            findNavController().navigate(R.id.action_forecastFragment_to_locationEntryFragment)
        }
        return view
    }


    fun viewClickEvent(dailyForecast: DailyForecast): Unit {

//        val action = ForecastFragmentDirections.actionForecastFragmentToForecastDetailFragment(
//            dailyForecast.temp,
//            dailyForecast.description
//        )
//        findNavController().navigate(action)
    }

    companion object {
        const val KEY_ZIPCODE = "key_zipcode"
        fun newInstance(zipcode: String): ForecastFragment {
            val fragment = ForecastFragment()
            val args = Bundle()
            args.putString(KEY_ZIPCODE, zipcode)
            fragment.arguments = args
            return fragment
        }


    }

}
