package com.example.roomforrent.services

import com.example.roomforrent.models.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface UserProfileService {
    @GET("/getUserInfo/{user_id}")
    fun getUserInfo(@Path("user_id") userId: String): Call<User>
}