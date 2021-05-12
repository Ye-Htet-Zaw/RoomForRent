/**
 *
 * ListYourSpaceActivity
 *
 * 2021/04/22 NTTT Create New
 *
 * To show Hosue List For Owner
 */
package com.example.roomforrent.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roomforrent.R
import com.example.roomforrent.adapter.HouseItemAdapter
import com.example.roomforrent.models.House
import com.example.roomforrent.models.HouseDetails
import com.example.roomforrent.models.HouseList
import com.example.roomforrent.services.*
import com.example.roomforrent.utils.Constants
import kotlinx.android.synthetic.main.activity_house_list.*
import kotlinx.android.synthetic.main.fragment_login_profile.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListYourSpaceActivity : BaseActivity() {
    lateinit var adapter: HouseItemAdapter
    private lateinit var houseDetails: HouseDetails
    var user_id=""
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_house_list)
        initAdapter()
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, android.R.color.transparent)
        window.setBackgroundDrawable(resources.getDrawable(R.drawable.toolbarbg))


        setupActionBar()

        listSpace()


    }

    private fun listSpace(){

        user_id= intent.extras?.getString(Constants.USERID).toString()
        val destinationService = ServiceBuilder.buildService(ListYourSpaceService::class.java)
        val callGetAllRoomList: Call<List<House>> = destinationService.getHouseList(user_id)
        showProgressDialog("Please Wait...")
        callGetAllRoomList.enqueue(object : Callback<List<House>> {
            override fun onResponse(call: Call<List<House>>, response: Response<List<House>>) {
                if (response.isSuccessful) {
                    hideProgressDialog()
                    if(response.body()!!.isEmpty()){
                        txtBlank.visibility = View.VISIBLE
                    }else {
                        txtBlank.visibility= View.GONE
                    }
                    val houseList = response.body()!! as List<House>
                    Log.d("Response", "houseList size : ${houseList}")
                    adapter.setData(houseList as ArrayList<House>)
                    adapter.setOnClickListener(object : HouseItemAdapter.OnClickListener{
                        override fun onClick(position: Int, model: House) {
                            showProgressDialog("Please Wait...")
                            getDetailData(model.house_ID)
                        }

                    })
                } else {
                    hideProgressDialog()
                    Log.d("Response", "fail")
                    Toast.makeText(
                        this@ListYourSpaceActivity,
                        "Something went wrong ${response.message()}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<List<House>>, t: Throwable) {
                hideProgressDialog()
                Log.d("Response", "fail service call ${t.message}")
            }
        })
        Log.d("Response","ListYourSpace userId: "+ user_id)
    }

    private fun initAdapter() {
        adapter = HouseItemAdapter(this)

        // Set the LayoutManager that this RecyclerView will use.
        recycler_view_items.layoutManager = LinearLayoutManager(this)
        recycler_view_items.adapter = adapter

    }
    @SuppressLint("ResourceAsColor")
    private fun setupActionBar() {
        setSupportActionBar(toolBarMain)
        val actionBar = supportActionBar
        if (actionBar != null) {
            //actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_backward_icon)
            actionBar.title = "House List"
        }
        toolBarMain.setNavigationOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun getDetailData(mDetailId: String) {
        val destinationService = ServiceBuilder.buildService(RoomForRentService::class.java)
        val requestCall = destinationService.getHouseDetailById(mDetailId)

        requestCall.enqueue(object : Callback<HouseDetails> {
            override fun onResponse(call: Call<HouseDetails>, response: Response<HouseDetails>) {
                houseDetails= response.body() as HouseDetails

                var intent = Intent(this@ListYourSpaceActivity,HouseDetailActivity::class.java)
                intent.putExtra(Constants.HOUSE_DETAIL, houseDetails)
                startActivity(intent)
                hideProgressDialog()
            }

            override fun onFailure(call: Call<HouseDetails>, t: Throwable) {
                Toast.makeText(this@ListYourSpaceActivity, "Something went wrong $t", Toast.LENGTH_SHORT)
            }
        })
    }
}