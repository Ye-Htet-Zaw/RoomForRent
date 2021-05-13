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


class ChooseAddressActivity : BaseActivity(), OnMapReadyCallback {

    var share: SharedPreferences?=null
    var editor: SharedPreferences.Editor ?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        share = getSharedPreferences(
            "myPreference",
            Context.MODE_PRIVATE
        )
        editor= share!!.edit()
        setContentView(R.layout.activity_choose_address)
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(map: GoogleMap?) {
        val location = LatLng(16.774720023534744, 96.15881592469528)
        map!!.addMarker(
            MarkerOptions().position(location))

        map.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 16.0f));

        map.setOnMapClickListener { point ->
            map.clear()
            map.addMarker(
                MarkerOptions()
                    .position(LatLng(point.latitude,point.longitude))
                    .icon(
                        BitmapDescriptorFactory
                            .defaultMarker(BitmapDescriptorFactory.HUE_GREEN)
                    )
            )

            editor!!.putString(POST_HOUSE_LATITUDE,point.latitude.toString())
            editor!!.putString(POST_HOUSE_LONGITUDE,point.longitude.toString())
            editor!!.commit()
            Toast.makeText(this,"${point.latitude} and ${point.longitude}",Toast.LENGTH_SHORT).show()
        }
    }
}