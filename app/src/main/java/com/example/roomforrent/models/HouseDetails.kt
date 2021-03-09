package com.example.roomforrent.models

import java.util.*

data  class HouseDetails(
        val house_address: String,
        val no_of_guests: Int,
        val no_of_room: Int,
        val no_of_bath: Int,
        val no_of_toilet: Int,
        val area: Int,
        val no_of_floor: Int,
        val no_of_aircon: Int,
        val wifi: Int,
        val phone_one: String,
        val phone_two: String,
        val available_date: String,
        val rent: Int,
        val deposit: Int,
        val longitude: String,
        val latitude: String,
        val recommented_points: String,
        val contract_rule: String,
        val period: String
)