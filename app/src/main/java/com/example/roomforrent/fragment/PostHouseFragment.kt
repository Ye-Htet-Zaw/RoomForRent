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
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Color
import android.location.Geocoder
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.RadioGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.roomforrent.R
import com.example.roomforrent.activity.ChooseAddressActivity
import com.example.roomforrent.activity.ListYourSpaceActivity
import com.example.roomforrent.adapter.MySpinnerAdapter
import com.example.roomforrent.models.House
import com.example.roomforrent.services.PostHouseService
import com.example.roomforrent.services.ServiceBuilder
import com.example.roomforrent.utils.Constants
import com.example.roomforrent.utils.Constants.POSITION
import com.example.roomforrent.utils.Constants.USERID
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_post_house.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class PostHouseFragment : BaseFragment() {

    private var list: ArrayList<MultipartBody.Part> = ArrayList()
    private var isLogin = false
    private var userID : String?=""
    private var userPosition :  Int?=null
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
    private lateinit var categoryAdapter: MySpinnerAdapter
    private lateinit var townshipAdapter: MySpinnerAdapter
    private lateinit var periodAdapter: MySpinnerAdapter
    var selectedCategory: String = ""
    var selectedAddress: String = ""
    var selectedPeriod: String = ""
    private var availableDate = SimpleDateFormat(
        "yyyy-MM-dd",
        Locale.getDefault()
    ).format(Date())
    private var categoryId: String = ""
    private var houseAddress: String = ""
    private var township: String = ""
    private var noOfGuest: String = ""
    private var noOfRoom: String = ""
    private var noOfBath: String = ""
    private var noOfToilet: String = ""
    private var area: String = ""
    private var noOfFloor: String = ""
    private var noOfAircon: String = ""
    private var wifi: Int = 0
    private var phoneOne: String = ""
    private var phoneTwo: String? = null
    private var rent: String = ""
    private var deposit: String = ""
    private var recommendedPoint: String = ""
    private var contractRule: String = ""
    private var period: String = ""
    private var latitude: Double = 0.0
    private var longitude: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        townshipAdapter = context?.let { createSpinnerAdapter(it, Constants.townshipArr) }!!
        categoryAdapter = context?.let { createSpinnerAdapter(it, Constants.categoryArr) }!!
        periodAdapter = context?.let { createSpinnerAdapter(it, Constants.periodArr) }!!

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadPostHouseScreen()

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        val contentResolver: ContentResolver= activity?.contentResolver!!

        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK &&
            requestCode == Constants.IMAGE_REQUEST_CODE_ONE && data!!.data != null){
                mSelectedImageFileUri1 = data.data
            Picasso.get().load(mSelectedImageFileUri1).noPlaceholder().centerCrop().fit()
                .into((img_1))
            val imageprojection = arrayOf<String>(MediaStore.Images.Media.DATA)
            val cursor: Cursor? =
                contentResolver.query(mSelectedImageFileUri1!!, imageprojection,null,null,null)

            if (cursor != null)
            {
                Log.i("cursor","cursor is not null")
                cursor.moveToFirst()
                val indexImage = cursor.getColumnIndex(imageprojection[0])
                val partImage = cursor.getString(indexImage)
                val imageRequest = prepareFilePart(partImage)
                list.add(imageRequest)
            }
        }
       if (resultCode == RESULT_OK &&
            requestCode == Constants.IMAGE_REQUEST_CODE_TWO && data!!.data != null){
            mSelectedImageFileUri2 = data.data
            Picasso.get().load(mSelectedImageFileUri2).noPlaceholder().centerCrop().fit()
                .into((img_2))
           val imageprojection = arrayOf<String>(MediaStore.Images.Media.DATA)
           val cursor: Cursor? =
               contentResolver.query(mSelectedImageFileUri2!!, imageprojection,null,null,null)

           if (cursor != null)
           {
               cursor.moveToFirst()
               val indexImage = cursor.getColumnIndex(imageprojection[0])
               val partImage = cursor.getString(indexImage)
               val imageRequest = prepareFilePart(partImage)
               list.add(imageRequest)
           }
        }
        if (resultCode == RESULT_OK &&
            requestCode == Constants.IMAGE_REQUEST_CODE_THREE && data!!.data != null){
            mSelectedImageFileUri3 = data.data
            Picasso.get().load(mSelectedImageFileUri3).noPlaceholder().centerCrop().fit()
                .into((img_3))

            val imageprojection = arrayOf<String>(MediaStore.Images.Media.DATA)
            val cursor: Cursor? =
                contentResolver.query(mSelectedImageFileUri3!!, imageprojection,null,null,null)

            if (cursor != null)
            {
                cursor.moveToFirst()
                val indexImage = cursor.getColumnIndex(imageprojection[0])
                val partImage = cursor.getString(indexImage)
                val imageRequest = prepareFilePart(partImage)
                list.add(imageRequest)
            }
        }
        if (resultCode == RESULT_OK &&
            requestCode == Constants.IMAGE_REQUEST_CODE_FOUR && data!!.data != null){
            mSelectedImageFileUri4 = data.data
            Picasso.get().load(mSelectedImageFileUri4).noPlaceholder().centerCrop().fit()
                .into((img_4))

            val imageprojection = arrayOf<String>(MediaStore.Images.Media.DATA)
            val cursor: Cursor? =
                contentResolver.query(mSelectedImageFileUri4!!, imageprojection,null,null,null)

            if (cursor != null)
            {
                cursor.moveToFirst()
                val indexImage = cursor.getColumnIndex(imageprojection[0])
                val partImage = cursor.getString(indexImage)
                val imageRequest = prepareFilePart(partImage)
                list.add(imageRequest)
            }
        }
        if (resultCode == RESULT_OK &&
            requestCode == Constants.IMAGE_REQUEST_CODE_FIVE && data!!.data != null){
            mSelectedImageFileUri5 = data.data
            Picasso.get().load(mSelectedImageFileUri5).noPlaceholder().centerCrop().fit()
                .into((img_5))

            val imageprojection = arrayOf<String>(MediaStore.Images.Media.DATA)
            val cursor: Cursor? =
                contentResolver.query(mSelectedImageFileUri5!!, imageprojection,null,null,null)

            if (cursor != null)
            {
                cursor.moveToFirst()
                val indexImage = cursor.getColumnIndex(imageprojection[0])
                val partImage = cursor.getString(indexImage)
                val imageRequest = prepareFilePart(partImage)
                list.add(imageRequest)
            }
        }
        if (resultCode == RESULT_OK &&
            requestCode == Constants.IMAGE_REQUEST_CODE_SIX && data!!.data != null){
            mSelectedImageFileUri6 = data.data
            Picasso.get().load(mSelectedImageFileUri6).noPlaceholder().centerCrop().fit()
                .into((img_6))

            val imageprojection = arrayOf<String>(MediaStore.Images.Media.DATA)

            val cursor: Cursor? =
                contentResolver.query(mSelectedImageFileUri6!!, imageprojection,null,null,null)

            if (cursor != null)
            {
                cursor.moveToFirst()
                val indexImage = cursor.getColumnIndex(imageprojection[0])
                val partImage = cursor.getString(indexImage)
                val imageRequest = prepareFilePart(partImage)
                list.add(imageRequest)
            }
        }
        if (resultCode == RESULT_OK &&
            requestCode == Constants.IMAGE_REQUEST_CODE_SEVEN && data!!.data != null){
            mSelectedImageFileUri7 = data.data
            Picasso.get().load(mSelectedImageFileUri7).noPlaceholder().centerCrop().fit()
                .into((img_7))

            val imageprojection = arrayOf<String>(MediaStore.Images.Media.DATA)
            val cursor: Cursor? =
                contentResolver.query(mSelectedImageFileUri7!!, imageprojection,null,null,null)

            if (cursor != null)
            {
                cursor.moveToFirst()
                val indexImage = cursor.getColumnIndex(imageprojection[0])
                val partImage = cursor.getString(indexImage)
                val imageRequest = prepareFilePart(partImage)
                list.add(imageRequest)
            }
        }
        if (resultCode == RESULT_OK &&
            requestCode == Constants.IMAGE_REQUEST_CODE_EIGHT && data!!.data != null){
            mSelectedImageFileUri8 = data.data
            Picasso.get().load(mSelectedImageFileUri8).noPlaceholder().centerCrop().fit()
                .into((img_8))

            val imageprojection = arrayOf<String>(MediaStore.Images.Media.DATA)
            val cursor: Cursor? =
                contentResolver.query(mSelectedImageFileUri8!!, imageprojection,null,null,null)

            if (cursor != null)
            {
                cursor.moveToFirst()
                val indexImage = cursor.getColumnIndex(imageprojection[0])
                val partImage = cursor.getString(indexImage)
                val imageRequest = prepareFilePart(partImage)
                list.add(imageRequest)
            }
        }
        if (resultCode == RESULT_OK &&
            requestCode == Constants.IMAGE_REQUEST_CODE_NINE && data!!.data != null){
            mSelectedImageFileUri9 = data.data
            Picasso.get().load(mSelectedImageFileUri9).noPlaceholder().centerCrop().fit()
                .into((img_9))

            val imageprojection = arrayOf<String>(MediaStore.Images.Media.DATA)
            val cursor: Cursor? =
                contentResolver.query(mSelectedImageFileUri9!!, imageprojection,null,null,null)

            if (cursor != null)
            {
                cursor.moveToFirst()
                val indexImage = cursor.getColumnIndex(imageprojection[0])
                val partImage = cursor.getString(indexImage)
                val imageRequest = prepareFilePart(partImage)
                list.add(imageRequest)
            }
        }
        if (resultCode == RESULT_OK &&
            requestCode == Constants.IMAGE_REQUEST_CODE_TEN && data!!.data != null){
            mSelectedImageFileUri10 = data.data
            Picasso.get().load(mSelectedImageFileUri10).noPlaceholder().centerCrop().fit()
                .into((img_10))

            val imageprojection = arrayOf<String>(MediaStore.Images.Media.DATA)
            val cursor: Cursor? =
                contentResolver.query(mSelectedImageFileUri10!!, imageprojection,null,null,null)

            if (cursor != null)
            {
                cursor.moveToFirst()
                val indexImage = cursor.getColumnIndex(imageprojection[0])
                val partImage = cursor.getString(indexImage)
                val imageRequest = prepareFilePart(partImage)
                list.add(imageRequest)
            }
        }


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_post_house, container, false)
    }

    override fun onResume() {
        super.onResume()
        loadPostHouseScreen()
        refreshFragment()

    }

    private fun refreshFragment(){
        requireFragmentManager().beginTransaction().setReorderingAllowed(false)
        requireFragmentManager().beginTransaction().detach(PostHouseFragment()).attach(PostHouseFragment()).commitAllowingStateLoss()
        et_guest.setText("")
        et_address.setText("")
        et_room.setText("")
        et_bath.setText("")
        et_toilet.setText("")
        et_area.setText("")
        et_floor.setText("")
        et_aircon.setText("")
        et_contact1.setText("")
        et_contact2.setText("")
        et_rent.setText("")
        et_deposit.setText("")
        et_recommended.setText("")
        et_contract_rule.setText("")
        et_available_date.text = ""
        rb_no.isChecked=false
        rb_yes.isChecked=false
    }

    private fun loadPostHouseScreen(){
        val share: SharedPreferences = context?.getSharedPreferences(
            "myPreference",
            Context.MODE_PRIVATE
        )!!
        isLogin = share.getBoolean("isLogin", false)
        //isLogin = true
        if(isLogin) {
            userID=share.getString(USERID,"")
            userPosition=share.getInt(POSITION,3)
            //userPosition = 1
            if (userPosition==1){
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

                iv_location.setOnClickListener {
                    startActivity(Intent(context,ChooseAddressActivity::class.java))
                }

                iv_available_date.setOnClickListener { view ->
                    clickDataPicker(view)
                }

                btn_post_house.setOnClickListener {
                    if(checkConnection())
                        setHouseData()
                }

                checkImageAndRadioData()
            }else{
                ph_createLayout.visibility=View.GONE
                Log.i("TestHouse", "User Position Owner")
                ph_blankLayout.visibility=View.VISIBLE
                ph_txtBlank.text=resources.getString(R.string.create_house_login_with_owner)
            }

        }else{
            ph_createLayout.visibility=View.GONE
            Log.i("TestHouse", "Need to Login")
            ph_blankLayout.visibility=View.VISIBLE
            ph_txtBlank.text=resources.getString(R.string.create_house_login)
        }


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

    private fun prepareFilePart(partName: String): MultipartBody.Part {
        val imageFile = File(partName)
        val reqBody = imageFile.asRequestBody("multipart/form-file".toMediaTypeOrNull())
        return MultipartBody.Part.createFormData("imageupload", imageFile.name, reqBody)
    }

    private fun setHouseData(){

        houseAddress = et_address.text.toString().trim()

        getLocationFromAddress(houseAddress)

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

        if (mSelectedImageFileUri1==null){
            tv_house_item.text = "Need to upload first photo of your house!"
            tv_house_item.setTextColor(Color.RED)
            tv_house_item.isFocusable=true
            tv_house_item.isFocusableInTouchMode=true
            tv_house_item.requestFocus()
        }
        else if (selectedCategory == "Select"){
            tv_catSpinner.text = "Need to select category!"
            tv_catSpinner.setTextColor(Color.RED)
            tv_catSpinner.isFocusable=true
            tv_catSpinner.isFocusableInTouchMode=true
            tv_catSpinner.requestFocus()
        }else if (selectedAddress == "Select"){
            tv_towSpinner.text = "Need to select Township!"
            tv_towSpinner.setTextColor(Color.RED)
            tv_towSpinner.isFocusable=true
            tv_towSpinner.isFocusableInTouchMode=true
            tv_towSpinner.requestFocus()
        }else if(houseAddress.isEmpty()){
            et_address.error = "Need to fill house address!"
            et_address.requestFocus()
        }else if(noOfGuest.isEmpty()){
            et_guest.error = "Need to fill guest!"
            et_guest.requestFocus()
        }else if(noOfBath.isEmpty()){
            et_bath.error = "Need to fill bathroom!"
            et_bath.requestFocus()
        }else if(noOfToilet.isEmpty()){
            et_toilet.error = "Need to fill toilet!"
            et_toilet.requestFocus()
        }else if(area.isEmpty()){
            et_area.error = "Need to fill house area!"
            et_area.requestFocus()
        }else if(phoneOne.isEmpty()){
            et_contact1.error = "Need to fill Phone No!"
            et_contact1.requestFocus()
        }else if(availableDate.isEmpty()){
            et_available_date.error = "Need to fill available date!"
            et_available_date.requestFocus()
        }else if(rent.isEmpty()){
            et_rent.error = "Need to fill house rent!"
            et_rent.requestFocus()
        }else if(deposit.isEmpty()){
            et_deposit.error = "Need to fill deposit!"
            et_deposit.requestFocus()
        }else if(recommendedPoint.isEmpty()){
            et_recommended.error = "Need to fill recommended point of house!"
            et_recommended.requestFocus()
        }else if(contractRule.isEmpty()){
            et_contract_rule.error = "Need to fill contract rule!"
            et_contract_rule.requestFocus()
        }else if (selectedPeriod == "Select") {
            tv_perSpinner.text = "Need to select Period!"
            tv_perSpinner.setTextColor(Color.RED)
            tv_perSpinner.isFocusable = true
            tv_perSpinner.isFocusableInTouchMode = true
            tv_perSpinner.requestFocus()
        }

       else {
            categoryId = when (selectedCategory) {
                "Condominum" -> {
                    "CAT0000001"
                }
                "WholeHouse" -> {
                    "CAT0000002"
                }
                "Apartment" -> {
                    "CAT0000003"
                }
                else -> {
                    "CAT0000004"
                }
            }
            var noOfRoomInt: Int? = null
            noOfRoomInt = if (noOfRoom==""){
                0
            }else noOfRoom.toInt()

            var noOfFloorInt: Int? = null
            noOfFloorInt = if (noOfFloor==""){
                0
            }else noOfFloor.toInt()

            var noOfAirconInt: Int? = null
            noOfAirconInt = if (noOfAircon==""){
                0
            }else noOfAircon.toInt()
            val house = House(
                category_ID = categoryId,
                township = township,
                house_ADDRESS = houseAddress,
                no_OF_GUESTS = noOfGuest.toInt(),
                no_OF_ROOM = noOfRoomInt!!,
                no_OF_BATH = noOfBath.toInt(),
                no_OF_TOILET = noOfToilet.toInt(),
                area = area.toInt(),
                no_OF_FLOOR = noOfFloorInt,
                no_OF_AIRCON = noOfAirconInt,
                wifi = wifi!!,
                phone_ONE = phoneOne,
                phone_TWO = phoneTwo!!,
                available_DATE = availableDate,
                rent = rent.toInt(),
                deposit = deposit.toInt(),
                recommented_POINTS = recommendedPoint,
                contract_RULE = contractRule,
                period = period.toInt(),
                user_ID = userID!!,
                longitude = longitude.toString(),
                latitude = latitude.toString(),
                expired_DATE = "2020-2-3",
                rent_FLAG = 0,
                delete_FLAG = 0,
                delete_DATETIME = "2020-2-3",
                creator_ID = userID!!,
                create_DATETIME = "2020-2-3",
                updator_ID = userID!!,
                update_DATETIME = "2020-2-3",
                house_ID = "HOU"
            )
             createHouse(house)
        }
    }

    private fun getLocationFromAddress(address: String){

        val geoCoder = Geocoder(context)
        try {
            val addresses = geoCoder.getFromLocationName("1600 Amphitheatre Pkwy, Mountain View, CA 94043, USA", 5)
            Log.i("CheckLatAndLon",addresses.size.toString())
            if (addresses.size > 0) {
                 latitude = addresses[0].latitude
                 longitude = addresses[0].longitude
                Log.i("CheckLatAndLon","$latitude and $longitude")
            }
        } catch (e: IOException) {
            e.printStackTrace()
            Log.i("CheckLatAndLon",e.message.toString()+"error")
        }
    }

    private fun createHouse(house: House){
        val destinationService  = ServiceBuilder.buildService(PostHouseService::class.java)
        destinationService.createHouse(house).enqueue(object: Callback<List<House>>{
            override fun onFailure(call: Call<List<House>>, t: Throwable) {
                Toast.makeText(context,"Fail to insert house data",Toast.LENGTH_SHORT).show()
            }
            override fun onResponse(call: Call<List<House>>, response: Response<List<House>>) {
                val res = response.body()
                if (response.code() == 200 && res!=null){
                    uploadImage(list)
                    val intent = Intent(context, ListYourSpaceActivity::class.java)
                    intent.putExtra(USERID, userID)
                    startActivity(intent)

                }else{
                    Toast.makeText(context,"Fail to insert house data",Toast.LENGTH_SHORT).show()
                }
            }

        })
    }

    private fun uploadImage(list: ArrayList<MultipartBody.Part>){
        val destinationService  = ServiceBuilder.buildService(PostHouseService::class.java)
        destinationService.uploadImages(list).enqueue(object : Callback<Void> {
            override fun onFailure(call: Call<Void>, t: Throwable) {
                t.message?.let { Log.i("UploadImageError", it+"UploadError") }
            }

            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                Log.i("UploadImageSuccess","Upload image to server")
            }
        })
    }

}