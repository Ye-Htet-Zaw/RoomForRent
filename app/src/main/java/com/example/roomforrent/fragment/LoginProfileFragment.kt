package com.example.roomforrent.fragment

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.example.roomforrent.R
import com.example.roomforrent.activity.*
import com.example.roomforrent.models.User
import com.example.roomforrent.services.ServiceBuilder
import com.example.roomforrent.services.UserProfileService
import com.example.roomforrent.utils.Constants.POSITION
import com.example.roomforrent.utils.Constants.USERID
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_login_profile.*
import kotlinx.android.synthetic.main.fragment_login_profile.view.*
import kotlinx.android.synthetic.main.fragment_login_profile.view.btn_sign_in_profile
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginProfileFragment : BaseFragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var isLogin:Boolean = false
    private var userId: String? = null
    private var position: Int? = null
    private var share : SharedPreferences? = null

    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                startActivity(Intent(context, MainActivity::class.java))

            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        share = context?.getSharedPreferences(
            "myPreference",
            Context.MODE_PRIVATE
        )!!
        position=share!!.getInt(POSITION,3)
        isLogin = share!!.getBoolean("isLogin",false)
        userId= share!!.getString(USERID,"")
        val v = inflater.inflate(R.layout.fragment_login_profile, container, false)
        if(isLogin){
            v.ll_loginedProfile.visibility = View.VISIBLE
            v.ll_unloginProfile.visibility = View.GONE
            if(position==1){
                v.ll_owner_list_space.visibility=View.VISIBLE
                v.ll_owner_list_space_line.visibility=View.VISIBLE
                v.ll_owner_change_password.visibility = View.VISIBLE
                v.ll_owner_change_password_line.visibility = View.VISIBLE
            } else{
                v.ll_owner_list_space.visibility=View.GONE
                v.ll_owner_list_space_line.visibility=View.GONE
                v.ll_owner_change_password.visibility = View.GONE
                v.ll_owner_change_password_line.visibility = View.GONE
            }
        }else{
            v.ll_loginedProfile.visibility = View.GONE
            v.ll_unloginProfile.visibility = View.VISIBLE
        }
        v.btn_sign_in_profile.setOnClickListener {
            startActivity(Intent(context, LoginActivity::class.java))
        }
        v.ll_owner_personal_info.setOnClickListener {
            if(checkConnection()){
                val i = Intent(context, PersonalInformationActivity::class.java)
                i.putExtra(USERID, userId)
                startActivity(i)
            }
        }

        v.ll_owner_change_password.setOnClickListener {
            val i = Intent(activity, ChangePasswordActivity::class.java)
            i.putExtra(USERID, userId)
            startActivity(i)
            (activity as Activity?)!!.overridePendingTransition(0, 0)
        }


        v.tv_owner_list_space.setOnClickListener{
            if(checkConnection()){
                val i = Intent(context, ListYourSpaceActivity::class.java)
                i.putExtra(USERID, userId)
                startActivity(i)
                Log.i("Response", "Login UserId at LoginProfileFragment :"+ userId)
            }
        }

        auth= FirebaseAuth.getInstance()

        v.btn_owner_profile_logout.setOnClickListener {
            showProgressDialog("Please Wait....")
            auth.signOut()
            startActivity(Intent(context, MainActivity::class.java))
            val share: SharedPreferences = context?.getSharedPreferences(
                "myPreference",
                Context.MODE_PRIVATE
            )!!
            val editor: SharedPreferences.Editor = share.edit()
            editor.putBoolean("isLogin", false)
            editor.commit()
            hideProgressDialog()
        }
        getUserInfoById(userId!!.toString())//changes for numberformat
        return v
        //FragmentManager.OnBackStackChangedListener {  }
    }


    //get user info from api
    private fun getUserInfoById(user_id: String) {
        //initiate the service
        val destinationService  = ServiceBuilder.buildService(UserProfileService::class.java)
        val requestCall =destinationService.getUserInfo(user_id.toString())
        //make network call asynchronously
        requestCall.enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                Log.d("Response", "onResponse: ${response.body()}")
                if (response.isSuccessful) {
                    val user = response.body()!!                  
                    if(user.user_name == null){
                        tv_owner_name.text = "Renter"
                    } else {
                        tv_owner_name.text = user.user_name
                    }


                    Picasso.get()
                        .load("http://192.168.1.15:9090/image/user/" + user.user_id + ".jpg").into(
                            iv_profile_user_image
                        )

                } else {
                    Log.i("Something", "Something went wrong ${response.message()}")
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Log.i("errorM", t.message.toString())
            }
        })
    }

    override fun onResume() {
        super.onResume()
        isLogin = share!!.getBoolean("isLogin",false)
        userId= share!!.getString(USERID,"")
        if(isLogin){
            ll_loginedProfile.visibility = View.VISIBLE
            ll_unloginProfile.visibility = View.GONE
            if(position==1){
                ll_owner_list_space.visibility=View.VISIBLE
                ll_owner_list_space_line.visibility=View.VISIBLE
                ll_owner_change_password.visibility = View.VISIBLE
                ll_owner_change_password_line.visibility = View.VISIBLE
            } else{
                ll_owner_list_space.visibility=View.GONE
                ll_owner_list_space_line.visibility=View.GONE
                ll_owner_change_password.visibility = View.GONE
                ll_owner_change_password_line.visibility = View.GONE
            }
        }else{
            ll_loginedProfile.visibility = View.GONE
            ll_unloginProfile.visibility = View.VISIBLE
        }
        getUserInfoById(userId!!.toString())
    }
}
