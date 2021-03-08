package com.example.roomforrent.activity

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.example.roomforrent.R
import com.example.roomforrent.models.UserLogin
import com.example.roomforrent.services.ServiceBuilder
import com.example.roomforrent.services.UserLoginService
import kotlinx.android.synthetic.main.activity_house_list.*
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    lateinit var callGetUser: Call<UserLogin>
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val destinationService = ServiceBuilder.buildService(UserLoginService::class.java)
        //For Change status bar color
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, android.R.color.transparent)
        window.setBackgroundDrawable(resources.getDrawable(R.drawable.toolbarbg))
        setUpActionBar()

        btn_sing_up.setOnClickListener {
            //startActivity(Intent(this, MainActivity::class.java))

            val email = et_email.text.toString().trim()
            val password = et_password.text.toString().trim()
            if (email.isEmpty()) {
                et_email.error = "Email Required"
                et_email.requestFocus()
                return@setOnClickListener
            }
            else if (password.isEmpty()) {
                et_password.error = "Password Required"
                et_password.requestFocus()
                return@setOnClickListener
            }else
            {
                callGetUser=destinationService.getUserWithEmailAndPassword(email,password)
                callGetUser.enqueue(object : Callback<UserLogin> {
                    override fun onResponse(call: Call<UserLogin>, response: Response<UserLogin>) {
                        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                    }
                    override fun onFailure(call: Call<UserLogin>, t: Throwable) {
                        Log.i("TestingApi", "LOGIN FAIL")
                    }


                })

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