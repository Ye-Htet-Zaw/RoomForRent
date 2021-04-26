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
    var house_ID:String = "",
    var user_ID:String = "",
    var category_ID:String = "",
    var house_ADDRESS:String = "",
    var township:String = "",
    var no_OF_GUESTS:Int = 0,
    var no_OF_ROOM:Int = 0,
    var no_OF_BATH:Int = 0,
    var no_OF_TOILET:Int = 0,
    var area:Int = 0,
    var no_OF_FLOOR:Int = 0,
    var no_OF_AIRCON:Int = 0,
    var wifi:Int = 0,
    var phone_ONE:String = "",
    var phone_TWO:String = "",
    var available_DATE:Date = Date(1/1/2000),
    var rent:Int = 0,
    var deposit:Int = 0,
    var longitude:String = "",
    var latitude:String = "",
    var expired_DATE:Date = Date(1/1/2000),
    var recommented_POINTS:String = "",
    var contract_RULE:String = "",
    var period:Int = 0,
    var rent_FLAG:Int = 0,
    var delete_FLAG:Int = 0,
    var delete_DATETIME:Timestamp = Date(1/1/2000) as Timestamp,
    var creator_ID:String = "",
    var create_DATETIME:Timestamp=Date(1/1/2000) as Timestamp,
    var updator_ID:String = "",
    var update_DATETIME:Timestamp = Date(1/1/2000) as Timestamp
)