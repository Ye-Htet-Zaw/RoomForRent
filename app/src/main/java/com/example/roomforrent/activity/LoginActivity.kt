/**
 *
 * LoginActivity
 *
 * 2021/03/8 NTTT Create New
 *
 * UserLogin
 */
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
import androidx.core.content.ContextCompat
import com.example.roomforrent.R
import com.example.roomforrent.models.UserLogin
import com.example.roomforrent.services.FacebookLoginService
import com.example.roomforrent.services.ServiceBuilder
import com.example.roomforrent.services.UserLoginService
import com.example.roomforrent.utils.Constants.POSITION
import com.example.roomforrent.utils.Constants.USERID
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginBehavior
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_login.*
import okhttp3.internal.platform.Platform
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*


class LoginActivity : BaseActivity() {
    private lateinit var auth: FirebaseAuth
    var callbackManager: CallbackManager?=null
    lateinit var callGetUser: Call<UserLogin>
    lateinit var callCreateAccount: Call<List<UserLogin>>
    lateinit var callfbCount:Call<Int>
    lateinit var callFbForUserId:Call<UserLogin>
    var share: SharedPreferences?=null
    var editor: SharedPreferences.Editor ?=null
    @ExperimentalStdlibApi
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        auth = FirebaseAuth.getInstance()
        callbackManager = CallbackManager.Factory.create()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        share = getSharedPreferences(
            "myPreference",
            Context.MODE_PRIVATE
        )
        editor= share!!.edit()
        //For Change status bar color
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, android.R.color.transparent)
        window.setBackgroundDrawable(resources.getDrawable(R.drawable.toolbarbg))
        setUpActionBar()

        //SignInForOwnerUser
        btn_sing_up.setOnClickListener() {
            showProgressDialog("Please wait...")
            if (et_email.text.toString().trim().isEmpty()) {
                hideProgressDialog()
                et_email.error = "Email Required"
                et_email.requestFocus()
                return@setOnClickListener
            }
            else if (et_password.text.toString().trim().isEmpty()) {
                hideProgressDialog()
                et_password.error = "Password Required"
                et_password.requestFocus()
                return@setOnClickListener
            }else
            {
                val destinationService = ServiceBuilder.buildService(UserLoginService::class.java)
                callGetUser = destinationService.getUserWithEmailAndPassword(
                    et_email.text.toString().trim(), et_password.text.toString().trim()
                )
                callGetUser.enqueue(object : Callback<UserLogin> {
                    override fun onFailure(call: Call<UserLogin>, t: Throwable) {
                        hideProgressDialog()
                        Toast.makeText(this@LoginActivity, "Something Wrong", Toast.LENGTH_LONG)
                            .show()
                        Log.e(t.message, "ERROR")

                    }

                    override fun onResponse(call: Call<UserLogin>, response: Response<UserLogin>) {
                        if (response.isSuccessful) {
                            hideProgressDialog()
                            editor!!.putBoolean("isLogin", true)
                            editor!!.putString(USERID, response.body()!!.user_id)
                            editor!!.putInt(POSITION, response.body()!!.user_position)
                            editor!!.commit()
                            Toast.makeText(
                                this@LoginActivity,
                                "LOGIN SUCCESSFULLY",
                                Toast.LENGTH_LONG
                            ).show()
                            finish()

                        }
                    }

                }

                )

            }

        }
        // Initialize Facebook Login button
        //SignInForRenderUserWithFacebook
        callbackManager = CallbackManager.Factory.create()
        login_button.setReadPermissions("email", "public_profile")
        login_button.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {
                Log.d("TAG", "facebook:onSuccess:$loginResult")
                handleFacebookAccessToken(loginResult.accessToken)

            }

            override fun onCancel() {
                Log.d("TAG", "facebook:onCancel")
            }

            override fun onError(error: FacebookException) {
                Log.d("TAG", "facebook:onError", error)
            }
        })

        //SignUp With Phone
        btn_phone_otp.setOnClickListener {
            var intent = Intent(
                this,
                LoginWithPhoneActivity::class.java
            )
            startActivity(intent)
        }

    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }
    private fun handleFacebookAccessToken(token: AccessToken) {
        Log.d("TAG", "handleFacebookAccessToken:$token")

        val credential = FacebookAuthProvider.getCredential(token.token)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val facebookService = ServiceBuilder.buildService(FacebookLoginService::class.java)
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("TAG", "signInWithCredential:success")
                    val user = auth.currentUser
                    updateUI(user)
                    val user_email=user.email
                    val fb_id=user.uid
                    val user_name=user.displayName
                    var date = SimpleDateFormat(
                        "yyyy-MM-dd",
                        Locale.getDefault()
                    ).format(Date())
                    val account=UserLogin(
                        user_id="USE",
                        user_name=user_name,
                        user_email= user_email,
                        facebook_id= fb_id,
                        password="",
                        phone_one="",
                        phone_two= "",
                        user_address ="",
                        user_gender=0,
                        user_dob =date,
                        user_position=0,
                        delete_flag= 0,
                        delete_datetime=date,
                        creator_id="USE",
                        create_datetime=date,
                        updator_id="",
                        update_datetime= date
                    )
                    callfbCount=facebookService.getFacebookId(fb_id)
                    callFbForUserId=facebookService.getUserId(fb_id)
                    Log.i("TAG", callfbCount.toString())

                    callfbCount.enqueue(object : Callback<Int> {

                        override fun onResponse(call: Call<Int>, response: Response<Int>) {
                            Log.i("TAG", "$user_email&$user_name&$fb_id")
                            Log.i("TAG", response.body().toString()+"COUNT SUCESS")
                            if(response.body()==0){
                                //Register Fb User in database
                                callCreateAccount=facebookService.createUser(account)
                                getAccountList()

                            }
                            else{
                                getUserId()

                            }
                        }

                        override fun onFailure(call: Call<Int>, t: Throwable) {
                            Log.i("TAG", "COUNT fail")
                        }


                    })

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("TAG", "signInWithCredential:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                    updateUI(null)
                }
            }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        callbackManager?.onActivityResult(requestCode, resultCode, data)

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
    private fun updateUI(user: FirebaseUser?) {

    }

    //CallApiForFacebookUserRegister
    private fun getAccountList() {
        callCreateAccount.enqueue(object : Callback<List<UserLogin>> {
            override fun onResponse(call: Call<List<UserLogin>>, response: Response<List<UserLogin>>) {
                Log.i("TAG", "Retrieve" +response.body()!!.size.toString())
                getUserId()
            }

            override fun onFailure(call: Call<List<UserLogin>>, t: Throwable) {

            }

        })
    }

    //Call Api for callFbForUserId
    private fun getUserId(){
        callFbForUserId.enqueue(object : Callback<UserLogin> {
            override fun onResponse(
                call: Call<UserLogin>,
                response: Response<UserLogin>
            ) {
                Log.d("TAG", "USERIDSUCESSFUL:"+response.body()!!.user_id)

                editor!!.putBoolean("isLogin", true)
                editor!!.putString(USERID, response.body()!!.user_id)
                editor!!.putInt(POSITION, response.body()!!.user_position)
                editor!!.commit()
                finish()

            }

            override fun onFailure(call: Call<UserLogin>, t: Throwable) {
                Log.d("TAG", "USERID FAIL")

            }

        })
    }
}