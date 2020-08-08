package com.example.myapplication

import android.content.Context
import androidx.appcompat.app.AlertDialog
import java.text.SimpleDateFormat
import java.util.*


fun formateTempForDisplay(temp:Float,tempDisplaySetting: TempDisplaySetting):String {

   // return

     return when(tempDisplaySetting){ // this is not in use
           TempDisplaySetting.Farenheit -> { String.format("%.2f C°",temp)}
            TempDisplaySetting.Celsius -> {
                val temp = (temp - 32f) * (5f/9f)
                String.format("%.2f C°",temp)
            }
     }
 }

fun formateDate(date : Long): String{
    try {
        val sdf = SimpleDateFormat("MM/dd/yyyy")

        return sdf.format(date)
    } catch (e: Exception) {
        return e.toString()
    }
}


fun showAlertDialog(context:Context,tempDisplaySettingManager: TempDisplaySettingManager){

    val dailogBuilder = AlertDialog.Builder(context)
        .setTitle("Choose display unit")
        .setMessage("Choose which temperature unit to use to display the temperature")
        .setPositiveButton("°F"){_,_->
            tempDisplaySettingManager.updateSetting(TempDisplaySetting.Farenheit)

        }
        .setNegativeButton("°C"){_,_ ->
            tempDisplaySettingManager.updateSetting(TempDisplaySetting.Celsius)

        }
        .setOnDismissListener {

        }
    dailogBuilder.show()
}