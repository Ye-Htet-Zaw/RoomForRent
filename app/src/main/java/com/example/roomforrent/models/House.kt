/**
 *
 * House Data
 *
 * 2021/03/8 KMMN Create New
 *
 * include House table fields
 */
package com.example.roomforrent.models

import java.sql.Timestamp
import java.util.*

data class House(
    var house_ID:String="",
    var user_ID:String,
    var category_ID:String,
    var house_ADDRESS:String,
    var township:String,
    var no_OF_GUESTS:Int ,
    var no_OF_ROOM:Int ,
    var no_OF_BATH:Int,
    var no_OF_TOILET:Int,
    var area:Int,
    var no_OF_FLOOR:Int,
    var no_OF_AIRCON:Int,
    var wifi:Int,
    var phone_ONE:String,
    var phone_TWO:String,
    var available_DATE:Date ,
    var rent:Int,
    var deposit:Int,
    var longitude:String,
    var latitude:String,
    var expired_DATE:Date,
    var recommented_POINTS:String,
    var contract_RULE:String,
    var period:Int,
    var rent_FLAG:Int,
    var delete_FLAG:Int,
    var delete_DATETIME:Timestamp,
    var creator_ID:String,
    var create_DATETIME:Timestamp,
    var updator_ID:String,
    var update_DATETIME:Timestamp
)