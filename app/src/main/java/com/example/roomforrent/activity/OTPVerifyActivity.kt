package com.example.roomforrent.activity

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.example.roomforrent.R
import com.example.roomforrent.models.Phone
import com.example.roomforrent.services.OTPPhoneService
import com.example.roomforrent.services.ServiceBuilder
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

class OTPVerifyActivity : AppCompatActivity() {
    lateinit var auth: FirebaseAuth

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_o_t_p_verify)

        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, android.R.color.transparent)
        window.setBackgroundDrawable(resources.getDrawable(R.drawable.toolbarbg))
        setupActionBar()

        auth=FirebaseAuth.getInstance()

        val storedVerificationId=intent.getStringExtra("storedVerificationId")

        verifyBtn.setOnClickListener{
            var otp=id_otp.text.toString().trim()
            if(!otp.isEmpty()){
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

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        val phoneNo=intent.getStringExtra("phoneNo")
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    checkExistPhoneUser(phoneNo.toString())
                    startActivity(Intent(applicationContext, MainActivity::class.java))
                    finish()
                } else {
                        // Sign in failed, display a message and update the UI
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        // The verification code entered was invalid
                        Toast.makeText(this,"Invalid OTP",Toast.LENGTH_SHORT).show()
                    }
                }
            }
    }

    private fun checkExistPhoneUser(phoneNo: String) {
        val destinationService = ServiceBuilder.buildService(OTPPhoneService::class.java)
        val requestCall = destinationService.getPhoneUserCount(phoneNo)
        requestCall.enqueue(object : Callback<List<Phone>> {
            override fun onResponse(call: Call<List<Phone>>, response: Response<List<Phone>>) {
                if (response.body()!!.count() > 0) {
                    Toast.makeText(this@OTPVerifyActivity, "Already User", Toast.LENGTH_SHORT).show()
                } else {
                    createPhoneUser(phoneNo)
                }
            }

            override fun onFailure(call: Call<List<Phone>>, t: Throwable) {
                Toast.makeText(
                    this@OTPVerifyActivity,
                    "Something went wrong $t",
                    Toast.LENGTH_SHORT
                )
            }
        })
    }

    private fun createPhoneUser(phoneNo: String) {
        val destinationService = ServiceBuilder.buildService(OTPPhoneService::class.java)
        var phone = Phone(phoneNo, "0", "0")
        val requestCall = destinationService.createPhoneUser(phone)

        requestCall.enqueue(object : Callback<Phone>{
            override fun onResponse(call: Call<Phone>, response: Response<Phone>) {
                Toast.makeText(this@OTPVerifyActivity, "Success", Toast.LENGTH_SHORT)
            }

            override fun onFailure(call: Call<Phone>, t: Throwable) {
                Toast.makeText(this@OTPVerifyActivity, "Something went wrong $t", Toast.LENGTH_SHORT)
            }
        })
    }
}