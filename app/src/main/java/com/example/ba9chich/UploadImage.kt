package com.example.ba9chich

import android.Manifest
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.StrictMode
import android.util.ArrayMap
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.ba9chich.models.User
import com.example.ba9chich.models.fileutil
import com.example.ba9chich.utils.ApiInterface
import com.google.gson.Gson
import com.koushikdutta.ion.Ion
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.util.ArrayList

class UploadImage : AppCompatActivity() {
    lateinit var imgsel: Button
    lateinit var upload: Button
    lateinit var img: ImageView
    lateinit  var path: String
    lateinit var uri: Uri
    lateinit var nowuser: User
    private lateinit var mSharedPref: SharedPreferences
    var f: fileutil = fileutil()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload_image)

        mSharedPref = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        val toolbar: Toolbar = findViewById(R.id.toolbarback)
        setSupportActionBar(toolbar)


        toolbar.setNavigationOnClickListener {


            finish()
        }
        Ion.getDefault(this).configure().setLogging("ion-sample", Log.DEBUG);
        img = findViewById(R.id.img);
        imgsel = findViewById(R.id.selimg);
        upload =findViewById(R.id.uploadimg);
        upload.setVisibility(View.INVISIBLE);

        val gson = Gson()
        val  us =  mSharedPref.getString(myuser, "")

        nowuser = gson.fromJson(us,User::class.java)
        var imagee =""
        if( nowuser.imageUrl!=null){
            imagee = "uploads/"+ nowuser.imageUrl.subSequence(8,nowuser.imageUrl.length)

        }
        Glide.with(img).load(ApiInterface.BASE_URL + imagee).placeholder(R.drawable.me).circleCrop()
            .error(R.drawable.me).into(img)
        upload.setOnClickListener {
            doLogin()
        }


        imgsel.setOnClickListener {
            val fintent = Intent(Intent.ACTION_GET_CONTENT)
            fintent.type = "image/jpeg"
            try {
                startActivityForResult(fintent, 100)
            } catch (e: ActivityNotFoundException) {
            }
        }

    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data == null) return
        when (requestCode) {
            100 -> if (resultCode == RESULT_OK) {
                uri = data.data!!
                img.setImageURI(data.data)
                upload.visibility = View.VISIBLE
            }
        }
    }
    private val apppermissions = arrayOf<String>(

        Manifest.permission.INTERNET,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE
    )

    private fun checkAndRequestPermission(): Boolean {
        val builder = StrictMode.VmPolicy.Builder()
        StrictMode.setVmPolicy(builder.build())
        val listPermissionsNeeded: MutableList<String> = ArrayList()
        for (perm in apppermissions) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    perm
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                listPermissionsNeeded.add(perm)
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(
                this, listPermissionsNeeded.toTypedArray(),
                200
            )
            return false
        }
        return true
    }

    private fun doLogin(){
        checkAndRequestPermission()
        val apiInterface = ApiInterface.create()

        val file = File(f.getPath(uri,this))
        val gson = Gson()
        val  us =  mSharedPref.getString(myuser, "")

        nowuser = gson.fromJson(us,User::class.java)



        val  reqFile = RequestBody.create("image/*".toMediaTypeOrNull(), file)
        val image = MultipartBody.Part.createFormData("Image",
            file.getName(), reqFile)

        apiInterface.upload(image,nowuser.id).enqueue(object : Callback<User> {

            override fun onResponse(call: Call<User>, response: retrofit2.Response<User>) {

                val user = response.body()

                if (user != null){
                    Toast.makeText(this@UploadImage, "Image Updated", Toast.LENGTH_SHORT).show()
                    Log.d("user",user.toString())
                    nowuser.imageUrl = user.imageUrl
                    val json = gson.toJson(nowuser)
                    print("////////////////////////////////////////////////")
                    Log.d("json",json.toString())
                    mSharedPref.edit().apply {
                        putString(myuser, json)

                    }.apply()

                }else{
                    Toast.makeText(this@UploadImage, "can not Update Image", Toast.LENGTH_SHORT).show()
                }


                window.clearFlags( WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Toast.makeText(this@UploadImage, t.message, Toast.LENGTH_SHORT).show()


                window.clearFlags( WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
            }

        })


    }
    private fun doLoginup(){

        val apiInterface = ApiInterface.create()


        window.setFlags(
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
        )
        val jsonParams: MutableMap<String?, Any?> = ArrayMap()
//put something inside the map, could be null
//put something inside the map, could be null
        jsonParams["email"] = nowuser.email
        jsonParams["nom"] = nowuser.nom
        jsonParams["phone"] = nowuser.phone
        jsonParams["imageUrl"] = nowuser.imageUrl


        val body = RequestBody.create(
            "application/json; charset=utf-8".toMediaTypeOrNull(),
            JSONObject(jsonParams).toString()
        )

        apiInterface.updateusernotpass(body,nowuser.id).enqueue(object : Callback<User> {

            override fun onResponse(call: Call<User>, response: Response<User>) {

                val user = response.body()

                if (user != null){
                    Toast.makeText(this@UploadImage, "User Updated", Toast.LENGTH_SHORT).show()

                }else{
                    Toast.makeText(this@UploadImage, "User can not Update", Toast.LENGTH_SHORT).show()
                }


                window.clearFlags( WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Toast.makeText(this@UploadImage, t.message, Toast.LENGTH_SHORT).show()


                window.clearFlags( WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
            }

        })


    }
}