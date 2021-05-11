package com.example.roomforrent.services

import com.example.roomforrent.models.User
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface UserProfileService {
    @GET("/getUserInfo/{user_id}")
    fun getUserInfo(@Path("user_id") userId: String): Call<User>

    @PUT("updateUserInfo")
    suspend fun updateUserInfo(@Body requestBody: RequestBody): Response<ResponseBody>

    @Multipart
    @POST("uploadUserMultipleFiles")
    fun uploadUserImages(@Part images: MultipartBody.Part): Call<Void>
}