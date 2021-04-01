/**
 *
 * LocationActivity
 *
 * 2021/03/15 YHZ Create New
 *
 * Load Google Map Information
 */
package com.example.roomforrent.activity

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import com.example.roomforrent.R
import com.example.roomforrent.utils.Constants
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_location.*

class LocationActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        setContentView(R.layout.activity_location)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        setupActionBar()
    }

    private fun setupActionBar(){
        setSupportActionBar(toolbar_location_activity)
        var actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24)
            actionBar.title= ""
        }
        toolbar_location_activity.setNavigationOnClickListener { onBackPressed() }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val latitude: Double = intent.getDoubleExtra(Constants.LATITUDE, 0.0)
        val longitude: Double = intent.getDoubleExtra(Constants.LONGITUDE, 0.0)

        // Add a marker in Sydney and move the camera
        val location = LatLng(latitude, longitude)
        mMap.addMarker(MarkerOptions().position(location)
            .icon(bitmapDescriptorFromVector(this, R.drawable.ic_baseline_home_24))
            .title("Current Location"))
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 16.0f));
    }

    private fun bitmapDescriptorFromVector(
        context: Context,
        @DrawableRes vectorDrawableResourceId: Int
    ): BitmapDescriptor? {
        val background = ContextCompat.getDrawable(context, R.drawable.ic_baseline_home_24)
        background!!.setBounds(0, 0, background.intrinsicWidth, background.intrinsicHeight)
        val vectorDrawable = ContextCompat.getDrawable(context, vectorDrawableResourceId)
        vectorDrawable!!.setBounds(
            40,
            20,
            vectorDrawable.intrinsicWidth + 40,
            vectorDrawable.intrinsicHeight + 20
        )
        val bitmap = Bitmap.createBitmap(
            background.intrinsicWidth,
            background.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        background.draw(canvas)
        vectorDrawable.draw(canvas)
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }
}