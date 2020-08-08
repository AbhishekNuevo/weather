package com.example.myapplication.com.example.myapplication.location

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.example.myapplication.LocationRepository

import com.example.myapplication.R


class LocationEntryFragment : Fragment() {

    private lateinit var locationRepository: LocationRepository


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_location_entry, container, false)

        val textView = view.findViewById<TextView>(R.id.textView1)
        val  zipEdit = view.findViewById<EditText>(R.id.editText1)
        val enterZipButton = view.findViewById<Button>(R.id.button1)

        locationRepository = LocationRepository(requireContext())
        enterZipButton.setOnClickListener {
            val zip = zipEdit.text
            textView.text = zip
            locationRepository.saveLocation(zip.toString())
           findNavController().navigateUp()
        }

        return view
    }


}
