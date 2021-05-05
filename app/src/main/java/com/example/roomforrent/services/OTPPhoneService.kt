package com.example.roomforrent.services

import com.example.roomforrent.models.Favourite
import com.example.roomforrent.models.Phone
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface OTPPhoneService {
    @GET("getPhoneUserCount/{PHONE_ONE}")
    fun getPhoneUserCount(@Path("PHONE_ONE") phone_one: String): Call<List<Phone>>

    @POST("savePhoneUser")
    fun createPhoneUser(@Body phone: Phone): Call<Phone>
}