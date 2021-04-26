package com.example.roomforrent.services

import com.example.roomforrent.models.House
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface PostHouseService {

    @POST("createHouse")
    fun createHouse(@Body house: House): Call<House>
}