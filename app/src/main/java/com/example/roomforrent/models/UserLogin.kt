/**
 *UserLoginDataClass
 * 2021/03/05 NTTT Create New
 * For Login User
 */
package com.example.roomforrent.models

import java.sql.Timestamp

data class UserLogin(
    val user_id:String,
    val user_name:String,
    val user_email:String,
    val facebook_id:String,
    val password:String,
    val phone_one:String,
    val phone_two:String,
    val user_address:String,
    val user_gender:Int,
    val user_dob:String,
    val user_position:Int,
    val delete_flag:Int,
    val delete_datetime:String,
    val creator_id:String,
    val create_datetime: String,
    val updator_id:String,
    val update_datetime:String)



