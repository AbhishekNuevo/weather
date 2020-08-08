package com.example.myapplication

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import java.io.Console

data class ZIPCODE(val zipcode: String)

private const val ZIP_CODE = "zip_code"


class LocationRepository(context: Context) {

    private val preference = context.getSharedPreferences("setting", Context.MODE_PRIVATE)
    private val _savedLocation: MutableLiveData<ZIPCODE> = MutableLiveData()
    val savedLocation: LiveData<ZIPCODE> = _savedLocation

    init {
        preference.registerOnSharedPreferenceChangeListener { sharedPreferences, key ->
            if (key == ZIP_CODE) {
                setInLiveData()
            }

        }
        setInLiveData()
    }

    private fun setInLiveData() {
        if(_savedLocation.value != null ) return

        val zipcode = preference.getString(ZIP_CODE, "")

        if (zipcode != null && zipcode.isNotBlank()) {

            _savedLocation.value = ZIPCODE(zipcode)
        }
    }

    fun saveLocation(zipcode: String) {
        preference.edit().putString(ZIP_CODE, zipcode).apply()
    }
}