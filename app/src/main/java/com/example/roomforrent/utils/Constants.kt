package com.example.roomforrent.utils

import java.util.*
import kotlin.collections.ArrayList

object Constants {

    //Search
    const val CALLAPI = "CallApi"
    const val select: String = "Select"
    const val GetAllRoomList = "GetAllRoomList"


    val townshipArr: ArrayList<String>
        get() {
            val towhshipArr = ArrayList(
                Arrays.asList(
                    select,
                    Ahlon,
                    Bahan,
                    Dagon,
                    Hlaing,
                    Kamayut,
                    Kyauktada,
                    Kyimyindaing,
                    Lanmadaw,
                    Latha,
                    Mayangon,
                    Pabedan,
                    Sanchaung,
                    Botataung,
                    DagonSeikkan,
                    Dawbon,
                    MingalaTaungnyunt,
                    NewDagonEast,
                    NewDagonNorth,
                    NewDagonSouth,
                    NorthOkkalapa,
                    Pazundaung,
                    SouthOkkalapa,
                    Tamwe,
                    Thaketa,
                    Thingangyun,
                    Yankin,
                    HlaingthayaEast,
                    HlaingthayaWest,
                    Hlegu,
                    Hmawbi,
                    Htantabin,
                    Insein,
                    Mingaladon,
                    Shwepyitha,
                    Taikkyi,
                    Cocokyun,
                    Dala,
                    Kawhmu,
                    Khayan,
                    Kungyangon,
                    Kyauktan,
                    SeikkyiKanaungto,
                    Thanlyin,
                    Thongwa,
                    Twante
                )
            )
            return towhshipArr
        }

    val categoryArr: ArrayList<String>
        get() {
            val categoryArr = ArrayList(
                Arrays.asList(
                    select,
                    Condominum,
                    WholeHouse,
                    Apartment,
                    Hostal
                )
            )
            return categoryArr
        }

    val periodArr: ArrayList<String>
        get() {
            val periodArr = ArrayList(
                Arrays.asList(
                    select,
                    ThreeMonths,
                    SixMonths,
                    OneYear,
                    TwoYears
                )
            )
            return periodArr
        }



    //Township
    const val Ahlon: String = "Ahlon"
    const val Bahan: String = "Bahan"
    const val Dagon: String = "Dagon"
    const val Hlaing: String = "Hlaing"
    const val Kamayut: String = "Kamayut"
    const val Kyauktada: String = "Kyauktada"
    const val Kyimyindaing: String = "Kyimyindaing"
    const val Lanmadaw: String = "Lanmadaw"
    const val Latha: String = "Latha"
    const val Mayangon: String = "Mayangon"
    const val Pabedan: String = "Pabedan"
    const val Sanchaung: String = "Sanchaung"
    const val Botataung: String = "Botataung"
    const val DagonSeikkan: String = "Dagon Seikkan"
    const val Dawbon: String = "Dawbon"
    const val MingalaTaungnyunt: String = "Mingala Taungnyunt"
    const val NewDagonEast: String = "New Dagon East"
    const val NewDagonNorth: String = "New Dagon North"
    const val NewDagonSouth: String = "New Dagon South"
    const val NorthOkkalapa: String = "North Okkalapa"
    const val Pazundaung: String = "Pazundaung"
    const val SouthOkkalapa: String = "South Okkalapa"
    const val Tamwe: String = "Tamwe"
    const val Thaketa: String = "Thaketa"
    const val Thingangyun: String = "Thingangyun"
    const val Yankin: String = "Yankin"
    const val HlaingthayaEast: String = "Hlaingthaya East"
    const val HlaingthayaWest: String = "Hlaingthaya West"
    const val Hlegu: String = "Hlegu"
    const val Hmawbi: String = "Hmawbi"
    const val Htantabin: String = "Htantabin"
    const val Insein: String = "Insein"
    const val Mingaladon: String = "Mingaladon"
    const val Shwepyitha: String = "Shwepyitha"
    const val Taikkyi: String = "Taikkyi"
    const val Cocokyun: String = "Cocokyun"
    const val Dala: String = "Dala"
    const val Kawhmu: String = "Kawhmu"
    const val Khayan: String = "Khayan"
    const val Kungyangon: String = "Kungyangon"
    const val Kyauktan: String = "Kyauktan"
    const val SeikkyiKanaungto: String = "Seikkyi Kanaungto"
    const val Thanlyin: String = "Thanlyin"
    const val Thongwa: String = "Thongwa"
    const val Twante: String = "Twante"

    //Category
    const val Condominum: String = "Condominum"
    const val WholeHouse: String = "Whole House"
    const val Apartment: String = "Apartment"
    const val Hostal: String = "Hostal"

    //Period
    const val ThreeMonths: String = "3"
    const val SixMonths: String = "6"
    const val OneYear: String = "12"
    const val TwoYears: String = "24"

    //HouseDetail
    const val NO_OF_GUESTS: String = "no_of_guests"
    const val AREA: String = "area"
    const val NO_OF_TOILET: String = "no_of_toilet"
    const val NO_OF_BATH: String = "no_of_bath"
    const val NO_OF_BEDROOM: String = "no_of_bedroom"
    const val WIFI: String = "wifi"
    const val NO_OF_AIRCON: String = "no_of_aircon"
    const val NO_OF_FLOOR: String = "no_of_floor"
    const val CONTRACT_RULE: String = "contract_rule"




}