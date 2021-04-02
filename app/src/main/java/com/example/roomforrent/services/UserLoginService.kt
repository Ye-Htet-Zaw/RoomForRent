/**
 *UserLoginService
 * 2021/03/08 NTTT Create New
 * For Login User
 */
package com.example.roomforrent.services

import com.example.roomforrent.models.UserLogin
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface UserLoginService {
    @GET("getUserWithEmailAndPassword/{user_email}&{password}/")
    fun getUserWithEmailAndPassword(@Path("user_email") user_email: String, @Path("password") password: String): Call<UserLogin>
}