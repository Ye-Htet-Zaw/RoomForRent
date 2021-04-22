package com.example.roomforrent.fragment

import android.content.Context
import android.content.SharedPreferences
import android.net.DnsResolver
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.roomforrent.R
import com.example.roomforrent.models.Favourite
import com.example.roomforrent.services.FavouriteService
import com.example.roomforrent.services.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FavouriteFragment : Fragment() {

    val favouriteService = ServiceBuilder.buildService(FavouriteService::class.java)
    var isLogin = false
    var userId: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.i("TestFavourite", "Need to Login1")
        return inflater.inflate(R.layout.fragment_favourite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.i("TestFavourite", "Need to Login2")
        super.onViewCreated(view, savedInstanceState)
        val share: SharedPreferences = context?.getSharedPreferences(
            "myPreference",
            Context.MODE_PRIVATE
        )!!
        //isLogin = share.getBoolean("isLogin", false)
        isLogin = true
        userId = "1"
        if(isLogin){
            //userId = share.getString(USERID, "")
            val callGetAllFavouriteList =
                favouriteService.getAllFavouriteListWithUserId(userId!!)
            callGetAllFavouriteList.enqueue(object : Callback<List<Favourite>> {
                override fun onResponse(
                    call: Call<List<Favourite>>,
                    response: Response<List<Favourite>>
                ) {
                    Log.i("TestFavourite", "success get all favouriteList ${response.body()?.size}")
                }

                override fun onFailure(call: Call<List<Favourite>>, t: Throwable) {
                    Log.i("TestFavourite", "fail get all favouriteList")
                }

            })
        }else{
            Log.i("TestFavourite", "Need to Login")
        }
    }
}