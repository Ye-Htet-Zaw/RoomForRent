package com.example.roomforrent.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.opengl.Visibility
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roomforrent.adapter.HouseItemAdapter
import com.example.roomforrent.R
import com.example.roomforrent.models.House
import com.example.roomforrent.models.HouseDetails
import com.example.roomforrent.models.HouseList
import com.example.roomforrent.services.HouseListService
import com.example.roomforrent.services.RoomForRentService
import com.example.roomforrent.services.SearchRoomService
import com.example.roomforrent.services.ServiceBuilder
import com.example.roomforrent.utils.Constants
import com.example.roomforrent.utils.Constants.Amount
import com.example.roomforrent.utils.Constants.CALLAPI
import com.example.roomforrent.utils.Constants.GetAllRoomList
import com.example.roomforrent.utils.Constants.GetRoomListByAddressAndAmountAndPeriod
import com.example.roomforrent.utils.Constants.GetRoomListByAll
import com.example.roomforrent.utils.Constants.GetRoomListByAmount
import com.example.roomforrent.utils.Constants.GetRoomListByAmountAndPeriod
import com.example.roomforrent.utils.Constants.GetRoomListByCategory
import com.example.roomforrent.utils.Constants.GetRoomListByCategoryAndAddressAndAmount
import com.example.roomforrent.utils.Constants.GetRoomListByCategoryAndAddressAndPeriod
import com.example.roomforrent.utils.Constants.GetRoomListByCategoryAndAmount
import com.example.roomforrent.utils.Constants.GetRoomListByCategoryAndAmountAndPeriod
import com.example.roomforrent.utils.Constants.GetRoomListByCategoryAndPeriod
import com.example.roomforrent.utils.Constants.GetRoomListByCategoryAndTownShip
import com.example.roomforrent.utils.Constants.GetRoomListByPeriod
import com.example.roomforrent.utils.Constants.GetRoomListByTownShipAndPeriod
import com.example.roomforrent.utils.Constants.GetRoomListByTownShipAndRent
import com.example.roomforrent.utils.Constants.GetRoomListByTownship
import com.example.roomforrent.utils.Constants.SelectedAddress
import com.example.roomforrent.utils.Constants.SelectedCategory
import com.example.roomforrent.utils.Constants.SelectedPeroid
import kotlinx.android.synthetic.main.activity_house_detail.*
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

            GetRoomListByCategory ->{
                val callGetRoomListByCategory: Call<List<House>> = searchRoomService.getRoomListByCategory(selectedCategory)
                callSearchService(callGetRoomListByCategory)
            }

            GetRoomListByTownship ->{
                val callGetRoomListByTownship: Call<List<House>> = searchRoomService.getRoomListByTownship(selectedAddress)
                callSearchService(callGetRoomListByTownship)
            }

            GetRoomListByPeriod ->{
                val callGetRoomListByPeriod: Call<List<House>> = searchRoomService.getRoomListByPeroid(3)
                callSearchService(callGetRoomListByPeriod)

            }

            GetRoomListByAmount ->{
                val callGetRoomListByAmount: Call<List<House>> = searchRoomService.getRoomListByAmount(amount.toInt())
                callSearchService(callGetRoomListByAmount)
            }

            GetRoomListByCategoryAndTownShip -> {
                val callGetRoomListByCategoryAndTownShip: Call<List<House>> =
                    searchRoomService.getRoomListByCategoryAndTownShip(
                        selectedCategory,
                        selectedAddress
                    )
                callSearchService(callGetRoomListByCategoryAndTownShip)
            }

            GetRoomListByCategoryAndPeriod ->{
                val callGetRoomListByCategoryAndPeriod: Call<List<House>> =
                    searchRoomService.getRoomListByCategoryAndPeriod(
                        selectedCategory,
                        selectedPeroid.toInt()
                    )
                callSearchService(callGetRoomListByCategoryAndPeriod)
            }

            GetRoomListByCategoryAndAmount -> {
                val callGetRoomListByCategoryAndAmount: Call<List<House>> =
                    searchRoomService.getRoomListByCategoryAndAmount(
                        selectedCategory,
                        amount.toInt()
                    )
                callSearchService(callGetRoomListByCategoryAndAmount)
            }

            GetRoomListByTownShipAndRent -> {
                val callGetRoomListByTownShipAndRent: Call<List<House>> =
                    searchRoomService.getRoomListByTownshipAndRent(
                        selectedAddress,
                        amount.toInt()
                    )
                callSearchService(callGetRoomListByTownShipAndRent)
            }

            GetRoomListByTownShipAndPeriod -> {
                val callGetRoomListByTownShipAndPeriod: Call<List<House>> =
                    searchRoomService.getRoomListByTownshipAndPeriod(
                        selectedAddress,
                        selectedPeroid.toInt()
                    )
                callSearchService(callGetRoomListByTownShipAndPeriod)
            }

            GetRoomListByAmountAndPeriod ->{
                val callGetRoomListByAmountAndPeriod: Call<List<House>> =
                    searchRoomService.getRoomListByAmountAndPeriod(
                        amount.toInt(),
                        selectedPeroid.toInt()
                    )
                callSearchService(callGetRoomListByAmountAndPeriod)
            }

            GetRoomListByCategoryAndAddressAndPeriod ->{
                val callGetRoomListByCategoryAndAddressAndPeriod: Call<List<House>> =
                    searchRoomService.getRoomListByCategoryAndAddressAndPeriod(
                        selectedCategory,
                        selectedAddress,
                        selectedPeroid.toInt()
                    )
                callSearchService(callGetRoomListByCategoryAndAddressAndPeriod)
            }

            GetRoomListByCategoryAndAddressAndAmount ->{
                val callGetRoomListByCategoryAndAddressAndAmount: Call<List<House>> =
                    searchRoomService.getRoomListByCategoryAndAddressAndAmount(
                        selectedCategory,
                        selectedAddress,
                        amount.toInt()
                    )
                callSearchService(callGetRoomListByCategoryAndAddressAndAmount)
            }

            GetRoomListByCategoryAndAmountAndPeriod ->{
                val callGetRoomListByCategoryAndAmountAndPeriod: Call<List<House>> =
                    searchRoomService.getRoomListByCategoryAndAmountAndPeriod(
                        selectedCategory,
                        selectedPeroid.toInt(),
                        amount.toInt()
                    )
                callSearchService(callGetRoomListByCategoryAndAmountAndPeriod)
            }

            GetRoomListByAddressAndAmountAndPeriod ->{
                val callGetRoomListByAddressAndAmountAndPeriod: Call<List<House>> =
                    searchRoomService.getRoomListByAddressAndAmountAndPeriod(
                        selectedAddress,
                        selectedPeroid.toInt(),
                        amount.toInt()
                    )
                callSearchService(callGetRoomListByAddressAndAmountAndPeriod)
            }

            GetRoomListByAll ->{
                val callGetRoomListByAll: Call<List<House>> =
                    searchRoomService.getRoomListByAll(
                        selectedCategory,
                        selectedAddress,
                        selectedPeroid.toInt(),
                        amount.toInt()
                    )
                callSearchService(callGetRoomListByAll)
            }
        }
    }

    private fun callSearchService(callGetRoomList: Call<List<House>>) {
        callGetRoomList.enqueue(object : Callback<List<House>> {
            override fun onResponse(call: Call<List<House>>, response: Response<List<House>>) {
                if (response.isSuccessful) {
                    progress.visibility= View.GONE
                    val houseList = response.body()!! as List<House>
                    Log.d("Response", "houseList size : ${houseList.size}")
                    adapter.setData(houseList as ArrayList<House>)
                    adapter.setOnClickListener(object : HouseItemAdapter.OnClickListener{
                        override fun onClick(position: Int, model: House) {
                            getDetailData(model.house_ID)
                        }

                    })
                } else {
                    Toast.makeText(
                        this@HouseListActivity,
                        "Something went wrong ${response.message()}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<List<House>>, t: Throwable) {
                Log.d("Response", "fail service call ${t.message}")
            }
        })
    }

    private fun getDetailData(mDetailId: String) {
        val destinationService = ServiceBuilder.buildService(RoomForRentService::class.java)
        val requestCall = destinationService.getHouseDetailById(mDetailId)

        requestCall.enqueue(object : Callback<HouseDetails> {
            override fun onResponse(call: Call<HouseDetails>, response: Response<HouseDetails>) {
                var postlist: HouseDetails = response.body() as HouseDetails

                var intent = Intent(this@HouseListActivity,HouseDetailActivity::class.java)
                intent.putStringArrayListExtra(Constants.HOUSE_IMAGE, ArrayList<String>(postlist.house_image))
                intent.putExtra(Constants.HOUSE_ADDRESS, postlist.house_address)
                intent.putExtra(Constants.NO_OF_GUESTS, postlist.no_of_guests)
                intent.putExtra(Constants.RECOMMENTED_POINT, postlist.recommented_points)
                intent.putExtra(Constants.CONTACT_ONE, postlist.phone_one)
                intent.putExtra(Constants.CONTACT_TWO, postlist.phone_two)
                intent.putExtra(Constants.AMOUNT, postlist.rent)
                intent.putExtra(Constants.DEPOSIT, postlist.deposit)
                intent.putExtra(Constants.AVAILABLE_DATE, postlist.available_date)

                startActivity(intent)
            }

            override fun onFailure(call: Call<HouseDetails>, t: Throwable) {
                Toast.makeText(this@HouseListActivity, "Something went wrong $t", Toast.LENGTH_SHORT)
            }
        })
    }

    private fun initAdapter() {
        adapter = HouseItemAdapter(this)

        // Set the LayoutManager that this RecyclerView will use.
        recycler_view_items.layoutManager = LinearLayoutManager(this)
        recycler_view_items.adapter = adapter

        /*adapter.setOnClickListener(object : HouseItemAdapter.OnClickListener {
            override fun onClick() {
                var intent = Intent(this@HouseListActivity, HouseDetailActivity::class.java)
                startActivity(intent)
            }
        })*/
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