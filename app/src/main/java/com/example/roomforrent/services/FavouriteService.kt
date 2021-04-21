package com.example.roomforrent.services

import com.example.roomforrent.models.Favourite
import retrofit2.Call
import retrofit2.http.*

interface FavouriteService {
    @GET("getAllFavouriteListWithUserId/{USER_ID}")
    fun getAllFavouriteListWithUserId(@Path("USER_ID") user_id: String): Call<List<Favourite>>

    @POST("saveFavouriteInfo")
    fun saveFavouriteInfo(@Body favourite: Favourite): Call<List<Favourite>>

    @GET("getFavouriteId/{USER_ID}&{HOUSE_ID}")
    fun getFavouriteId(@Path("USER_ID")user_id: String,@Path("HOUSE_ID")house_id: String):Call<Favourite>

    @DELETE("deleteFavouriteWithId/{USER_ID}&{FAVOURITE_ID}")
    fun deleteFavouriteWithId(@Path("USER_ID")user_id: String,@Path("FAVOURITE_ID")favourite_id: String):Call<List<Favourite>>
}