package com.example.myapplication.com.example.myapplication.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.formateDate
import java.lang.IllegalArgumentException

class ForecastDetailFactory(private val args : ForecastDetailFragmentArgs) : ViewModelProvider.Factory{
     override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ForecastDetailViewModel::class.java)){
            return ForecastDetailViewModel(args) as T
        }
         throw IllegalArgumentException("unknown view model ")
     }

 }

class ForecastDetailViewModel(args: ForecastDetailFragmentArgs) : ViewModel() {

     private val _viewState: MutableLiveData<ForecastDetailViewState> = MutableLiveData()
      val viewState : LiveData<ForecastDetailViewState> = _viewState

    init {
        _viewState.value = ForecastDetailViewState(
            temp = args.temp,
            date = formateDate(args.date),
            description = args.description,
            iconUrl = "http://openweathermap.org/img/wn/${args.icon}@2x.png"
        )
    }


}