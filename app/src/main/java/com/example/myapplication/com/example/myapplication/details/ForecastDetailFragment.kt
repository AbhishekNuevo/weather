package com.example.myapplication.com.example.myapplication.details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import coil.api.load
import com.example.myapplication.R
import com.example.myapplication.TempDisplaySettingManager
import com.example.myapplication.databinding.FragmentForecastDetailBinding
import com.example.myapplication.formateDate
import com.example.myapplication.formateTempForDisplay

class ForecastDetailFragment : Fragment() {

    private lateinit var tempDisplaySettingManager: TempDisplaySettingManager
    private var _binding: FragmentForecastDetailBinding? = null
    private val binding get() = _binding!!
    private val args: ForecastDetailFragmentArgs by navArgs()

    private lateinit var viewModelFactory : ForecastDetailFactory

    private val viewModel : ForecastDetailViewModel by viewModels(
        factoryProducer = {viewModelFactory}
    )
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentForecastDetailBinding.inflate(inflater, container, false)

        tempDisplaySettingManager = TempDisplaySettingManager(requireContext())
        viewModelFactory = ForecastDetailFactory(args)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewStateObserver  = Observer<ForecastDetailViewState>{viewState ->
            binding.tvTemp.text =  formateTempForDisplay(viewState.temp, tempDisplaySettingManager.getTempDisplaySetting())
            binding.tvDescription.text = viewState.description
            binding.tvDate.text = viewState.date
            binding.imageVIew.load(viewState.iconUrl)
        }
        viewModel.viewState.observe(viewLifecycleOwner,viewStateObserver)


    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}
