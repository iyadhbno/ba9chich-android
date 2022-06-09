package tn.edu.esprit.ba9chich

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class accountFound : AppCompatActivity() {
    lateinit var btnLogin: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_found)
        btnLogin = findViewById(R.id.findAccount)


        btnLogin.setOnClickListener{
            val mainIntent = Intent(this, changePassword::class.java)


            startActivity(mainIntent)
            finish()
        }
    }
}