/**
 *
 * OTPVerifyActivity
 *
 * 2021/04/30 YHZ Create New
 *
 * OTP SMS Verify
 */
package com.example.roomforrent.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.example.roomforrent.R
import com.example.roomforrent.fragment.LoginProfileFragment
import com.example.roomforrent.models.Phone
import com.example.roomforrent.services.OTPPhoneService
import com.example.roomforrent.services.ServiceBuilder
import com.example.roomforrent.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.android.synthetic.main.activity_login_with_phone.*
import kotlinx.android.synthetic.main.activity_o_t_p_verify.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class OTPVerifyActivity : BaseActivity() {
    lateinit var auth: FirebaseAuth
    var share: SharedPreferences?=null
    var editor: SharedPreferences.Editor ?=null

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_o_t_p_verify)

        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, android.R.color.transparent)
        window.setBackgroundDrawable(resources.getDrawable(R.drawable.toolbarbg))
        setupActionBar()

        auth=FirebaseAuth.getInstance()

        share = getSharedPreferences(
            "myPreference",
            Context.MODE_PRIVATE
        )

        editor= share!!.edit()

        val storedVerificationId=intent.getStringExtra("storedVerificationId")

        verifyBtn.setOnClickListener{
            var otp=id_otp.text.toString().trim()
            if(!otp.isEmpty()){
                showProgressDialog("Please Wait....")
                val credential : PhoneAuthCredential = PhoneAuthProvider.getCredential(
                    storedVerificationId.toString(), otp)
                signInWithPhoneAuthCredential(credential)
            }else{
                Toast.makeText(this,"Enter OTP", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupActionBar(){
        setSupportActionBar(toolbar_otp_verify)
        var actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_backward_icon)
            actionBar.title= "OTP Verify"
        }
        toolbar_otp_verify.setNavigationOnClickListener { onBackPressed() }
    }

    //OTP SMS Verify
    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        val phoneNo=intent.getStringExtra("phoneNo")
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    hideProgressDialog()
                    checkExistPhoneUser(phoneNo.toString())
                    startActivity(Intent(applicationContext, MainActivity::class.java))
                    finish()
                } else {
                    hideProgressDialog()
                        // Sign in failed, display a message and update the UI
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        // The verification code entered was invalid
                        Toast.makeText(this,"Invalid OTP",Toast.LENGTH_SHORT).show()
                    }
                }
            }
    }

    //Check Existing User
    private fun checkExistPhoneUser(phoneNo: String) {
        val destinationService = ServiceBuilder.buildService(OTPPhoneService::class.java)
        val requestCall = destinationService.getPhoneUserCount(phoneNo)
        requestCall.enqueue(object : Callback<Phone> {
            override fun onResponse(call: Call<Phone>, response: Response<Phone>) {
                if (response.isSuccessful) {
                    editor!!.putBoolean("isLogin", true)
                    editor!!.putString(Constants.USERID, response.body()!!.user_id)
                    editor!!.putInt(Constants.POSITION, response.body()!!.user_position)
                    editor!!.commit()
                    Toast.makeText(this@OTPVerifyActivity, "Already User", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
            override fun onFailure(call: Call<Phone>, t: Throwable) {
                createPhoneUser(phoneNo)
            }
        })
    }

    //Create User With Phone No
    private fun createPhoneUser(phoneNo: String) {
        val destinationService = ServiceBuilder.buildService(OTPPhoneService::class.java)
        var phone = Phone("",phoneNo, 0, 0)
        val requestCall = destinationService.createPhoneUser(phone)
        requestCall.enqueue(object : Callback<Phone>{
            override fun onResponse(call: Call<Phone>, response: Response<Phone>) {
                if (response.isSuccessful) {
                    editor!!.putBoolean("isLogin", true)
                    editor!!.putString(Constants.USERID, response.body()!!.user_id)
                    editor!!.putInt(Constants.POSITION, response.body()!!.user_position)
                    editor!!.commit()
                    finish()
                }
            }
            override fun onFailure(call: Call<Phone>, t: Throwable) {
                Toast.makeText(this@OTPVerifyActivity, "Something went wrong $t", Toast.LENGTH_SHORT)
            }
        })
    }
}