/**
 *
 * ListYourSpaceService
 *
 * 2021/04/20 NTTT Create New
 *
 * call search api to show hosue list
 */
package com.example.roomforrent.services

import com.example.roomforrent.models.House
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ListYourSpaceService {
    //RetrieveHosueList
    @GET("listYourSpace/{user_id}")
    fun getHouseList(@Path("user_id") userId: String): Call<List<House>>
}