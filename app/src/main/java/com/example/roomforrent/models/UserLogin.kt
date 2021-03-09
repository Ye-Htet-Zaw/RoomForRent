package com.example.roomforrent.models

import java.sql.Timestamp
import java.util.*

data class UserLogin (
    val user_id:String,
    val  user_name:String,
    val user_email:String,
    val facebook_id:String,
    val password:String,
    val phone_one:String,
    val phone_two:String,
    val user_address:String,
    val user_gender:Int,
    val user_dob:Date,
    val user_position:Int,
    val delete_flag:Int,
    val delete_datetime:Timestamp,
    val creator_id:Int,
    val create_datetime:Timestamp,
    val updator_id:Int,
    val update_datetime:Timestamp)



