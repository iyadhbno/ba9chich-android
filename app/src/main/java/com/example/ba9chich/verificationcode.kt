package com.example.ba9chich

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.ArrayMap
import android.util.Log
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import com.bumptech.glide.Glide
import com.example.ba9chich.models.User
import com.example.ba9chich.utils.ApiInterface
import com.google.android.material.imageview.ShapeableImageView
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import org.json.JSONObject

class verificationcode : AppCompatActivity() {
    lateinit var btnLogin: Button
    private lateinit var txtFullName: TextView
    private lateinit var mSharedPref: SharedPreferences
    lateinit var nowuser: User
    lateinit var mainIntent : Intent
    private lateinit var imageme: ShapeableImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verificationcode)

        imageme = findViewById(R.id.idUrlImg)
        //toolbar
        val toolbar: Toolbar = findViewById(R.id.toolbarback)
        setSupportActionBar(toolbar)


        toolbar.setNavigationOnClickListener {

            mainIntent = Intent(this, ForgetPassword::class.java)
            startActivity(mainIntent)
            finish()
        }
        txtFullName = findViewById(R.id.idfullname)
        mSharedPref = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        val gson = Gson()
        val  us =  mSharedPref.getString(myuser, "")

        nowuser = gson.fromJson(us, User::class.java)
        txtFullName.text = nowuser.nom
        var imagee =""
        if( nowuser.imageUrl!=null){
            imagee = "uploads/"+ nowuser.imageUrl.subSequence(8,nowuser.imageUrl.length)

        }
        Glide.with(imageme).load(ApiInterface.BASE_URL + imagee).placeholder(R.drawable.ic_account).circleCrop()
            .error(R.drawable.ic_baseline_account_circle_24).into(imageme)
        btnLogin = findViewById(R.id.findAccount)
        btnLogin.setOnClickListener{
            mainIntent = Intent(this, accountfound::class.java)


            doLogin()
        }
    }

    fun rand(start: Int, end: Int): Int {
        require(start <= end) { "Illegal Argument" }
        return (start..end).random()
    }
    private fun doLogin(){

        val apiInterface = ApiInterface.create()


        window.setFlags(
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
        )
        val gson = Gson()
        val  us =  mSharedPref.getString(myuser, "")

        val random = rand(1000,9999)
        Log.d("random",random.toString())
        nowuser = gson.fromJson(us, User::class.java)
        val jsonParams: MutableMap<String?, Any?> = ArrayMap()
//put something inside the map, could be null
//put something inside the map, could be null
        jsonParams["email"] = nowuser.email
        jsonParams["code"] = random.toString()

        val body = RequestBody.create(
            "application/json; charset=utf-8".toMediaTypeOrNull(),
            JSONObject(jsonParams).toString()
        )



        Log.d("code",random.toString())
        mSharedPref.edit().apply{
            putString("code", random.toString())
        }.apply()

        startActivity(mainIntent)
        finish()





    }

}