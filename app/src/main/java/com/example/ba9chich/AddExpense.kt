package com.example.ba9chich

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.ArrayMap
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.example.ba9chich.models.Expense
import com.example.ba9chich.models.User
import com.example.ba9chich.utils.ApiInterface
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddExpense : AppCompatActivity() {

    private lateinit var txtEdit: EditText
    private lateinit var txtEditSub: EditText
    lateinit var nowuser: User
    lateinit var btnADDQuestion: Button
    private lateinit var mSharedPref: SharedPreferences
    lateinit var amainIntent : Intent
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_expense)
        //toolbar
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        mSharedPref = getSharedPreferences(PREF_NAME, MODE_PRIVATE);


        toolbar.setNavigationOnClickListener {

            val  mainIntent = Intent(this, HomeActivity::class.java)

            startActivity(mainIntent)
            finish()
        }

        txtEdit = findViewById(R.id.Edittext)
        txtEditSub = findViewById(R.id.EdittextSubject)
        btnADDQuestion= findViewById(R.id.btnAddQuestion)

        amainIntent = Intent(this, HomeActivity::class.java)
        btnADDQuestion.setOnClickListener{
            if (txtEditSub?.text!!.isEmpty()) {
                Toast.makeText(this@AddExpense, "Subject must not be empty", Toast.LENGTH_SHORT).show()

                return@setOnClickListener
            }
            if (txtEdit?.text!!.isEmpty()) {
                Toast.makeText(this@AddExpense, "Description must not be empty", Toast.LENGTH_SHORT).show()

                return@setOnClickListener
            }
            doLogin()
        }
    }
    private fun doLogin(){
        val gson = Gson()
        val  us =  mSharedPref.getString(myuser, "")

        nowuser = gson.fromJson(us,User::class.java)
        val apiInterface = ApiInterface.create()


        window.setFlags(
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
        )
        val jsonParams: MutableMap<String?, Any?> = ArrayMap()
//put something inside the map, could be null
//put something inside the map, could be null
        jsonParams["price"] = txtEdit.text.toString() +" DT"
        jsonParams["userId"] = nowuser.id
        jsonParams["item"] = txtEditSub.text.toString()



        val body = RequestBody.create(
            "application/json; charset=utf-8".toMediaTypeOrNull(),
            JSONObject(jsonParams).toString()
        )

        apiInterface.createexpense(body).enqueue(object : Callback<Expense> {

            override fun onResponse(call: Call<Expense>, response: Response<Expense>) {

                val user = response.body()

                if (user != null){
                    Toast.makeText(this@AddExpense, "Expense Aded", Toast.LENGTH_SHORT).show()


                    startActivity(amainIntent)
                    finish()
                }else{
                    Toast.makeText(this@AddExpense, "can not Add Expense", Toast.LENGTH_SHORT).show()
                }


                window.clearFlags( WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
            }

            override fun onFailure(call: Call<Expense>, t: Throwable) {
                Toast.makeText(this@AddExpense, t.message, Toast.LENGTH_SHORT).show()


                window.clearFlags( WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
            }

        })


    }
}