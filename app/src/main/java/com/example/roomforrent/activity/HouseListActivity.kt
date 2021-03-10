package com.example.roomforrent.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roomforrent.adapter.HouseItemAdapter
import com.example.roomforrent.R
import com.example.roomforrent.models.House
import com.example.roomforrent.models.HouseList
import com.example.roomforrent.services.HouseListService
import com.example.roomforrent.services.SearchRoomService
import com.example.roomforrent.services.ServiceBuilder
import com.example.roomforrent.utils.Constants
import com.example.roomforrent.utils.Constants.Amount
import com.example.roomforrent.utils.Constants.CALLAPI
import com.example.roomforrent.utils.Constants.GetAllRoomList
import com.example.roomforrent.utils.Constants.GetRoomListByCategoryAndTownShip
import com.example.roomforrent.utils.Constants.SelectedAddress
import com.example.roomforrent.utils.Constants.SelectedCategory
import com.example.roomforrent.utils.Constants.SelectedPeroid
import kotlinx.android.synthetic.main.activity_house_list.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HouseListActivity : AppCompatActivity() {

    lateinit var adapter: HouseItemAdapter
    var serviceName: String = ""
    var selectedCategory: String = ""
    var selectedAddress: String = ""
    var selectedPeroid: String = ""
    var amount: String = ""

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_house_list)

        initAdapter()

        //loadAccount()

        //get parameter value and service name form SearchFragment
        getIntentValue()

        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, android.R.color.transparent)
        window.setBackgroundDrawable(resources.getDrawable(R.drawable.toolbarbg))


        setupActionBar()
    }

    //get parameter value and service name form SearchFragment
    private fun getIntentValue() {
        //service name
        serviceName = intent.extras?.getString(CALLAPI).toString()

        //parameter value
        selectedCategory = intent.extras?.getString(SelectedCategory).toString()
        selectedAddress = intent.extras?.getString(SelectedAddress).toString()
        selectedPeroid = intent.extras?.getString(SelectedPeroid).toString()
        amount = intent.extras?.getString(Amount).toString()

        //call api with service name
        checkServiceName(serviceName)
    }


    private fun checkServiceName(serviceName: String) {
        val searchRoomService = ServiceBuilder.buildService(SearchRoomService::class.java)
        when (serviceName) {
            GetAllRoomList -> {
                val callGetAllRoomList: Call<List<House>> = searchRoomService.getAllRoomList()
                callSearchService(callGetAllRoomList)
            }

            GetRoomListByCategoryAndTownShip -> {
                val callGetRoomListByCategoryAndTownShip: Call<List<House>> =
                    searchRoomService.getRoomListByCategoryAndTownShip(
                        selectedCategory,
                        selectedAddress
                    )
                callSearchService(callGetRoomListByCategoryAndTownShip)
            }

        }
    }

    private fun callSearchService(callGetRoomList: Call<List<House>>) {
        callGetRoomList.enqueue(object : Callback<List<House>> {
            override fun onResponse(call: Call<List<House>>, response: Response<List<House>>) {
                if (response.isSuccessful) {
                    val houseList = response.body()!! as List<House>
                    Log.d("Response", "houseList size : ${houseList.size}")
                    adapter.setData(houseList as ArrayList<House>)
                } else {
                    Toast.makeText(
                        this@HouseListActivity,
                        "Something went wrong ${response.message()}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<List<House>>, t: Throwable) {
                Log.d("Response", "fail service call")
            }
        })
    }

    private fun initAdapter() {
        adapter = HouseItemAdapter(this)

        // Set the LayoutManager that this RecyclerView will use.
        recycler_view_items.layoutManager = LinearLayoutManager(this)
        recycler_view_items.adapter = adapter

        adapter.setOnClickListener(object : HouseItemAdapter.OnClickListener {
            override fun onClick() {
                startActivity(Intent(this@HouseListActivity, HouseDetailActivity::class.java))
            }
        })
    }


    private fun loadAccount() {
        //initiate the service
        val destinationService = ServiceBuilder.buildService(HouseListService::class.java)
        val requestCall = destinationService.getHouseList()
        //make network call asynchronously
        requestCall.enqueue(object : Callback<List<HouseList>> {
            override fun onResponse(
                call: Call<List<HouseList>>,
                response: Response<List<HouseList>>
            ) {
                Log.d("Response", "onResponse: ${response.body()}")
                if (response.isSuccessful) {
                    val houseList = response.body()!!
                    Log.d("Response", "houseList size : ${houseList.size}")
                    adapter.setData(response.body()!! as ArrayList<House>)
                } else {
                    Toast.makeText(
                        this@HouseListActivity,
                        "Something went wrong ${response.message()}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<List<HouseList>>, t: Throwable) {
                Toast.makeText(
                    this@HouseListActivity,
                    "Something went wrong $t",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    @SuppressLint("ResourceAsColor")
    private fun setupActionBar() {
        setSupportActionBar(toolBarMain)
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_backward_icon)
            actionBar.title = "House List"
        }
        toolBarMain.setNavigationOnClickListener { onBackPressed() }
    }


}