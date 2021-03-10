package com.example.roomforrent.fragment

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.roomforrent.R
import com.example.roomforrent.activity.ChangePasswordActivity
import com.example.roomforrent.activity.MainActivity
import com.example.roomforrent.activity.PersonalInformationActivity
import kotlinx.android.synthetic.main.fragment_login_profile.view.*

class LoginProfileFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_login_profile, container, false)
        v.ll_owner_personal_info.setOnClickListener {
            startActivity(Intent(context, PersonalInformationActivity::class.java))
        }
        v.ll_owner_change_password.setOnClickListener {
            startActivity(Intent(context, ChangePasswordActivity::class.java))
        }
        //NTTT
        v.btn_owner_profile_logout.setOnClickListener {
            startActivity(Intent(context, MainActivity::class.java))
            val share: SharedPreferences = context?.getSharedPreferences("myPreference",
                Context.MODE_PRIVATE)!!
            val editor: SharedPreferences.Editor = share.edit()
            editor.putBoolean("isLogin", false)
            editor.commit()
        }
        return v
    }
}