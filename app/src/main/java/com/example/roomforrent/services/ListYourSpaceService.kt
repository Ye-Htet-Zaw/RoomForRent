package com.example.roomforrent.services

import com.example.roomforrent.models.HouseList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ListYourSpaceService {
    @GET("houseLists/{user_id}")
    fun getHouseList(@Path("user_id") userId: String): Call<List<HouseList>>
}