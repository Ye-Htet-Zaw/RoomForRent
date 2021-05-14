/**
 *
 * OTPPhoneService
 *
 * 2021/03/8 YHZ Create New
 *
 * Call Api To Insert, Select User
 */
package com.example.roomforrent.services

import com.example.roomforrent.models.Phone
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface OTPPhoneService {
    @GET("getPhoneUserCount/{PHONE_ONE}")
    fun getPhoneUserCount(@Path("PHONE_ONE") phone_one: String): Call<Phone>

    @POST("savePhoneUser")
    fun createPhoneUser(@Body phone: Phone): Call<Phone>
}