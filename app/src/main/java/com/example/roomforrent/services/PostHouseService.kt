package com.example.roomforrent.services

import com.example.roomforrent.models.House
import com.example.roomforrent.models.ServerResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface PostHouseService {

    @POST("createHouse")
    fun createHouse(@Body house: House): Call<House>

    @Multipart
    @POST("uploadMultipleFiles")
    fun uploadImages(@Part images: ArrayList<MultipartBody.Part>): Call<ServerResponse>
}