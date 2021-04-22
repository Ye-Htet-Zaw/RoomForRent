package com.example.roomforrent.fragment

import android.R.attr.endColor
import android.R.attr.startColor
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Shader
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roomforrent.R
import com.example.roomforrent.activity.HouseDetailActivity
import com.example.roomforrent.adapter.FavouriteItemAdapter
import com.example.roomforrent.models.House
import com.example.roomforrent.models.HouseDetails
import com.example.roomforrent.services.FavouriteService
import com.example.roomforrent.services.RoomForRentService
import com.example.roomforrent.services.ServiceBuilder
import com.example.roomforrent.utils.Constants
import kotlinx.android.synthetic.main.fragment_favourite.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class FavouriteFragment : BaseFragment() {

    lateinit var adapter : FavouriteItemAdapter
    val favouriteService = ServiceBuilder.buildService(FavouriteService::class.java)
    var isLogin = false
    var userID : String?=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private fun initAdapter() {
        adapter = context?.let { FavouriteItemAdapter(it) }!!
        favouriteRecyclerView.layoutManager= LinearLayoutManager(context)
        favouriteRecyclerView.adapter = adapter
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favourite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        val share: SharedPreferences = context?.getSharedPreferences(
            "myPreference",
            Context.MODE_PRIVATE
        )!!
        //isLogin = share.getBoolean("isLogin", false)
        //userID=share.getString(USERID,"")
        isLogin = true
        userID = "1"
        if(isLogin){
            blankLayout.visibility=View.GONE
            var callGetFavouriteHouseList = favouriteService.getFavouritHouseList()
            callGetFavouriteHouseList.enqueue(object : Callback<List<House>> {
                override fun onResponse(call: Call<List<House>>, response: Response<List<House>>) {
                    Log.i("TestFavourite", "success to load favourite house list")
                    adapter.setData(response.body() as ArrayList<House>, userID!!)
                    if (response.body()?.size == 0) {
                        blankLayout.visibility = View.VISIBLE
                        txtBlank.text = resources.getString(R.string.nohaveFav)
                    }else {
                        blankLayout.visibility = View.GONE
                    }

                    adapter.setOnHeartClickListener(object :FavouriteItemAdapter.OnHeartClickListener{
                        override fun onClick(houseList: ArrayList<House>) {
                            if (houseList.size == 0) {
                                blankLayout.visibility = View.VISIBLE
                                txtBlank.text = resources.getString(R.string.nohaveFav)
                            }else {
                                blankLayout.visibility = View.GONE
                            }
                        }
                    })
                    adapter.setOnClickListener(object : FavouriteItemAdapter.OnClickListener {
                        override fun onClick(position: Int, model: House) {
                            showProgressDialog("Please Wait...")
                            getDetailData(model.house_ID)
                        }
                    })
                }

                override fun onFailure(call: Call<List<House>>, t: Throwable) {
                    Log.i("TestFavourite", "fail to load favourite house list")
                }

            })
        }else{
            Log.i("TestFavourite", "Need to Login")
            blankLayout.visibility=View.VISIBLE
            txtBlank.text=resources.getString(R.string.nologin)
        }
    }

    private fun getDetailData(mDetailId: String) {
        val destinationService = ServiceBuilder.buildService(RoomForRentService::class.java)
        val requestCall = destinationService.getHouseDetailById(mDetailId)
        requestCall.enqueue(object : Callback<HouseDetails> {
            override fun onResponse(call: Call<HouseDetails>, response: Response<HouseDetails>) {
                var houseDetails: HouseDetails = response.body() as HouseDetails

                var intent = Intent(context, HouseDetailActivity::class.java)
                intent.putExtra(Constants.HOUSE_DETAIL, houseDetails)
                startActivity(intent)
                hideProgressDialog()
            }

            override fun onFailure(call: Call<HouseDetails>, t: Throwable) {
                Toast.makeText(context, "Something went wrong $t", Toast.LENGTH_SHORT)
            }
        })
    }
}