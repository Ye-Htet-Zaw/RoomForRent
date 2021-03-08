package com.example.roomforrent.services

import com.example.roomforrent.models.HouseList
import retrofit2.Call
import retrofit2.http.GET

interface HouseListService {

    @GET("houseLists")
    fun getHouseList(): Call<List<HouseList>>
}