package com.example.roomforrent.activity

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.example.roomforrent.R
import com.example.roomforrent.fragment.LoginProfileFragment
import com.facebook.appevents.codeless.internal.ViewHierarchy.setOnClickListener
import com.facebook.login.Login
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.android.synthetic.main.activity_house_detail.*
import kotlinx.android.synthetic.main.activity_login_with_phone.*
import java.util.concurrent.TimeUnit

class LoginWithPhoneActivity : BaseActivity() {
    lateinit var auth: FirebaseAuth
    lateinit var storedVerificationId:String
    lateinit var resendToken: PhoneAuthProvider.ForceResendingToken
    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_with_phone)

        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, android.R.color.transparent)
        window.setBackgroundDrawable(resources.getDrawable(R.drawable.toolbarbg))
        setupActionBar()

        auth=FirebaseAuth.getInstance()

        var currentUser = auth.currentUser
        if(currentUser != null) {
            startActivity(Intent(applicationContext, LoginProfileFragment::class.java))
            finish()
        }

        loginBtn.setOnClickListener{
            login()
        }

        // Callback function for Phone Auth
        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                startActivity(Intent(applicationContext, LoginProfileFragment::class.java))
                finish()
            }

            override fun onVerificationFailed(e: FirebaseException) {
                hideProgressDialog()
                Toast.makeText(applicationContext, "Failed", Toast.LENGTH_LONG).show()
            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {

                Log.d("TAG","onCodeSent:$verificationId")
                storedVerificationId = verificationId
                var number="+95"+phoneNumber.text.toString().trim()
                resendToken = token

                var intent = Intent(applicationContext,OTPVerifyActivity::class.java)
                intent.putExtra("storedVerificationId",storedVerificationId)
                intent.putExtra("phoneNo",number)
                startActivity(intent)
                hideProgressDialog()
            }
        }
    }

    private fun setupActionBar(){
        setSupportActionBar(toolbar_login_with_phone)
        var actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_backward_icon)
            actionBar.title= "Login With Phone"
        }

        toolbar_login_with_phone.setNavigationOnClickListener { onBackPressed() }
    }

    private fun login() {
        val mobileNumber=findViewById<EditText>(R.id.phoneNumber)
        var number=mobileNumber.text.toString().trim()

        if(!number.isEmpty()){
            showProgressDialog("Please Wait....")
            number="+95"+number
            sendVerificationcode (number)
        }else{
            Toast.makeText(this,"Enter mobile number", Toast.LENGTH_SHORT).show()
        }
    }

    private fun sendVerificationcode(number: String) {
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(number) // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(this) // Activity (for callback binding)
            .setCallbacks(callbacks) // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }
}