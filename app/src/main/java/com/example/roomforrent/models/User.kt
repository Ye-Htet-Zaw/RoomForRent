package com.example.roomforrent.models

import java.util.*

data class User(val user_id:String,val user_name:String,
                val user_email:String,val facebook_id:String,
                val password:String,val phone_one:String,
                val phone_two:String,val user_address:String,
                val user_gender:Int,val user_dob:Date,val dobString:String,
                val user_position:Int,val user_delete_flag:Int,
                val delete_dateTime:Date,val creator_id:String,
                val creator_dateTime:Date,val updator_id:String,
                val update_dateTime:Date
)
