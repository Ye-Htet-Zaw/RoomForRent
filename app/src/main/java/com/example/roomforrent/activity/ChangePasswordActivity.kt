package com.example.roomforrent.activity

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.example.roomforrent.R
import com.example.roomforrent.models.UserLogin
import com.example.roomforrent.services.ChangePasswordService
import com.example.roomforrent.services.ServiceBuilder
import com.example.roomforrent.utils.Constants.USERID
import kotlinx.android.synthetic.main.activity_change_password.*
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChangePasswordActivity : AppCompatActivity() {

    var cur_pw=""
    var input_cur_pw=""
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)
        var user_id:String= intent.extras?.getString(USERID).toString()
        Log.i("TestingApi", user_id)
        var new_pw=et_new_pw.text.toString().trim()
        input_cur_pw=et_current_pw.text.toString().trim()
        var confirmed_pw=et_confirmPassword.text.toString().trim()
        val destinationService = ServiceBuilder.buildService(ChangePasswordService::class.java)
        //For Change status bar color
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, android.R.color.transparent)
        window.setBackgroundDrawable(resources.getDrawable(R.drawable.toolbarbg))
        setUpActionBar()

        btn_updatePassword.setOnClickListener() {
            var callGetCurPw=destinationService.getPassword(user_id)
            callGetCurPw.enqueue(object : Callback<String>{
                override fun onFailure(call: Call<String>, t: Throwable) {
                    Log.i("TestingApi", "fail"+t.message)
                }

                override fun onResponse(call: Call<String>, response: Response<String>) {
                    cur_pw=response.body().toString()
                    Log.i("TestingApi", "sucessful")
                }

            })
            Log.i("TestingApi", "IIII"+cur_pw)
            if(cur_pw.equals(input_cur_pw)){
            if(new_pw==confirmed_pw){
                var callGetPw=destinationService.updatePassword(user_id,confirmed_pw)
                    callGetPw.enqueue(object : Callback<UserLogin> {
                        override fun onResponse(call: Call<UserLogin>, response: Response<UserLogin>) {
                            val intent= Intent(this@ChangePasswordActivity,PersonalInformationActivity::class.java)
                            Log.i("TestingApi", "Update Sucessful" +response.body()!!.password)
                        }

                        override fun onFailure(call: Call<UserLogin>, t: Throwable) {
                            Log.i("TestingApi", "update fail"+t.message)
                        }

                    })
            }

            }

//            when {
//
//
//                (et_current_pw.text.toString().trim().isEmpty()) ->
//                {et_current_pw.error = "Please Insert Current Password"
//                    et_current_pw.requestFocus()
//                    return@setOnClickListener
//                }
//                (et_new_pw.text.toString().trim().isEmpty()) ->
//                {et_new_pw.error = "Please New Current Password"
//                    et_new_pw.requestFocus()
//                    return@setOnClickListener
//                }
//                (et_current_pw.text.toString().trim()==et_new_pw.text.toString().trim())->
//                {
//                    Toast.makeText(this,"Current Password and New Password is same",Toast.LENGTH_LONG)
//                }
//                (et_confirmPassword.text.toString().trim().isEmpty()) ->
//                {et_confirmPassword.error = "Please Insert Confirm Password"
//                    et_confirmPassword.requestFocus()
//                    return@setOnClickListener
//
//                }
//
//                (et_new_pw.text.toString().trim()!=et_confirmPassword.text.toString().trim())->
//                {
//                    Toast.makeText(this,"Your Confirmed Password is Wrong",Toast.LENGTH_LONG)
//                }
//                else -> {
//                    val destinationService = ServiceBuilder.buildService(ChangePasswordService::class.java)
//
//                    callGetPw=destinationService.updatePassword("UserId",et_confirmPassword.text.toString().trim())
//                    callGetPw.enqueue(object : Callback<UserLogin> {
//                        override fun onResponse(call: Call<UserLogin>, response: Response<UserLogin>) {
//                            val intent= Intent(this@ChangePasswordActivity,PersonalInformationActivity::class.java)
//                            Log.i("TestingApi", "Update Sucessful" +response.body()!!.password)
//                        }
//
//                        override fun onFailure(call: Call<UserLogin>, t: Throwable) {
//                            Log.i("TestingApi", "update fail"+t.message)
//                        }
//
//                    })
//
//                }
//            }

        }
    }


        private fun setUpActionBar() {
        setSupportActionBar(toolBarChangePassword)
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back)
            actionBar.title = "Change Password"
        }
        toolBarChangePassword.setNavigationOnClickListener { onBackPressed() }
    }



}

