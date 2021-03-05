//YHZ
//Test
//Hello
package com.example.roomforrent.activity

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.example.roomforrent.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_house_detail.*

class HouseDetailActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var mMap: GoogleMap
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_house_detail)

        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, android.R.color.transparent)
        window.setBackgroundDrawable(resources.getDrawable(R.drawable.toolbarbg))

        location.setOnClickListener {
            startActivity(Intent(this, LocationActivity::class.java))
        }

        house_information_id.setOnClickListener {
            startActivity(Intent(this, HouseInformationActivity::class.java))
        }

        contract_rules_id.setOnClickListener {
            startActivity(Intent(this, ContractRulesActivity::class.java))

        }

        description_id.setOnClickListener {
            startActivity(Intent(this, DescriptionActivity::class.java))
        }

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.miniMap) as SupportMapFragment
        mapFragment.getMapAsync(this)
        setupActionBar()
    }

    private fun setupActionBar(){
        setSupportActionBar(toolbar_house_detail_activity)
        var actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_backward_icon)
            actionBar.title= "Detail"
        }

        toolbar_house_detail_activity.setNavigationOnClickListener { onBackPressed() }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val location = LatLng(16.844353, 96.128355)
        mMap.addMarker(
            MarkerOptions().position(location)

                .title("Current Location")
        )
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 16.0f));
    }
}
