package com.example.roomforrent.activity

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.EditText
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.example.roomforrent.R
import com.example.roomforrent.models.User
import com.example.roomforrent.services.ServiceBuilder
import com.example.roomforrent.services.UserProfileService
import com.example.roomforrent.utils.Constants
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_personal_information.*
import kotlinx.android.synthetic.main.fragment_login_profile.*
import kotlinx.android.synthetic.main.fragment_post_house.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class PersonalInformationActivity : BaseActivity() {

     var dateFromDbString: String?=null
     var dateString:String?=null
     var multiPartImage: MultipartBody.Part? = null

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_personal_information)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, android.R.color.transparent)
        window.setBackgroundDrawable(resources.getDrawable(R.drawable.toolbarbg))
        setUpToolbar()

        var userId=intent.getStringExtra(Constants.USERID)
         getUserInfoById(userId!!)

        add_image_floating_btn.setOnClickListener {
            chooseImage()
        }

        //to change gender when click on gender edittext
        et_gender.setOnClickListener {
            val dialog = Dialog(this)
            dialog.setContentView(R.layout.gender_popup_window)
            dialog.setTitle("This is my custom dialog box")
            dialog.setCancelable(false)
            dialog.show()

            //when choose male radio button
            var rbm: RadioButton = dialog.findViewById(R.id.rb_male)
            rbm.setOnClickListener() {
                Toast.makeText(this, rbm.text, Toast.LENGTH_SHORT).show()
                var genderText = findViewById<TextView>(R.id.et_gender)
                genderText.text = rbm.text
                dialog.dismiss()
            }

            //when choose female radio button
            var rbf: RadioButton = dialog.findViewById(R.id.rb_female)
            rbf.setOnClickListener() {
                Toast.makeText(this, rbf.text, Toast.LENGTH_SHORT).show()
                var genderText = findViewById<TextView>(R.id.et_gender)
                genderText.text = rbf.text
                dialog.dismiss()
            }

            //when choose other radio button
            var other: RadioButton = dialog.findViewById(R.id.rb_other)
            other.setOnClickListener() {
                Toast.makeText(this, other.text, Toast.LENGTH_SHORT).show()
                var genderText = findViewById<TextView>(R.id.et_gender)
                genderText.text = other.text
                dialog.dismiss()
            }
        }//end of gender


        val editText = findViewById<EditText>(R.id.et_birth_date)
        editText.setOnClickListener() { view->
            clickDatePicker(view)

        }//end of dateTime

        //if save text is clicked ,updatefun is worked
        tv_titlePI.setOnClickListener { view->
            showProgressDialog("Please Wait.....")
            updatePersonalInfo(view, userId)
        }

        //if user name is inputed error msg is showed
        et_user_name.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
               tv_user_name_error.isVisible=false
            }
            override fun afterTextChanged(s: Editable?) {

            }

        })
    }//end of onCreate

    //choose photo from gallery
    private fun chooseImage() {
        Dexter.withActivity(this).withPermissions(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        ).withListener(object : MultiplePermissionsListener {
            override fun onPermissionsChecked(report: MultiplePermissionsReport?) {

                // Here after all the permission are granted launch the gallery to select and image.
                if (report!!.areAllPermissionsGranted()) {

                    val galleryIntent = Intent(
                        Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                    )
                    startActivityForResult(galleryIntent, GALLERY)
                }
            }

            override fun onPermissionRationaleShouldBeShown(
                permissions: MutableList<PermissionRequest>?,
                token: PermissionToken?
            ) {
                showRationalDialogForPermissions()
            }
        }).onSameThread()
            .check()
    }

    //permission
    private fun showRationalDialogForPermissions() {
        AlertDialog.Builder(this).setMessage("It Looks like you have turned off permissions required for this feature. It can be enabled under Application Settings")
            .setPositiveButton("GO TO SETTINGS")
            { _, _ ->
                try {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    val uri = Uri.fromParts("package", packageName, null)
                    intent.data = uri
                    startActivity(intent)
                } catch (e: ActivityNotFoundException) {
                    e.printStackTrace()
                }
            }.setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }.show()
    }

    private fun prepareFilePart(partName: String): MultipartBody.Part {
        val imageFile = File(partName)
        val reqBody = imageFile.asRequestBody("multipart/form-file".toMediaTypeOrNull())
        return MultipartBody.Part.createFormData("imageupload", imageFile.name, reqBody)
    }

    public  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == GALLERY){
                if (data != null){
                    var contentURL = data.data
                    Picasso.get().load(contentURL).noPlaceholder().centerCrop().fit()
                        .into((cv_information_profile))
                    val imageprojection = arrayOf<String>(MediaStore.Images.Media.DATA)
                    val cursor: Cursor? =
                        contentResolver.query(contentURL!!, imageprojection,null,null,null)

                    if (cursor != null)
                    {
                        Log.i("cursor","cursor is not null")
                        cursor.moveToFirst();
                        var indexImage = cursor.getColumnIndex(imageprojection[0]);
                        var partImage = cursor.getString(indexImage)
                        val imageRequest = prepareFilePart(partImage)
                        multiPartImage = imageRequest
                    }
                }
            }
        }
    }

    //toolbar
    private fun setUpToolbar(){
        setSupportActionBar(toolbar_personal_info)
        val actionBar=supportActionBar
        if(actionBar !=null){
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24)
        }
        toolbar_personal_info.setNavigationOnClickListener{onBackPressed()}
    }

    //to show date picker
    fun clickDatePicker(view: View){

        //to show current date
        val myCalendar= Calendar.getInstance()
        val year=myCalendar.get(Calendar.YEAR)
        val month=myCalendar.get(Calendar.MONTH)
        val day=myCalendar.get(Calendar.DAY_OF_MONTH)

        val dpd= DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener
            { view, Selectedyear, Selectedmonth, SelecteddayOfMonth ->
                Toast.makeText(
                    this,
                    "The choosen year is $Selectedyear, the month is $Selectedmonth, and the day is $SelecteddayOfMonth ",
                    Toast.LENGTH_LONG
                ).show()

                //insert dateString to give it to db
                dateString = "$Selectedyear-${Selectedmonth + 1}-$SelecteddayOfMonth"

                //to show in android
                val selectedDate = "$SelecteddayOfMonth/${Selectedmonth + 1}/$Selectedyear"
                var genderText = findViewById<TextView>(R.id.et_birth_date)
                genderText.text = selectedDate

            },
            year,
            month,
            day
        )
        dpd.datePicker.setMaxDate(Date().time - 86400000)
        dpd.show()
    }

    //get user info from api
    private fun getUserInfoById(user_id: String) {
        //initiate the service
        val destinationService  = ServiceBuilder.buildService(UserProfileService::class.java)
        val requestCall =destinationService.getUserInfo(user_id)
        //make network call asynchronously
        requestCall.enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                Log.d("Response", "onResponse: ${response.body()}")
                if (response.isSuccessful) {
                    val user = response.body()!!
                    Log.d("Response", "countrylist size : ${user.user_name}")
                    Toast.makeText(
                        this@PersonalInformationActivity,
                        "user name is ${user.user_name}",
                        Toast.LENGTH_SHORT
                    ).show()

                    Picasso.get()
                        .load("http://192.168.101.12:9090/image/user/" + user.user_id + ".jpg").into(
                            cv_information_profile
                        )
                    et_user_name.setText(user.user_name)
                    when (user.user_gender) {
                        0 -> et_gender.setText("Male")
                        1 -> et_gender.setText("Female")
                        2 -> et_gender.setText("Other")
                        else -> et_gender.setText("Null")
                    }

                    //assign date from db to local var to show in android screen
                    var dateFromDb: Date = user.user_dob
                    //to show date from db to android
                    val pattern = "dd/MM/yyyy"
                    val simpleDateFormat = SimpleDateFormat(pattern)
                    val date = simpleDateFormat.format(dateFromDb)
                    et_birth_date.setText(date)

                    //if datepicker is not used to save orignal date
                    val pattern1 = "yyyy-MM-dd"
                    val simpleDateFormat1 = SimpleDateFormat(pattern1)
                    dateFromDbString = simpleDateFormat1.format(dateFromDb)

                    et_email.setText(user.user_email)
                    et_phone_num1.setText(user.phone_one)
                    et_phone_num2.setText(user.phone_two)

                } else {
                    Toast.makeText(
                        this@PersonalInformationActivity,
                        "Something went wrong ${response.message()}",
                        Toast.LENGTH_SHORT
                    ).show()
                    Log.i("Something", "Something went wrong ${response.message()}")
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Toast.makeText(
                    this@PersonalInformationActivity,
                    "Something went wrong $t",
                    Toast.LENGTH_SHORT
                ).show()
                Log.i("errorM", t.message.toString())
            }
        })
    }

    //to update personal info
    private fun updatePersonalInfo(view: View, user_id: String){

        var name = et_user_name.text.toString()
        var selectedGender=et_gender.text.toString()
        var gender=0
        when(selectedGender){
            "Male" -> gender = 0
            "Female" -> gender = 1
            "Other" -> gender = 2
        }
        var email=et_email.text.toString()
        var phoneOne=et_phone_num1.text.toString()
        var phoneTwo=et_phone_num2.text.toString()

      if(name.isEmpty()){
           tv_user_name_error.text="Name must not be empty"
           tv_user_name_error.isVisible=true

       }else if(name[0].isDigit()){
              tv_user_name_error.text="Name should start with A-Z"
              tv_user_name_error.isVisible=true

       }else{

           // Create JSON using JSONObject
           val jsonObject = JSONObject()
           jsonObject.put("user_id", user_id)
           jsonObject.put("user_name", name)
           jsonObject.put("user_email", email)
           jsonObject.put("phone_one", phoneOne)
           jsonObject.put("phone_two", phoneTwo)
           jsonObject.put("user_gender", gender)

           if(dateString==null) {
               jsonObject.put("dobString", dateFromDbString)
           }else{
               jsonObject.put("dobString", dateString)
           }

           jsonObject.put("updator_id", user_id)


           // Convert JSONObject to String
           val jsonObjectString = jsonObject.toString()
           // Create RequestBody
           val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())
           CoroutineScope(Dispatchers.IO).launch {
               val destinationService  = ServiceBuilder.buildService(UserProfileService::class.java)
               val response =destinationService.updateUserInfo(requestBody)
               withContext(Dispatchers.Main) {
                   if (response.isSuccessful) {
                       uploadUserImage(multiPartImage)
                       hideProgressDialog()
                       Toast.makeText(
                           this@PersonalInformationActivity,
                           "successful updated",
                           Toast.LENGTH_SHORT
                       ).show()
                       //tv_owner_name.text=et_user_name.text.toString()
                   } else {
                       hideProgressDialog()
                       Log.e("RETROFIT_ERROR", response.code().toString())
                       Toast.makeText(
                           this@PersonalInformationActivity,
                           "Updated Fail",
                           Toast.LENGTH_SHORT
                       ).show()

                   }
               }
           }
       }
    }

    private fun uploadUserImage(multiPartImage: MultipartBody.Part?) {
        val destinationService  = ServiceBuilder.buildService(UserProfileService::class.java)
        if (multiPartImage != null) {
            destinationService.uploadUserImages(multiPartImage).enqueue(object : Callback<Void> {
                override fun onFailure(call: Call<Void>, t: Throwable) {
                    t.message?.let { Log.i("UploadImageError", it+"UploadError") }
                }
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    Log.i("UploadImageSuccess","Upload image to server")
                }
            })
        }
    }

    companion object {
        private const val GALLERY = 1
    }
}