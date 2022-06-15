package com.example.ba9chich

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.ArrayMap
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.widget.Toolbar
import com.example.ba9chich.models.Transaction
import com.example.ba9chich.models.User
import com.example.ba9chich.utils.ApiInterface
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddTransaction : AppCompatActivity() {

    private lateinit var txtEditSub: EditText
    private lateinit var txtEditSubto: AutoCompleteTextView
    var UserList : MutableList<User> = ArrayList()
    var UserListemail : MutableList<String> = ArrayList()
    lateinit var btnADDQuestion: Button
    lateinit var nowuser: User
    private lateinit var mSharedPref: SharedPreferences
    lateinit var amainIntent : Intent
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_transaction)
        //toolbar
        alluser()
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        mSharedPref = getSharedPreferences(PREF_NAME, MODE_PRIVATE);


        toolbar.setNavigationOnClickListener {


            finish()
        }

        txtEditSub = findViewById(R.id.EdittextSubject)
        txtEditSubto = findViewById(R.id.Edittextto)
        btnADDQuestion= findViewById(R.id.btnAddQuestion)

        amainIntent = Intent(this, HomeActivity::class.java)

        val adapter = ArrayAdapter(this,
            android.R.layout.simple_list_item_1, UserListemail)
        txtEditSubto.setAdapter(adapter)
        btnADDQuestion.setOnClickListener{

            if (txtEditSubto?.text!!.isEmpty()) {
                Toast.makeText(this@AddTransaction, "To must not be empty", Toast.LENGTH_SHORT).show()

                return@setOnClickListener
            }
            if (txtEditSub?.text!!.isEmpty()) {
                Toast.makeText(this@AddTransaction, "Amount must not be empty", Toast.LENGTH_SHORT).show()

                return@setOnClickListener
            }

            doLogin()
        }
    }
    private fun alluser(){

        val apiInterface = ApiInterface.create()



        apiInterface.allusers().enqueue(object : Callback<MutableList<User>> {

            override fun onResponse(call: Call<MutableList<User>>, response: Response<MutableList<User>>) {

                val user = response.body()

                if (user != null) {

                    UserList = user

                    for(a in UserList){
                        UserListemail.add(a.email)
                    }

                }



            }

            override fun onFailure(call: Call<MutableList<User>>, t: Throwable) {

            }

        })


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

        jsonParams["amount"] = txtEditSub.text.toString()+" DT"

        jsonParams["fromAdress"] = nowuser.email
        jsonParams["toAdress"] = txtEditSubto.text.toString()


        val body = RequestBody.create(
            "application/json; charset=utf-8".toMediaTypeOrNull(),
            JSONObject(jsonParams).toString()
        )

        apiInterface.createTransaction(body).enqueue(object : Callback<Transaction> {

            override fun onResponse(call: Call<Transaction>, response: Response<Transaction>) {

                val user = response.body()

                if (user != null){
                    Toast.makeText(this@AddTransaction, "Transaction Aded", Toast.LENGTH_SHORT).show()



                    finish()
                }else{
                    Toast.makeText(this@AddTransaction, "can not Add Transaction", Toast.LENGTH_SHORT).show()
                }


                window.clearFlags( WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
            }

            override fun onFailure(call: Call<Transaction>, t: Throwable) {
                Toast.makeText(this@AddTransaction, t.message, Toast.LENGTH_SHORT).show()


                window.clearFlags( WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
            }

        })


    }
}