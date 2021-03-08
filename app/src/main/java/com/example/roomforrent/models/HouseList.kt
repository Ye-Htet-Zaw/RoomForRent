package com.example.roomforrent.models

data class HouseList(
    var house_id:String = "",
    var user_id:String = "",
    var category_id:String = "",
    var house_address:String = "",
    var rent:Int = 0,
    var houseImage:String = ""
)