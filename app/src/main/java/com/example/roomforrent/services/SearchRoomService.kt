package com.example.roomforrent.services

import com.example.roomforrent.models.House
import retrofit2.Call
import retrofit2.http.GET

interface SearchRoomService {
    @GET("getAllRoomList")
    fun getAllRoomList(): Call<List<House>>
}