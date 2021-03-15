package com.example.roomforrent.fragment

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.roomforrent.R
import com.example.roomforrent.activity.ChangePasswordActivity
import com.example.roomforrent.activity.MainActivity
import com.example.roomforrent.activity.PersonalInformationActivity
import com.example.roomforrent.models.User
import com.example.roomforrent.services.ServiceBuilder
import com.example.roomforrent.services.UserProfileService
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_personal_information.*
import kotlinx.android.synthetic.main.fragment_login_profile.*
import kotlinx.android.synthetic.main.fragment_login_profile.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

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
        getUserInfoById("USE0000001")
        return v
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
                    //Toast.makeText(this@LoginProfileFragment,"user name is ${user.user_name}",Toast.LENGTH_SHORT).show()
                   Log.i("user_name",user.user_name)

                    tv_owner_name.text=user.user_name
                    Picasso.get().load("http://192.168.99.129:9090/image/user/"+user.user_id+".jpg").into(iv_profile_user_image)

                } else {
                    Log.i("Something", "Something went wrong ${response.message()}")
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Log.i("errorM", t.message.toString())
            }
        })
    }
}