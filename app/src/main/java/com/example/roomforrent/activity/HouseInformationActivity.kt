/**
 *
 * HouseInformationActivity
 *
 * 2021/03/8 YHZ Create New
 *
 * Load House Information
 */
package com.example.roomforrent.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.roomforrent.R
import com.example.roomforrent.utils.Constants
import kotlinx.android.synthetic.main.activity_house_information.*

class HouseInformationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_house_information)
        if (intent.hasExtra(Constants.NO_OF_GUESTS)){
            val noOfGuest = intent.getIntExtra(Constants.NO_OF_GUESTS, -1).toString()
            no_of_guests_information.text = "Limit "+noOfGuest+" Guests"
        }

        if (intent.hasExtra(Constants.AREA)){
            val area = intent.getIntExtra(Constants.AREA, -1).toString()
            house_area.text = area+" Sqft"
        }

        if (intent.hasExtra(Constants.NO_OF_TOILET)){
            val noOfToilet = intent.getIntExtra(Constants.NO_OF_TOILET, -1).toString()
            no_of_toilet.text = noOfToilet+" Toilets"
        }

        if (intent.hasExtra(Constants.NO_OF_BATH)){
            val noOfBath = intent.getIntExtra(Constants.NO_OF_BATH, -1).toString()
            no_of_bath.text = noOfBath+" Baths"
        }

        if (intent.hasExtra(Constants.NO_OF_BEDROOM)){
            val noOfBedroom = intent.getIntExtra(Constants.NO_OF_BEDROOM, -1).toString()
            no_of_bedroom.text = noOfBedroom+" Rooms"
        } else {
            no_of_bedroom.text = "None"
        }

        if (intent.hasExtra(Constants.WIFI)){
            val noOfWifi = intent.getIntExtra(Constants.WIFI, -1).toString()
            if (noOfWifi == "1"){
                house_wifi.text = "Free"
            }else {
                house_wifi.text = "None"
            }
        }

        if (intent.hasExtra(Constants.NO_OF_AIRCON)){
            val noOfAircon = intent.getIntExtra(Constants.NO_OF_AIRCON, -1).toString()
            no_of_aircon.text = noOfAircon+" Aircons"
        } else {
            no_of_aircon.text = "None"
        }

        if (intent.hasExtra(Constants.NO_OF_FLOOR)){
            val noOfFloor = intent.getIntExtra(Constants.NO_OF_FLOOR, -1).toString()
            no_of_floor.text = noOfFloor+" Floors"
        } else {
            no_of_floor.text = "None"
        }
        setupActionBar()
    }

    //ToolBar
    private fun setupActionBar(){
        setSupportActionBar(toolbar_house_information_activity)
        var actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24)
            actionBar.title = ""
        }
        toolbar_house_information_activity.setNavigationOnClickListener { onBackPressed() }
    }
}