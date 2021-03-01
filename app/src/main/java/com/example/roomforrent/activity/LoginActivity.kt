package com.example.roomforrent.activity

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.example.roomforrent.R
import kotlinx.android.synthetic.main.activity_house_list.*
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //For Change status bar color
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, android.R.color.transparent)
        window.setBackgroundDrawable(resources.getDrawable(R.drawable.toolbarbg))
        setUpActionBar()

        btn_sing_up.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
   //NangTintTintTun
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