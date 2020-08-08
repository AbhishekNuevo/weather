package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.example.myapplication.API.DailyWeather
import com.example.myapplication.API.WeeklyWeather


class DailyForecastViewHolder(
    view: View,
    private val tempDisplaySettingManager: TempDisplaySettingManager
) : RecyclerView.ViewHolder(view) {
    val temp = view.findViewById<TextView>(R.id.tv_temp);
    val description = view.findViewById<TextView>(R.id.tv_description);
    val date = view.findViewById<TextView>(R.id.tv_date)
    val icon = view.findViewById<ImageView>(R.id.imageView)

    fun bind(dailyWeather: DailyWeather) {
        temp.text = formateTempForDisplay(
            dailyWeather.temp.max,
            tempDisplaySettingManager.getTempDisplaySetting()
        )
        description.text = dailyWeather.weather[0].description
        date.text = formateDate(dailyWeather.date)
          val weatherIcon = dailyWeather.weather[0].icon
        icon.load("http://openweathermap.org/img/wn/$weatherIcon@2x.png")

    }
}


class ForecastAdapter( private val selectItem : (dailyWeather: DailyWeather) -> Unit , private val tempDisplaySettingManager: TempDisplaySettingManager) :
    ListAdapter<DailyWeather, DailyForecastViewHolder>(DIFF_CONFIG) {

    companion object {
        val DIFF_CONFIG = object : DiffUtil.ItemCallback<DailyWeather>() {
            override fun areItemsTheSame(
                oldItem: DailyWeather,
                newItem: DailyWeather
            ): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(
                oldItem: DailyWeather,
                newItem: DailyWeather
            ): Boolean {
                return oldItem == newItem
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyForecastViewHolder {

        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.forecast_item_layout, parent, false)
        return DailyForecastViewHolder(itemView, tempDisplaySettingManager)

    }

    override fun onBindViewHolder(holder: DailyForecastViewHolder, position: Int) {

        holder.bind( getItem(position))
        holder.itemView.setOnClickListener {
            selectItem(getItem(position))
        }
    }


}
