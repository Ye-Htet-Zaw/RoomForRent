/**
 *
 * FacebookLoginService
 *
 * 2021/04/24 NTTT Create New
 *
 * call search api for Facebook
 */
package com.example.roomforrent.services

import com.example.roomforrent.models.User
import com.example.roomforrent.models.UserLogin
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface FacebookLoginService {
    //RegisterFacebookUser
    @POST("createFacebookAccount")
    fun createUser(@Body account: UserLogin): Call<List<UserLogin>>

    //CountFbAccountForDuplicate
    @GET("getFacebookId/{facebook_id}")
    fun getFacebookId(@Path("facebook_id") facebook_id: String):Call<Int>

    //RetrieveUserIdWithFacebookId
    @GET("getUserId/{facebook_id}")
    fun getUserId(@Path("facebook_id") facebook_id: String): Call<UserLogin>

}