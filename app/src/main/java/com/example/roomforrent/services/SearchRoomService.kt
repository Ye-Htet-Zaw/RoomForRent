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

    @GET("getRoomListByCategory/{CATEGORY_NAME}")
    fun getRoomListByCategory(@Path("CATEGORY_NAME") category_Name: String): Call<List<House>>

    @GET("getRoomListByTownship/{TOWNSHIP}")
    fun getRoomListByTownship(@Path("TOWNSHIP") township: String): Call<List<House>>

    @GET("getRoomListByAmount/{RENT}")
    fun getRoomListByAmount(@Path("RENT") rent:Int): Call<List<House>>

    @GET("getRoomListByPeroid/{PERIOD}")
    fun getRoomListByPeroid(@Path("PERIOD") period: Int):Call<List<House>>

    @GET("getRoomListByCategoryAndAmount/{CATEGORY_NAME}&{RENT}")
    fun getRoomListByCategoryAndAmount(@Path("CATEGORY_NAME") category_Name: String,
                                         @Path("RENT") rent: Int): Call<List<House>>

    @GET("getRoomListByCategoryAndPeriod/{CATEGORY_NAME}&{PERIOD}")
    fun getRoomListByCategoryAndPeriod(@Path("CATEGORY_NAME") category_Name: String,
                                       @Path("PERIOD") period: Int): Call<List<House>>

    @GET("getRoomListByTownshipAndRent/{TOWNSHIP}&{RENT}")
    fun getRoomListByTownshipAndRent(@Path("TOWNSHIP") township: String,
                                       @Path("RENT") rent: Int): Call<List<House>>

    @GET("getRoomListByTownshipAndPeriod/{TOWNSHIP}&{PERIOD}")
    fun getRoomListByTownshipAndPeriod(@Path("TOWNSHIP") township: String,
                                     @Path("PERIOD") period: Int): Call<List<House>>

    @GET("getRoomListByAmountAndPeriod/{RENT}&{PERIOD}")
    fun getRoomListByAmountAndPeriod(@Path("RENT") rent: Int,
                                       @Path("PERIOD") period: Int): Call<List<House>>

    @GET("getRoomListByCategoryAndAddressAndPeriod/{CATEGORY_NAME}&{TOWNSHIP}&{PERIOD}")
    fun getRoomListByCategoryAndAddressAndPeriod(@Path("CATEGORY_NAME") category_Name: String,
                                                 @Path("TOWNSHIP") township: String,
                                     @Path("PERIOD") period: Int): Call<List<House>>

    @GET("getRoomListByCategoryAndAddressAndAmount/{CATEGORY_NAME}&{TOWNSHIP}&{RENT}")
    fun getRoomListByCategoryAndAddressAndAmount(@Path("CATEGORY_NAME") category_Name: String,
                                                 @Path("TOWNSHIP") township: String,
                                                 @Path("RENT") rent: Int): Call<List<House>>

    @GET("getRoomListByCategoryAndAmountAndPeriod/{CATEGORY_NAME}&{RENT}&{PERIOD}")
    fun getRoomListByCategoryAndAmountAndPeriod(@Path("CATEGORY_NAME") category_Name: String,
                                                 @Path("PERIOD") period: Int,
                                                 @Path("RENT") rent: Int): Call<List<House>>

    @GET("getRoomListByAddressAndAmountAndPeriod/{TOWNSHIP}&{RENT}&{PERIOD}")
    fun getRoomListByAddressAndAmountAndPeriod(@Path("TOWNSHIP") township: String,
                                                @Path("PERIOD") period: Int,
                                               @Path("RENT") rent: Int): Call<List<House>>

    @GET("getRoomListByAll/{CATEGORY_NAME}&{TOWNSHIP}&{RENT}&{PERIOD}")
    fun getRoomListByAll(@Path("CATEGORY_NAME") category_Name: String,
                         @Path("TOWNSHIP") township: String,
                         @Path("PERIOD") period: Int,
                         @Path("RENT") rent: Int): Call<List<House>>
}