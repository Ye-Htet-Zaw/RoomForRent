/**
 *
 * ContractRulesActivity
 *
 * 2021/03/8 YHZ Create New
 *
 * Load House Detail Information
 */
package com.example.roomforrent.activity

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.example.roomforrent.R
import com.example.roomforrent.models.HouseDetails
import com.example.roomforrent.utils.Constants
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.squareup.picasso.Picasso
import com.synnapps.carouselview.ImageListener
import kotlinx.android.synthetic.main.activity_house_detail.*
import kotlinx.android.synthetic.main.activity_house_information.*
import kotlinx.android.synthetic.main.activity_location.*

class HouseDetailActivity : BaseActivity(), OnMapReadyCallback {
    private lateinit var mMap: GoogleMap
    private lateinit var houseDetails: HouseDetails
    private var houseImage: ArrayList<String>? = null

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_house_detail)
        loadHouseDetailsData()
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, android.R.color.transparent)
        window.setBackgroundDrawable(resources.getDrawable(R.drawable.toolbarbg))
        setupActionBar()
    }

    private fun loadHouseDetailsData() {
        if (intent.hasExtra(Constants.HOUSE_DETAIL)) {
            houseDetails = intent.getParcelableExtra(Constants.HOUSE_DETAIL)!!
        }

        //Bind House Image
        houseImage =  houseDetails.house_image

        var imageListener: ImageListener = object : ImageListener{
            override fun setImageForPosition(position: Int, imageView: ImageView?) {
                Picasso.get().load(houseImage!![position]).into(imageView)
            }
        }

        carouselView.pageCount= houseImage!!.size
        carouselView.setImageListener(imageListener)

        house_Address.text = houseDetails.house_address
        no_of_guests_detail.text = "Limit " + houseDetails.no_of_guests + " Guests"

        //House Information
        house_information_id.setOnClickListener {
            var intent = Intent(
                this@HouseDetailActivity,
                HouseInformationActivity::class.java
            )
            intent.putExtra(Constants.NO_OF_GUESTS, houseDetails.no_of_guests)
            intent.putExtra(Constants.AREA, houseDetails.area)
            intent.putExtra(Constants.NO_OF_TOILET, houseDetails.no_of_toilet)
            intent.putExtra(Constants.NO_OF_BATH, houseDetails.no_of_bath)
            intent.putExtra(Constants.NO_OF_BEDROOM, houseDetails.no_of_room)
            intent.putExtra(Constants.WIFI, houseDetails.wifi)
            intent.putExtra(Constants.NO_OF_AIRCON, houseDetails.no_of_aircon)
            intent.putExtra(Constants.NO_OF_FLOOR, houseDetails.no_of_floor)

            startActivity(intent)
        }

        recommended_point.text = houseDetails.recommented_points

        //Contract Rule
        contract_rules_id.setOnClickListener {
            var intent = Intent(this@HouseDetailActivity, ContractRulesActivity::class.java)
            intent.putExtra(Constants.CONTRACT_RULE, houseDetails.contract_rule)
            startActivity(intent)
        }

        phone_one.text = houseDetails.phone_one
        phone_two.text = houseDetails.phone_two
        house_rent.text = "${houseDetails.rent} MMK per Month"
        house_deposit.text = "${houseDetails.deposit} MMK"

        available_date.text = houseDetails.available_date

        contact_one.setOnClickListener {
            val dialIntent = Intent(Intent.ACTION_DIAL)
            dialIntent.data = Uri.parse("tel:" + houseDetails.phone_one.toString())
            startActivity(dialIntent)
        }

        contact_two.setOnClickListener {
            val dialIntent = Intent(Intent.ACTION_DIAL)
            dialIntent.data = Uri.parse("tel:" + houseDetails.phone_two.toString())
            startActivity(dialIntent)
        }

        location.setOnClickListener {
            var intent = Intent(this@HouseDetailActivity, LocationActivity::class.java)
            intent.putExtra(Constants.LATITUDE, houseDetails.latitude)
            intent.putExtra(Constants.LONGITUDE, houseDetails.longitude)
            startActivity(intent)
        }

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.miniMap) as SupportMapFragment
        mapFragment.getMapAsync(this)
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

        val lattitude = houseDetails.latitude
        val longitude = houseDetails.longitude

        // Add a marker in Sydney and move the camera
        /*val location = LatLng(16.844353, 96.128355)*/
        val location = LatLng(lattitude, longitude)
        mMap.addMarker(
            MarkerOptions().position(location)

                .title("Current Location")
        )
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 16.0f));
    }
}
