package com.example.roomforrent.models

import java.sql.Timestamp
import java.util.*

data class House(
    var HOUSE_ID:String,
    var USER_ID:String,
    var CATEGORY_ID:String,
    var HOUSE_ADDRESS:String,
    var TOWNSHIP:String,
    var NO_OF_GUESTS:Int,
    var NO_OF_ROOM:Int,
    var NO_OF_BATH:Int,
    var NO_OF_TOILET:Int,
    var AREA:Int,
    var NO_OF_FLOOR:Int,
    var NO_OF_AIRCON:Int,
    var WIFI:Int,
    var PHONE_ONE:String,
    var PHONE_TWO:String,
    var AVAILABLE_DATE:Date,
    var RENT:Int,
    var DEPOSIT:Int,
    var LONGITUDE:String,
    var LATITUDE:String,
    var EXPIRED_DATE:Date,
    var RECOMMENTED_POINTS:String,
    var CONTRACT_RULE:String,
    var PERIOD:Int,
    var RENT_FLAG:Int,
    var DELETE_FLAG:Int,
    var DELETE_DATETIME:Timestamp,
    var CREATOR_ID:String,
    var CREATE_DATETIME:Timestamp,
    var UPDATOR_ID:String,
    var UPDATE_DATETIME:Timestamp
)