package com.example.roomforrent.activity

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import com.example.roomforrent.R
import com.example.roomforrent.utils.Constants.POST_HOUSE_LATITUDE
import com.example.roomforrent.utils.Constants.POST_HOUSE_LONGITUDE
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_choose_address.*


class ChooseAddressActivity : BaseActivity(), OnMapReadyCallback {

    var share: SharedPreferences? = null
    var editor: SharedPreferences.Editor? = null
    var latitude: Double = 0.0
    var longitude: Double = 0.0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        share = getSharedPreferences(
            "myPreference",
            Context.MODE_PRIVATE
        )
        editor = share!!.edit()
        setContentView(R.layout.activity_choose_address)
        setUpToolbar()
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        addrSaveBtn.setOnClickListener {
            editor!!.putString(POST_HOUSE_LATITUDE, latitude.toString())
            editor!!.putString(POST_HOUSE_LONGITUDE, longitude.toString())
            editor!!.commit()
            finish()
        }
    }

    private fun setUpToolbar() {
        setSupportActionBar(toolbar_choose_addr)
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back)
        }
        toolbar_choose_addr.setNavigationOnClickListener { onBackPressed() }
    }

    override fun onMapReady(map: GoogleMap?) {
        val location = LatLng(16.774720023534744, 96.15881592469528)
        map!!.addMarker(
            MarkerOptions().position(location)
        )

        map.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 16.0f));

        map.setOnMapClickListener { point ->
            map.clear()
            map.addMarker(
                MarkerOptions()
                    .position(LatLng(point.latitude, point.longitude))
                    .icon(
                        BitmapDescriptorFactory
                            .defaultMarker(BitmapDescriptorFactory.HUE_GREEN)
                    )
            )
            latitude = point.latitude
            longitude = point.longitude
            Toast.makeText(this, "${point.latitude} and ${point.longitude}", Toast.LENGTH_SHORT)
                .show()
        }
    }
}