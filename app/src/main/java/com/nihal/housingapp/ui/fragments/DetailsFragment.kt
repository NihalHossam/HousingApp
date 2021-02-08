package com.nihal.housingapp.ui.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.nihal.housingapp.BR
import com.nihal.housingapp.R
import com.nihal.housingapp.databinding.FragmentDetailsBinding
import com.nihal.housingapp.models.House
import com.nihal.housingapp.ui.activities.MainActivity
import com.nihal.housingapp.viewmodel.HouseViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint
import java.util.*


@AndroidEntryPoint
class DetailsFragment : Fragment(), OnMapReadyCallback {

    private var map: GoogleMap? = null
    private lateinit var houseViewModel: HouseViewModel
    private val args: DetailsFragmentArgs by navArgs()
    private lateinit var house: House
    private var latitude: Double = 0.0
    private var longitude: Double = 0.0
    private lateinit var binding: FragmentDetailsBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_details, container, false)
        binding.mapView.onCreate(savedInstanceState)
        binding.mapView.getMapAsync(this)
        house = args.house
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
        binding.toolbar.setNavigationIcon(R.drawable.ic_back)
        (activity as AppCompatActivity).supportActionBar?.title = null
        setBackClickListener()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        houseViewModel = (activity as MainActivity).houseViewModel
        binding.setVariable(BR.house, house)
        binding.lifecycleOwner = this
        binding.savedIcon.setOnClickListener {
            houseViewModel.saveHouse(house)
            Toast.makeText(context, "House Saved.", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * Shows the house's location with a pin in the map view.
     * @param googleMap A GoogleMap instance.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        map!!.uiSettings.isMyLocationButtonEnabled = false
        latitude  = house.latitude
        longitude  = house.longitude
        map!!.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(latitude, longitude), 15F))
        map!!.addMarker(MarkerOptions().position(LatLng(latitude, longitude)))
        mapOnClick(map!!)

    }

    /**
     * Opens Google Maps App upon click that starts the navigation from current location to the house selected.
     */
    private fun mapOnClick(map: GoogleMap) {
        map.setOnMapClickListener {
            val uri: String = String.format(
                Locale.ENGLISH,
                "http://maps.google.com/maps?daddr=%f,%f",
                latitude,
                longitude
            )
            val gmmIntentUri = Uri.parse(uri)
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setClassName(
                "com.google.android.apps.maps",
                "com.google.android.maps.MapsActivity"
            )
            startActivity(mapIntent)
        }
    }

    /**
     * Go back to Home Fragment when top back button is clicked.
     */
    private fun setBackClickListener() {
        binding.toolbar.setNavigationOnClickListener {
            activity?.onBackPressed()
        }
    }

    override fun onResume() {
        binding.mapView.onResume()
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
        binding.mapView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        binding.mapView.onLowMemory()
    }

}