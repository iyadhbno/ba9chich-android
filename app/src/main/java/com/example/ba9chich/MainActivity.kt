package com.example.ba9chich

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.ArrayMap
import android.util.Log
import android.util.Patterns
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import com.example.ba9chich.models.User
import com.example.ba9chich.utils.ApiInterface
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

const val PREF_NAME = "DATA_CV_PREF"
const val emailfull = "email"
const val fullname = "fullname"
const val password = "password"
const val myuser = "myuser"
const val USER_ID = "USER_ID"
const val Facebookk = "FALSE"
const val IS_REMEMBRED = "remembred"
class MainActivity : AppCompatActivity() {

    lateinit var txtLogin: TextInputEditText
    lateinit var txtLayoutLogin: TextInputLayout

    lateinit var txtPassword: TextInputEditText
    lateinit var txtLayoutPassword: TextInputLayout

    lateinit var buttonSingUp: TextView
    lateinit var forgotpass: TextView

    lateinit var cbRememberMe: CheckBox

    lateinit var btnLogin: Button


    lateinit var mSharedPref: SharedPreferences
    lateinit var progBar: CircularProgressIndicator
    lateinit var mainIntent : Intent
    lateinit var obje : User
    var gson = Gson()







    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        forgotpass = findViewById(R.id.forgotPassword)
        buttonSingUp= findViewById(R.id.textViewSignUp)


        //printHashKey(applicationContext)     FB



        txtLogin = findViewById(R.id.txtEmail)
        txtLayoutLogin = findViewById(R.id.txtLayoutEmail)

        txtPassword = findViewById(R.id.txtAge)
        txtLayoutPassword = findViewById(R.id.txtLayoutAge)

        cbRememberMe = findViewById(R.id.cbRememberMe)
        btnLogin = findViewById(R.id.btnSubmit)

        progBar = findViewById(R.id.progBar)
        progBar.visibility = View.INVISIBLE
        mSharedPref = getSharedPreferences(PREF_NAME, MODE_PRIVATE);

        if (mSharedPref.getBoolean(IS_REMEMBRED, false)) {
            val mainIntent = Intent(this, HomeActivity::class.java)

            startActivity(mainIntent)
            finish()
        }
        buttonSingUp.setOnClickListener{
            val mainIntent = Intent(this, SignUp::class.java)


            startActivity(mainIntent)

        }
        forgotpass.setOnClickListener{
            val mainIntent = Intent(this, ForgetPassword::class.java)


            startActivity(mainIntent)

        }
        btnLogin.setOnClickListener{
            txtLayoutLogin!!.error = null
            txtLayoutPassword!!.error = null




            if (txtLogin?.text!!.isEmpty()) {
                txtLayoutLogin!!.error = "must not be empty"
                return@setOnClickListener
            }
            if (!isEmailValid(txtLogin?.text.toString())){
                txtLayoutLogin!!.error = "Check your email !"

                return@setOnClickListener
            }
            if (txtPassword?.text!!.isEmpty()) {
                txtLayoutPassword!!.error = "must not be empty"
                return@setOnClickListener
            }

            mSharedPref.edit().apply {
                putString(emailfull, txtLogin!!.text.toString())

                putString(password, txtPassword!!.text.toString())
                putBoolean(IS_REMEMBRED, cbRememberMe.isChecked)

            }.apply()
            if (cbRememberMe.isChecked){
                mSharedPref.edit().apply{
                    putBoolean(IS_REMEMBRED, cbRememberMe.isChecked)
                }.apply()
            }
            mainIntent = Intent(this, HomeActivity::class.java)
            doLogin()
        }

    }
    fun isEmailValid(email: String?): Boolean {
        return !(email == null || TextUtils.isEmpty(email)) && Patterns.EMAIL_ADDRESS.matcher(email)
            .matches()
    }
    private fun doLogin(){

        val apiInterface = ApiInterface.create()
        progBar.visibility = View.VISIBLE

        window.setFlags(
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
        )
        val jsonParams: MutableMap<String?, Any?> = ArrayMap()
//put something inside the map, could be null
//put something inside the map, could be null
        jsonParams["email"] = txtLogin!!.text.toString()
        jsonParams["password"] = txtPassword!!.text.toString()

        val body = RequestBody.create(
            "application/json; charset=utf-8".toMediaTypeOrNull(),
            JSONObject(jsonParams).toString()
        )

        apiInterface.seConnecter(body).enqueue(object : Callback<User> {

            override fun onResponse(call: Call<User>, response: Response<User>) {

                val user = response.body()



                if (user != null){
                    Toast.makeText(this@MainActivity, "Login Success", Toast.LENGTH_SHORT).show()


                    Log.d("user",user.toString())

                    val json = gson.toJson(user)
                    print("////////////////////////////////////////////////")
                    Log.d("json",json.toString())
                    mSharedPref.edit().apply {
                        putString(myuser, json)
                        putString(USER_ID,user.id)

                    }.apply()
                    startActivity(mainIntent)
                    finish()
                }else{
                    Toast.makeText(this@MainActivity, "User not found", Toast.LENGTH_SHORT).show()
                }

                progBar.visibility = View.INVISIBLE
                window.clearFlags( WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Toast.makeText(this@MainActivity, t.message, Toast.LENGTH_SHORT).show()

                progBar.visibility = View.INVISIBLE
                window.clearFlags( WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
            }

        })


    }


}