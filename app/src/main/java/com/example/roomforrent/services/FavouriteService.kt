/**
 *
 * FavouriteService
 *
 * 2021/04/20 KMMN Create New
 *
 * call favourite api to get favourite list
 */
package com.example.roomforrent.services

import com.example.roomforrent.models.Favourite
import com.example.roomforrent.models.House
import retrofit2.Call
import retrofit2.http.*

interface FavouriteService {

    /**
     * get favourite house list by user id
     */
    @GET("/getFavHouseListWithUserId/{USER_ID}")
    fun getFavHouseListWithUserId(@Path("USER_ID") user_id: String): Call<List<House>>

    /**
     * save favouite information
     */
    @POST("saveFavouriteInfo")
    fun saveFavouriteInfo(@Body favourite: Favourite): Call<List<Favourite>>

    /**
     * get favourite id by user id and house id
     */
    @GET("getFavouriteId/{USER_ID}&{HOUSE_ID}")
    fun getFavouriteId(
        @Path("USER_ID") user_id: String,
        @Path("HOUSE_ID") house_id: String
    ): Call<Favourite>

    /**
     * delete favourite item by user id and house id
     */
    @DELETE("deleteFavouriteWithUserAndHouseId/{USER_ID}&{HOUSE_ID}")
    fun deleteFavouriteWithUserAndHouseId(
        @Path("USER_ID") user_id: String,
        @Path("HOUSE_ID") house_id: String
    ): Call<List<House>>
}