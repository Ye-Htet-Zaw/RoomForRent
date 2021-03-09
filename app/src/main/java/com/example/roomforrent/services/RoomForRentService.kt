package com.example.roomforrent.services

import com.example.roomforrent.models.HouseDetails
import retrofit2.Call
import retrofit2.http.*

interface RoomForRentService {
    @GET("houseDetail/{id}")
    fun getHouseDetailById (@Path("id") id: String) : Call<HouseDetails>
}
