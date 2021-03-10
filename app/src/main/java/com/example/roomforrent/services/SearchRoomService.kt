package com.example.roomforrent.services

import com.example.roomforrent.models.House
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface SearchRoomService {
    @GET("getAllRoomList")
    fun getAllRoomList(): Call<List<House>>

    @GET("getRoomListByCategoryAndTownShip/{CATEGORY_NAME}&{TOWNSHIP}")
    fun getRoomListByCategoryAndTownShip(@Path("CATEGORY_NAME") category_Name: String,
                   @Path("TOWNSHIP") township: String): Call<List<House>>
}