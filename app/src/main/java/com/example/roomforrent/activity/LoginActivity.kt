package com.example.roomforrent.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.roomforrent.R
import com.example.roomforrent.models.UserLogin
import com.example.roomforrent.services.ServiceBuilder
import com.example.roomforrent.services.UserLoginService
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.log

class LoginActivity : AppCompatActivity() {
    lateinit var callGetUser: Call<UserLogin>

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val share:SharedPreferences  = getSharedPreferences("myPreference",
            Context.MODE_PRIVATE)
        //For Change status bar color
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, android.R.color.transparent)
        window.setBackgroundDrawable(resources.getDrawable(R.drawable.toolbarbg))
        setUpActionBar()

    //SignInForUser
        btn_sing_up.setOnClickListener() {
            if (et_email.text.toString().trim().isEmpty()) {
                et_email.error = "Email Required"
                et_email.requestFocus()
                return@setOnClickListener
            }
            else if (et_password.text.toString().trim().isEmpty()) {
                et_password.error = "Password Required"
                et_password.requestFocus()
                return@setOnClickListener
            }else
            {
                val destinationService = ServiceBuilder.buildService(UserLoginService::class.java)
                callGetUser = destinationService.getUserWithEmailAndPassword(et_email.text.toString().trim(),et_password.text.toString().trim())
                callGetUser.enqueue(object :Callback<UserLogin>{
                    override fun onFailure(call: Call<UserLogin>, t: Throwable) {
                        Toast.makeText(this@LoginActivity,"Your Email and Password Incorrect",Toast.LENGTH_LONG).show()
                        Log.e(t.message, "ERROR")

                    }

                    override fun onResponse(call: Call<UserLogin>, response: Response<UserLogin>) {
                        if (response.isSuccessful) {
                        val intent=Intent(this@LoginActivity,MainActivity::class.java)
                        intent.putExtra("UserId",response.body()!!.user_id)
                        startActivity(intent)}
                        //when success use shared preferences
                        val editor: SharedPreferences.Editor = share.edit()
                        editor.putBoolean("isLogin", true)
                        editor.commit()
                        Toast.makeText(this@LoginActivity,"LOGIN SUCCESSFULLY",Toast.LENGTH_LONG).show()

                    }

                }

                )

        }
        }
    }

    private fun setUpActionBar() {
        setSupportActionBar(toolBarLogin)
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back)
            actionBar.title = "Login"
        }
        toolBarLogin.setNavigationOnClickListener { onBackPressed() }
    }
}