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
import kotlinx.android.synthetic.main.activity_change_password.*
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChangePasswordActivity : AppCompatActivity() {
    lateinit var callGetPw: Call<UserLogin>
    var user_id:String= intent.extras?.getString("UserId").toString()
    val cur_pw=et_current_pw.text.toString().trim()
    val new_pw=et_new_pw.text.toString().trim()
    val confirmed_pw=et_confirmPassword.text.toString().trim()

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)
        val destinationService = ServiceBuilder.buildService(ChangePasswordService::class.java)
        //For Change status bar color
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, android.R.color.transparent)
        window.setBackgroundDrawable(resources.getDrawable(R.drawable.toolbarbg))
        setUpActionBar()

        btn_updatePassword.setOnClickListener() {
            var cur_pw=destinationService.getPassword(user_id)
            Log.i("TestingApi", "IIII"+cur_pw)
            if(checkPassword(cur_pw)){
            if(new_pw==confirmed_pw){
                callGetPw=destinationService.updatePassword(user_id,confirmed_pw)
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

        private fun checkPassword(password:String):Boolean{

            when (cur_pw) {
                password -> return  true
                else -> return false
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

