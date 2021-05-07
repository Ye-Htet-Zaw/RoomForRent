package com.example.roomforrent.utils

import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.webkit.MimeTypeMap
import androidx.fragment.app.FragmentActivity
import com.example.roomforrent.fragment.PostHouseFragment
import java.util.*
import kotlin.collections.ArrayList

object Constants {

    /**
     * Key name to go house list activity
     */
    const val CALLAPI = "CallApi"


    /**
     * String used in search ui
     */
    const val SelectedCategory = "SelectedCategory"
    const val SelectedAddress = "SelectedAddress"
    const val SelectedPeroid = "SelectedPeroid"
    const val Amount = "Amount"
    const val select: String = "Select"

    /**
     * api names to call house list api
     */
    const val GetAllRoomList = "GetAllRoomList"
    const val GetRoomListByCategoryAndTownShip = "GetRoomListByCategoryAndTownShip"
    const val GetRoomListByCategory = "GetRoomListByCategory"
    const val GetRoomListByTownship = "GetRoomListByTownship"
    const val GetRoomListByAmount = "GetRoomListByAmount"
    const val GetRoomListByPeriod = "GetRoomListByPeriod"
    const val GetRoomListByCategoryAndAmount = "GetRoomListByCategoryAndAmount"
    const val GetRoomListByCategoryAndPeriod = "GetRoomListByCategoryAndPeriod"
    const val GetRoomListByTownShipAndRent = "GetRoomListByTownShipAndRent"
    const val GetRoomListByTownShipAndPeriod = "GetRoomListByTownShipAndPeriod"
    const val GetRoomListByAmountAndPeriod = "GetRoomListByAmountAndPeriod"
    const val GetRoomListByCategoryAndAddressAndPeriod = "GetRoomListByCategoryAndAddressAndPeriod"
    const val GetRoomListByCategoryAndAddressAndAmount = "GetRoomListByCategoryAndAddressAndAmount"
    const val GetRoomListByCategoryAndAmountAndPeriod = "GetRoomListByCategoryAndAmountAndPeriod"
    const val GetRoomListByAddressAndAmountAndPeriod = "GetRoomListByAddressAndAmountAndPeriod"
    const val GetRoomListByAll = "GetRoomListByAll"

    //For transfer data
    const val USERID: String= "UserId"
    const val POSITION="user_position"

    /**
     * township list use in search ui
     */
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

    /**
     * category list use in search ui
     */
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

    /**
     * period list use in search ui
     */
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



    /**
     * township names
     */
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

    /**
     * Category names
     */
    const val Condominum: String = "Condominum"
    const val WholeHouse: String = "Whole House"
    const val Apartment: String = "Apartment"
    const val Hostal: String = "Hostal"

    /**
     * periods
     */
    const val ThreeMonths: String = "3"
    const val SixMonths: String = "6"
    const val OneYear: String = "12"
    const val TwoYears: String = "24"

    //HouseDetail
    const val HOUSE_DETAIL: String = "house_detail"
    const val NO_OF_GUESTS: String = "no_of_guests"
    const val AREA: String = "area"
    const val NO_OF_TOILET: String = "no_of_toilet"
    const val NO_OF_BATH: String = "no_of_bath"
    const val NO_OF_BEDROOM: String = "no_of_bedroom"
    const val WIFI: String = "wifi"
    const val NO_OF_AIRCON: String = "no_of_aircon"
    const val NO_OF_FLOOR: String = "no_of_floor"
    const val CONTRACT_RULE: String = "contract_rule"
    const val LATITUDE: String = "latitude"
    const val LONGITUDE: String = "longitude"


    const val READ_STORAGE_PERMISSION_CODE = 1

    const val IMAGE_REQUEST_CODE_ONE = 2
    const val IMAGE_REQUEST_CODE_TWO = 3
    const val IMAGE_REQUEST_CODE_THREE = 4
    const val IMAGE_REQUEST_CODE_FOUR = 5
    const val IMAGE_REQUEST_CODE_FIVE = 6
    const val IMAGE_REQUEST_CODE_SIX = 7
    const val IMAGE_REQUEST_CODE_SEVEN = 8
    const val IMAGE_REQUEST_CODE_EIGHT = 9
    const val IMAGE_REQUEST_CODE_NINE = 10
    const val IMAGE_REQUEST_CODE_TEN = 11




    fun showImageChooser(activity: PostHouseFragment, requestCode: Int){
        var galleryIntent = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        activity.startActivityForResult(galleryIntent,requestCode)
    }

    fun getFileExtension(activity: FragmentActivity,uri: Uri?): String?{
        return MimeTypeMap.getSingleton().getExtensionFromMimeType(activity.contentResolver.getType(uri!!))
    }
}