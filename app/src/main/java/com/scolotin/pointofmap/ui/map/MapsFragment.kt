package com.scolotin.pointofmap.ui.map

import android.Manifest
import android.location.Geocoder
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.scolotin.pointofmap.R
import com.scolotin.pointofmap.ui.common.SharedViewModel

class MapsFragment : Fragment(), OnMapReadyCallback {

    private lateinit var viewModel: MapsVM
    private val sharedViewModel: SharedViewModel by activityViewModels()

    private lateinit var locationProviderClient: FusedLocationProviderClient
    private lateinit var geocoder: Geocoder

    private val requestPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                viewModel.apply {
                    setMyLocationEnabled(true)
                    moveToMyLocation()
                }
            }
            else {
                showToast(R.string.txt_location_denied)
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        setHasOptionsMenu(true)

        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.btn_marker_list -> {
                view?.let {
                    sharedViewModel.updateData()
                    Navigation.findNavController(it)
                        .navigate(R.id.action_mapsFragment_to_mainFragment)
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onMapReady(map: GoogleMap) {
        requestPermission.launch(Manifest.permission.ACCESS_FINE_LOCATION)

        locationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        geocoder = Geocoder(requireContext())

        val viewModelFactory = MapsVMFactory(map, geocoder, locationProviderClient, sharedViewModel)
        viewModel = ViewModelProvider(requireActivity(), viewModelFactory)[MapsVM::class.java]

        map.setOnMapLongClickListener { latLang ->
            viewModel.addMarker(latLang)
        }
    }

    private fun showToast(textId: Int) {
        Toast.makeText(context, textId, Toast.LENGTH_LONG).show()
    }
}
