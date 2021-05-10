/**
 *
 * ChangePasswordService
 *
 * 2021/03/10 NTTT Create New
 *
 * call search api to change password
 */
package com.example.roomforrent.services

import com.example.roomforrent.models.UserLogin
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface ChangePasswordService {
    //RetrievePasswordFromDatabase
    @GET("getPassword/{user_id}")
    fun getPassword(@Path("user_id") user_id: String):Call<String>

    //UpdatePasswordToDatabase
    @PUT("updatePassword/{user_id}&{password}")
    fun updatePassword(@Path("user_id") user_id: String, @Path("password") password:String): Call<UserLogin>
}