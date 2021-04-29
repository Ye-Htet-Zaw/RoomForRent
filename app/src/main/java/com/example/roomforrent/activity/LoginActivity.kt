package com.example.roomforrent.activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.roomforrent.R
import com.example.roomforrent.fragment.LoginProfileFragment
import com.example.roomforrent.models.UserLogin
import com.example.roomforrent.services.ServiceBuilder
import com.example.roomforrent.services.UserLoginService
import com.example.roomforrent.utils.Constants.USERID
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class LoginActivity : BaseActivity() {
    lateinit var callGetUser: Call<UserLogin>
    lateinit var callbackManager: CallbackManager
    private var EMAIL="email"
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        login_button.setOnClickListener(View.OnClickListener {
            //login_button.setReadPermissions("public_profile", "email")
            // Login
            callbackManager = CallbackManager.Factory.create()
           LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "email"))
            LoginManager.getInstance().registerCallback(callbackManager,
                object : FacebookCallback<LoginResult> {
                    override fun onSuccess(loginResult: LoginResult) {
                        Log.d("MainActivity", "Facebook token: " + loginResult.accessToken.token)
                        startActivity(Intent(applicationContext, MainActivity::class.java))

                    }

                    override fun onCancel() {
                        Log.d("MainActivity", "Facebook onCancel.")

                    }

                    override fun onError(error: FacebookException) {
                        Log.d("MainActivity", "Facebook onError. ${error.message}")

                    }
                })
        })

        btn_phone_otp.setOnClickListener {
            var intent = Intent(
                this,
                LoginWithPhoneActivity::class.java
            )
            startActivity(intent)
        }

        val share:SharedPreferences  = getSharedPreferences("myPreference",
            Context.MODE_PRIVATE)
        //For Change status bar color
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, android.R.color.transparent)
        window.setBackgroundDrawable(resources.getDrawable(R.drawable.toolbarbg))
        setUpActionBar()

        //SignInForUser
        btn_sing_up.setOnClickListener() {
            showProgressDialog("Please wait...")
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
                        hideProgressDialog()
                        Toast.makeText(this@LoginActivity,"Something Wrong",Toast.LENGTH_LONG).show()
                        Log.e(t.message, "ERROR")

                    }

                    override fun onResponse(call: Call<UserLogin>, response: Response<UserLogin>) {
                        if (response.isSuccessful) {
                            hideProgressDialog()
                            var fragment=LoginProfileFragment()
                            val b = Bundle()
                            b.putString(USERID,response.body()!!.user_id)


                            Log.i("Response", "Login UserId at LoginActivity: "+response.body()!!.user_id)
                            fragment.setArguments(b)
                            supportFragmentManager.beginTransaction()
                                .add(android.R.id.content, fragment).commit()
                            //val intent=Intent(this@LoginActivity,MainActivity::class.java)
                            //intent.putExtra(MainActivity.USERID,response.body()!!.user_id)
                            //startActivity(intent)
                            val editor: SharedPreferences.Editor = share.edit()
                            editor.putBoolean("isLogin", true)
                            editor.putString(USERID,response.body()!!.user_id)
                            editor.commit()
                            Toast.makeText(this@LoginActivity,"LOGIN SUCCESSFULLY",Toast.LENGTH_LONG).show()

                        }

                        //when success use shared preferences
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        callbackManager?.onActivityResult(requestCode, resultCode, data)
    }
}