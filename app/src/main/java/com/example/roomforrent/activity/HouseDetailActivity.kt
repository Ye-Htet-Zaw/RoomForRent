package com.example.roomforrent.activity

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.roomforrent.R
import com.example.roomforrent.models.HouseDetails
import com.example.roomforrent.services.RoomForRentService
import com.example.roomforrent.services.ServiceBuilder
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
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HouseDetailActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var mMap: GoogleMap
    private lateinit var houseDetails: HouseDetails
    private var houseImage: ArrayList<String>? = null
    private var houDetail: HouseDetails? = null

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

       val mapFragment = supportFragmentManager
            .findFragmentById(R.id.miniMap) as SupportMapFragment
        mapFragment.getMapAsync(this)
        setupActionBar()
        //loadHouseDetails()
        loadHouseDetailsData()
    }

    private fun loadHouseDetailsData() {
        if (intent.hasExtra(Constants.HOUSE_IMAGE)){
            houseImage =  intent.getStringArrayListExtra(Constants.HOUSE_IMAGE)
        }

        if (intent.hasExtra(Constants.HOUSE_ADDRESS)){
            house_Address.text =  intent.getStringExtra(Constants.HOUSE_ADDRESS)
        }

        if (intent.hasExtra(Constants.NO_OF_GUESTS)){
            no_of_guests_detail.text =  intent.getStringExtra(Constants.NO_OF_GUESTS)
        }

        if (intent.hasExtra(Constants.RECOMMENTED_POINT)){
            recommended_point.text =  intent.getStringExtra(Constants.RECOMMENTED_POINT)
        }

        if (intent.hasExtra(Constants.CONTACT_ONE)){
            phone_one.text =  intent.getStringExtra(Constants.CONTACT_ONE)
        }

        if (intent.hasExtra(Constants.CONTACT_TWO)){
            phone_two.text =  intent.getStringExtra(Constants.CONTACT_TWO)
        }

        if (intent.hasExtra(Constants.AMOUNT)){
            house_rent.text =  intent.getStringExtra(Constants.AMOUNT)
        }

        if (intent.hasExtra(Constants.DEPOSIT)){
            house_deposit.text =  intent.getStringExtra(Constants.DEPOSIT)
        }

        if (intent.hasExtra(Constants.AVAILABLE_DATE)){
            available_date.text =  intent.getStringExtra(Constants.AVAILABLE_DATE)
        }


            /*val product  = intent.getParcelableExtra<HouseDetails>(Constants.HOUSE_IMAGE)

            product!!.area
        Toast.makeText(this@HouseDetailActivity, "TEST "+product!!.area, Toast.LENGTH_SHORT).show()*/





        var imageListener: ImageListener = object : ImageListener{
            override fun setImageForPosition(position: Int, imageView: ImageView?) {
                Picasso.get().load(houseImage!![position]).into(imageView)
            }
        }

        carouselView.pageCount= houseImage!!.size
        carouselView.setImageListener(imageListener)
    }

    private fun loadHouseDetails() {
        val destinationService  = ServiceBuilder.buildService(RoomForRentService::class.java)
        val requestCall =destinationService.getHouseDetailById("HOU0000001")
        requestCall.enqueue(object : Callback<HouseDetails> {
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onResponse(call: Call<HouseDetails>, response: Response<HouseDetails>) {
                houseDetails = response.body() as HouseDetails
                house_Address.text = houseDetails.house_address
                no_of_guests_detail.text = "Limit " + houseDetails.no_of_guests + " Guests"

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
            }

            override fun onFailure(call: Call<HouseDetails>, t: Throwable) {
                Toast.makeText(this@HouseDetailActivity, t.message, Toast.LENGTH_SHORT).show()
                Log.e(t.message, "ERROR")
            }
        })
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
