package com.example.roomforrent.models

import com.google.gson.annotations.SerializedName

class ServerResponse {
    @SerializedName("success")
    var success: Boolean? = null


    fun getSuccess(): Boolean {
        return success!!
    }
}