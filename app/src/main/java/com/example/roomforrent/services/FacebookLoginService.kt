package com.example.roomforrent.services

import com.example.roomforrent.models.UserLogin
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface FacebookLoginService {
    @POST("createFacebookAccount")
    fun createUser(@Body account: UserLogin): Call<List<UserLogin>>

    @GET("getFacebookId/{facebook_id}")
    fun getFacebookId(@Path("facebook_id") facebook_id: String):Call<Int>


}