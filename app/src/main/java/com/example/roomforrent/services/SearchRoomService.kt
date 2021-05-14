/**
 *
 * SearchRoomService
 *
 * 2021/03/8 KMMN Create New
 *
 * call search api to get house list
 */
package com.example.roomforrent.services

import com.example.roomforrent.models.House
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface SearchRoomService {
    /**
     * get all room list
     */
    @GET("getAllRoomList")
    fun getAllRoomList(): Call<List<House>>

    /**
     * search room list by category name and township
     */
    @GET("getRoomListByCategoryAndTownShip/{CATEGORY_NAME}&{TOWNSHIP}")
    fun getRoomListByCategoryAndTownShip(
        @Path("CATEGORY_NAME") category_Name: String,
        @Path("TOWNSHIP") township: String
    ): Call<List<House>>

    /**
     * search room list by category name
     */
    @GET("getRoomListByCategory/{CATEGORY_NAME}")
    fun getRoomListByCategory(@Path("CATEGORY_NAME") category_Name: String): Call<List<House>>

    /**
     * search room list by  township
     */
    @GET("getRoomListByTownship/{TOWNSHIP}")
    fun getRoomListByTownship(@Path("TOWNSHIP") township: String): Call<List<House>>

    /**
     * search room list by rent price
     */
    @GET("getRoomListByAmount/{RENT}")
    fun getRoomListByAmount(@Path("RENT") rent: Int): Call<List<House>>

    /**
     * search room list by period
     */
    @GET("getRoomListByPeroid/{PERIOD}")
    fun getRoomListByPeroid(@Path("PERIOD") period: Int): Call<List<House>>

    /**
     * search room list by category name and rent price
     */
    @GET("getRoomListByCategoryAndAmount/{CATEGORY_NAME}&{RENT}")
    fun getRoomListByCategoryAndAmount(
        @Path("CATEGORY_NAME") category_Name: String,
        @Path("RENT") rent: Int
    ): Call<List<House>>

    /**
     * search room list by category name and period
     */
    @GET("getRoomListByCategoryAndPeriod/{CATEGORY_NAME}&{PERIOD}")
    fun getRoomListByCategoryAndPeriod(
        @Path("CATEGORY_NAME") category_Name: String,
        @Path("PERIOD") period: Int
    ): Call<List<House>>

    /**
     * search room list by township and rent price
     */
    @GET("getRoomListByTownshipAndRent/{TOWNSHIP}&{RENT}")
    fun getRoomListByTownshipAndRent(
        @Path("TOWNSHIP") township: String,
        @Path("RENT") rent: Int
    ): Call<List<House>>

    /**
     * search room list by township and period
     */
    @GET("getRoomListByTownshipAndPeriod/{TOWNSHIP}&{PERIOD}")
    fun getRoomListByTownshipAndPeriod(
        @Path("TOWNSHIP") township: String,
        @Path("PERIOD") period: Int
    ): Call<List<House>>

    /**
     * search room list by rent price and period
     */
    @GET("getRoomListByAmountAndPeriod/{RENT}&{PERIOD}")
    fun getRoomListByAmountAndPeriod(
        @Path("RENT") rent: Int,
        @Path("PERIOD") period: Int
    ): Call<List<House>>

    /**
     * search room list by category name , township and period
     */
    @GET("getRoomListByCategoryAndAddressAndPeriod/{CATEGORY_NAME}&{TOWNSHIP}&{PERIOD}")
    fun getRoomListByCategoryAndAddressAndPeriod(
        @Path("CATEGORY_NAME") category_Name: String,
        @Path("TOWNSHIP") township: String,
        @Path("PERIOD") period: Int
    ): Call<List<House>>

    /**
     * search room list by category name , township and rent price
     */
    @GET("getRoomListByCategoryAndAddressAndAmount/{CATEGORY_NAME}&{TOWNSHIP}&{RENT}")
    fun getRoomListByCategoryAndAddressAndAmount(
        @Path("CATEGORY_NAME") category_Name: String,
        @Path("TOWNSHIP") township: String,
        @Path("RENT") rent: Int
    ): Call<List<House>>

    /**
     * search room list by category name , rent price and period
     */
    @GET("getRoomListByCategoryAndAmountAndPeriod/{CATEGORY_NAME}&{RENT}&{PERIOD}")
    fun getRoomListByCategoryAndAmountAndPeriod(
        @Path("CATEGORY_NAME") category_Name: String,
        @Path("PERIOD") period: Int,
        @Path("RENT") rent: Int
    ): Call<List<House>>

    /**
     * search room list by township , rent price and period
     */
    @GET("getRoomListByAddressAndAmountAndPeriod/{TOWNSHIP}&{RENT}&{PERIOD}")
    fun getRoomListByAddressAndAmountAndPeriod(
        @Path("TOWNSHIP") township: String,
        @Path("PERIOD") period: Int,
        @Path("RENT") rent: Int
    ): Call<List<House>>

    /**
     * search room list by category name , township , rent price and period
     */
    @GET("getRoomListByAll/{CATEGORY_NAME}&{TOWNSHIP}&{RENT}&{PERIOD}")
    fun getRoomListByAll(
        @Path("CATEGORY_NAME") category_Name: String,
        @Path("TOWNSHIP") township: String,
        @Path("PERIOD") period: Int,
        @Path("RENT") rent: Int
    ): Call<List<House>>
}