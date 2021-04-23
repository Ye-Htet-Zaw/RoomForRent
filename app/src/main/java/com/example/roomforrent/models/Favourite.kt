package com.example.roomforrent.models

import java.sql.Timestamp

data class Favourite(
    var favourite_id:String="",
    var user_id:String,
    var house_id:String,
    var creator_id:String,
    var create_dateTime: String
)