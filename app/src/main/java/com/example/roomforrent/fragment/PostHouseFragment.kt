/**
 *
 * PostHouseFragment
 *
 * 2021/04/22 HNT Create New
 *
 * insert house information into house table
 */
package com.example.roomforrent.fragment

import android.Manifest
import android.app.Activity.RESULT_OK
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.RadioGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.roomforrent.R
import com.example.roomforrent.activity.HouseListActivity
import com.example.roomforrent.activity.ListYourSpaceActivity
import com.example.roomforrent.activity.MainActivity
import com.example.roomforrent.adapter.MySpinnerAdapter
import com.example.roomforrent.models.House
import com.example.roomforrent.services.PostHouseService
import com.example.roomforrent.services.ServiceBuilder
import com.example.roomforrent.utils.Constants
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_house_list.*
import kotlinx.android.synthetic.main.fragment_favourite.*
import kotlinx.android.synthetic.main.fragment_post_house.*
import kotlinx.android.synthetic.main.fragment_post_house.toolBarMain
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class PostHouseFragment : Fragment() {

    var isLogin = false
    var userID : String?=""
    private var mSelectedImageFileUri1: Uri? = null
    private var mSelectedImageFileUri2: Uri? = null
    private var mSelectedImageFileUri3: Uri? = null
    private var mSelectedImageFileUri4: Uri? = null
    private var mSelectedImageFileUri5: Uri? = null
    private var mSelectedImageFileUri6: Uri? = null
    private var mSelectedImageFileUri7: Uri? = null
    private var mSelectedImageFileUri8: Uri? = null
    private var mSelectedImageFileUri9: Uri? = null
    private var mSelectedImageFileUri10: Uri? = null
    lateinit var categoryAdapter: MySpinnerAdapter
    lateinit var townshipAdapter: MySpinnerAdapter
    lateinit var periodAdapter: MySpinnerAdapter
    var selectedCategory: String = ""
    var selectedAddress: String = ""
    var selectedPeriod: String = ""
    var availableDate = SimpleDateFormat(
        "yyyy-MM-dd",
        Locale.getDefault()
    ).format(Date())
    var categoryId: String = ""
    var houseAddress: String = ""
    var township: String = ""
    var noOfGuest: String = ""
    var noOfRoom: String = ""
    var noOfBath: String = ""
    var noOfToilet: String = ""
    var area: String = ""
    var noOfFloor: String = ""
    var noOfAircon: String = ""
    var wifi: Int = 0
    var phoneOne: String = ""
    var phoneTwo: String = ""
    var rent: String = ""
    var deposit: String = ""
    var recommendedPoint: String = ""
    var contractRule: String = ""
    var period: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        townshipAdapter = context?.let { createSpinnerAdapter(it, Constants.townshipArr) }!!
        categoryAdapter = context?.let { createSpinnerAdapter(it, Constants.categoryArr) }!!
        periodAdapter = context?.let { createSpinnerAdapter(it, Constants.periodArr) }!!

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val intent = Intent(context, MainActivity::class.java)
        toolBarMain.setNavigationOnClickListener { startActivity(intent) }

        val share: SharedPreferences = context?.getSharedPreferences(
            "myPreference",
            Context.MODE_PRIVATE
        )!!
        isLogin = share.getBoolean("isLogin", false)

        if(isLogin) {
            userID=share.getString(Constants.USERID,"")
            ph_createLayout.visibility=View.VISIBLE
            ph_blankLayout.visibility = View.GONE
            //set Adapter for spinner
            ph_categorySpinner.adapter = categoryAdapter
            ph_addressSpinner.adapter = townshipAdapter
            ph_periodSpinner.adapter = periodAdapter

            ph_categorySpinner.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        selectedCategory = Constants.categoryArr.get(position)
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {

                    }

                }

            ph_addressSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    selectedAddress = Constants.townshipArr.get(position)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {

                }

            }

            ph_periodSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    selectedPeriod = Constants.periodArr.get(position)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {

                }

            }


            iv_available_date.setOnClickListener { view ->
                clickDataPicker(view)
            }

            btn_post_house.setOnClickListener {
                setHouseData()
            }

            checkImageAndRadioData()
        }else{
            ph_createLayout.visibility=View.GONE
            Log.i("TestFavourite", "Need to Login")
            ph_blankLayout.visibility=View.VISIBLE
            ph_txtBlank.text=resources.getString(R.string.create_house_login)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK &&
            requestCode == Constants.IMAGE_REQUEST_CODE_ONE && data!!.data != null){
                mSelectedImageFileUri1 = data.data
            Picasso.get().load(mSelectedImageFileUri1).noPlaceholder().centerCrop().fit()
                .into((img_1));
            }
       if (resultCode == RESULT_OK &&
            requestCode == Constants.IMAGE_REQUEST_CODE_TWO && data!!.data != null){
            mSelectedImageFileUri2 = data.data
            Picasso.get().load(mSelectedImageFileUri2).noPlaceholder().centerCrop().fit()
                .into((img_2));
        }
        if (resultCode == RESULT_OK &&
            requestCode == Constants.IMAGE_REQUEST_CODE_THREE && data!!.data != null){
            mSelectedImageFileUri3 = data.data
            Picasso.get().load(mSelectedImageFileUri3).noPlaceholder().centerCrop().fit()
                .into((img_3));
        }
        if (resultCode == RESULT_OK &&
            requestCode == Constants.IMAGE_REQUEST_CODE_FOUR && data!!.data != null){
            mSelectedImageFileUri4 = data.data
            Picasso.get().load(mSelectedImageFileUri4).noPlaceholder().centerCrop().fit()
                .into((img_4));
        }
        if (resultCode == RESULT_OK &&
            requestCode == Constants.IMAGE_REQUEST_CODE_FIVE && data!!.data != null){
            mSelectedImageFileUri5 = data.data
            Picasso.get().load(mSelectedImageFileUri5).noPlaceholder().centerCrop().fit()
                .into((img_5));
        }
        if (resultCode == RESULT_OK &&
            requestCode == Constants.IMAGE_REQUEST_CODE_SIX && data!!.data != null){
            mSelectedImageFileUri6 = data.data
            Picasso.get().load(mSelectedImageFileUri6).noPlaceholder().centerCrop().fit()
                .into((img_6));
        }
        if (resultCode == RESULT_OK &&
            requestCode == Constants.IMAGE_REQUEST_CODE_SEVEN && data!!.data != null){
            mSelectedImageFileUri7 = data.data
            Picasso.get().load(mSelectedImageFileUri7).noPlaceholder().centerCrop().fit()
                .into((img_7));
        }
        if (resultCode == RESULT_OK &&
            requestCode == Constants.IMAGE_REQUEST_CODE_EIGHT && data!!.data != null){
            mSelectedImageFileUri8 = data.data
            Picasso.get().load(mSelectedImageFileUri8).noPlaceholder().centerCrop().fit()
                .into((img_8));
        }
        if (resultCode == RESULT_OK &&
            requestCode == Constants.IMAGE_REQUEST_CODE_NINE && data!!.data != null){
            mSelectedImageFileUri9 = data.data
            Picasso.get().load(mSelectedImageFileUri9).noPlaceholder().centerCrop().fit()
                .into((img_9));
        }
        if (resultCode == RESULT_OK &&
            requestCode == Constants.IMAGE_REQUEST_CODE_TEN && data!!.data != null){
            mSelectedImageFileUri10 = data.data
            Picasso.get().load(mSelectedImageFileUri10).noPlaceholder().centerCrop().fit()
                .into((img_10));
        }


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_post_house, container, false)
    }

    private fun createSpinnerAdapter(context: Context, arr: ArrayList<String>): MySpinnerAdapter {
        return MySpinnerAdapter(context, arr)
    }

    private fun checkImageAndRadioData(){

        val radioGroup = view?.findViewById(R.id.rb_group) as RadioGroup

        radioGroup.setOnCheckedChangeListener { group, checkedId -> // checkedId is the RadioButton selected
            when (checkedId) {
                R.id.rb_yes -> {
                    wifi = 1
                }
                R.id.rb_no -> {
                    wifi = 0
                }
            }
        }

        img_1.setOnClickListener{
            if(context?.let { it1 -> ContextCompat.checkSelfPermission(it1, Manifest.permission.READ_EXTERNAL_STORAGE) }
                == PackageManager.PERMISSION_GRANTED){
                Constants.showImageChooser(this,Constants.IMAGE_REQUEST_CODE_ONE)
            }
            else{
                activity?.let { it1 ->
                    ActivityCompat.requestPermissions(
                        it1,
                        arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                        Constants.READ_STORAGE_PERMISSION_CODE
                    )
                }
            }
        }

        img_2.setOnClickListener{
            if(context?.let { it1 -> ContextCompat.checkSelfPermission(it1, Manifest.permission.READ_EXTERNAL_STORAGE) }
                == PackageManager.PERMISSION_GRANTED){
                Constants.showImageChooser(this,Constants.IMAGE_REQUEST_CODE_TWO)
            }
            else{
                activity?.let { it1 ->
                    ActivityCompat.requestPermissions(
                        it1,
                        arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                        Constants.READ_STORAGE_PERMISSION_CODE
                    )
                }
            }
        }

        img_3.setOnClickListener{
            if(context?.let { it1 -> ContextCompat.checkSelfPermission(it1, Manifest.permission.READ_EXTERNAL_STORAGE) }
                == PackageManager.PERMISSION_GRANTED){
                Constants.showImageChooser(this,Constants.IMAGE_REQUEST_CODE_THREE)
            }
            else{
                activity?.let { it1 ->
                    ActivityCompat.requestPermissions(
                        it1,
                        arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                        Constants.READ_STORAGE_PERMISSION_CODE
                    )
                }
            }
        }

        img_4.setOnClickListener{
            if(context?.let { it1 -> ContextCompat.checkSelfPermission(it1, Manifest.permission.READ_EXTERNAL_STORAGE) }
                == PackageManager.PERMISSION_GRANTED){
                Constants.showImageChooser(this,Constants.IMAGE_REQUEST_CODE_FOUR)
            }
            else{
                activity?.let { it1 ->
                    ActivityCompat.requestPermissions(
                        it1,
                        arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                        Constants.READ_STORAGE_PERMISSION_CODE
                    )
                }
            }
        }

        img_5.setOnClickListener{
            if(context?.let { it1 -> ContextCompat.checkSelfPermission(it1, Manifest.permission.READ_EXTERNAL_STORAGE) }
                == PackageManager.PERMISSION_GRANTED){
                Constants.showImageChooser(this,Constants.IMAGE_REQUEST_CODE_FIVE)
            }
            else{
                activity?.let { it1 ->
                    ActivityCompat.requestPermissions(
                        it1,
                        arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                        Constants.READ_STORAGE_PERMISSION_CODE
                    )
                }
            }
        }

        img_6.setOnClickListener{
            if(context?.let { it1 -> ContextCompat.checkSelfPermission(it1, Manifest.permission.READ_EXTERNAL_STORAGE) }
                == PackageManager.PERMISSION_GRANTED){
                Constants.showImageChooser(this,Constants.IMAGE_REQUEST_CODE_SIX)
            }
            else{
                activity?.let { it1 ->
                    ActivityCompat.requestPermissions(
                        it1,
                        arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                        Constants.READ_STORAGE_PERMISSION_CODE
                    )
                }
            }
        }

        img_7.setOnClickListener{
            if(context?.let { it1 -> ContextCompat.checkSelfPermission(it1, Manifest.permission.READ_EXTERNAL_STORAGE) }
                == PackageManager.PERMISSION_GRANTED){
                Constants.showImageChooser(this,Constants.IMAGE_REQUEST_CODE_SEVEN)
            }
            else{
                activity?.let { it1 ->
                    ActivityCompat.requestPermissions(
                        it1,
                        arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                        Constants.READ_STORAGE_PERMISSION_CODE
                    )
                }
            }
        }

        img_8.setOnClickListener{
            if(context?.let { it1 -> ContextCompat.checkSelfPermission(it1, Manifest.permission.READ_EXTERNAL_STORAGE) }
                == PackageManager.PERMISSION_GRANTED){
                Constants.showImageChooser(this,Constants.IMAGE_REQUEST_CODE_EIGHT)
            }
            else{
                activity?.let { it1 ->
                    ActivityCompat.requestPermissions(
                        it1,
                        arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                        Constants.READ_STORAGE_PERMISSION_CODE
                    )
                }
            }
        }
        img_9.setOnClickListener{
            if(context?.let { it1 -> ContextCompat.checkSelfPermission(it1, Manifest.permission.READ_EXTERNAL_STORAGE) }
                == PackageManager.PERMISSION_GRANTED){
                Constants.showImageChooser(this,Constants.IMAGE_REQUEST_CODE_NINE)
            }
            else{
                activity?.let { it1 ->
                    ActivityCompat.requestPermissions(
                        it1,
                        arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                        Constants.READ_STORAGE_PERMISSION_CODE
                    )
                }
            }
        }
        img_10.setOnClickListener{
            if(context?.let { it1 -> ContextCompat.checkSelfPermission(it1, Manifest.permission.READ_EXTERNAL_STORAGE) }
                == PackageManager.PERMISSION_GRANTED){
                Constants.showImageChooser(this,Constants.IMAGE_REQUEST_CODE_TEN)
            }
            else{
                activity?.let { it1 ->
                    ActivityCompat.requestPermissions(
                        it1,
                        arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                        Constants.READ_STORAGE_PERMISSION_CODE
                    )
                }
            }
        }

    }

    private fun clickDataPicker(view: View) {
        val c = Calendar.getInstance()
        val year =
            c.get(Calendar.YEAR) // Returns the value of the given calendar field. This indicates YEAR
        val month = c.get(Calendar.MONTH) // This indicates the Month
        val day = c.get(Calendar.DAY_OF_MONTH) // This indicates the Day
        val dpd = context?.let {
            DatePickerDialog(
                it,
                DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                    val selectedDate = "$dayOfMonth/${monthOfYear + 1}/$year"

                    // Selected date it set to the TextView to make it visible to user.
                    et_available_date.setText(selectedDate).toString()
                },
                year,
                month,
                day
            )
        }
        dpd?.show() // It is used to show the datePicker Dialog.
    }

    private fun setHouseData(){
        categoryId = when (selectedCategory) {
            "Condominum" -> {
                "1"
            }
            "WholeHouse" -> {
                "2"
            }
            "Apartment" -> {
                "3"
            }
            else -> {
                "4"
            }
        }

        houseAddress = et_address.text.toString().trim()
        township = selectedAddress
        noOfGuest = et_guest.text.toString().trim()
        noOfRoom = et_room.text.toString().trim()
        noOfBath = et_bath.text.toString().trim()
        noOfToilet = et_toilet.text.toString().trim()
        area = et_area.text.toString().trim()
        noOfFloor = et_floor.text.toString().trim()
        noOfAircon = et_aircon.text.toString().trim()
        phoneOne = et_contact1.text.toString().trim()
        phoneTwo = et_contact2.text.toString().trim()
        rent = et_rent.text.toString().trim()
        availableDate=et_available_date.text.toString().trim()
        deposit = et_deposit.text.toString().trim()
        recommendedPoint = et_recommended.text.toString().trim()
        contractRule = et_contract_rule.text.toString().trim()
        period = selectedPeriod



//
//            val formatter = SimpleDateFormat("dd/MM/yyyy")
//            val date1 = formatter.parse(availableDate)
        if (categoryId.isNotEmpty() && township.isNotEmpty() && houseAddress.isNotEmpty() &&
            noOfGuest.isNotEmpty() && noOfRoom.isNotEmpty() && noOfBath.isNotEmpty() &&
            noOfToilet.isNotEmpty() && area.isNotEmpty() && noOfFloor.isNotEmpty() &&
            noOfAircon.isNotEmpty() && wifi !== null && phoneOne.isNotEmpty() &&
            phoneTwo.isNotEmpty() && availableDate.isNotEmpty() && rent.isNotEmpty() &&
            deposit.isNotEmpty() && recommendedPoint.isNotEmpty() && contractRule.isNotEmpty() &&
            period.isNotEmpty()
        ) {
            val house = House(
                category_ID = categoryId,
                township = township,
                house_ADDRESS = houseAddress,
                no_OF_GUESTS = noOfGuest.toInt(),
                no_OF_ROOM = noOfRoom.toInt(),
                no_OF_BATH = noOfBath.toInt(),
                no_OF_TOILET = noOfToilet.toInt(),
                area = area.toInt(),
                no_OF_FLOOR = noOfFloor.toInt(),
                no_OF_AIRCON = noOfAircon.toInt(),
                wifi = wifi,
                phone_ONE = phoneOne,
                phone_TWO = phoneTwo,
                available_DATE = availableDate,
                rent = rent.toInt(),
                deposit = deposit.toInt(),
                recommented_POINTS = recommendedPoint,
                contract_RULE = contractRule,
                period = period.toInt(),
                user_ID = userID!!,
                longitude = "09",
                latitude = "93",
                expired_DATE = "2020-2-3",
                rent_FLAG = 0,
                delete_FLAG = 0,
                delete_DATETIME = "2020-2-3",
                creator_ID = userID!!,
                create_DATETIME = "2020-2-3",
                updator_ID = "CRD000002",
                update_DATETIME = "2020-2-3",
                house_ID = "009"
            )

            var createHouseLiveDate: LiveData<House>? = null
            createHouseLiveDate = createHouse(house)
            if (createHouseLiveDate != null) {
                val intent = Intent(context, ListYourSpaceActivity::class.java)
                 startActivity(intent)
            } else {
                Toast.makeText(context, "Something went wrong!", Toast.LENGTH_SHORT).show()
            }

        }else {
            Toast.makeText(context, "Please fill data successfully!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun createHouse(house: House): LiveData<House> {
        val data = MutableLiveData<House>()
        val destinationService  = ServiceBuilder.buildService(PostHouseService::class.java)
        destinationService.createHouse(house).enqueue(object : Callback<House>{
            override fun onFailure(call: Call<House>, t: Throwable) {
                data.value = null
            }

            override fun onResponse(call: Call<House>, response: Response<House>) {
                val res = response.body()
                if (response.code() == 200 && res!=null){
                    data.value = res
                }else{
                    data.value = null
                }
            }

        })
        return data
    }

}