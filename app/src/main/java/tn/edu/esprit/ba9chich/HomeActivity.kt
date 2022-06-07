package tn.edu.esprit.ba9chich

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import tn.edu.esprit.ba9chich.models.User


class HomeActivity : AppCompatActivity() {
    lateinit var bottomNav: BottomNavigationView
    lateinit var nowuser: User
    private lateinit var mSharedPref: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        //toolbar
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        //setSupportActionBar(toolbar)

        toolbar.setNavigationOnClickListener {
            finish()
        }
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportFragmentManager.beginTransaction().add(R.id.fragment_container, FragmentTransaction()).commit()
        ///bottom bar
        mSharedPref = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        bottomNav = findViewById(R.id.bottomNavigationView)

        bottomNav.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.mihome -> {
                    changeFragment(FragmentTransaction(),"")
                    toolbar.setTitle("Home");
                }
                R.id.mimessage -> {
                    changeFragment(ProfileFragment(),"")
                    toolbar.setTitle("Message");
                }
                R.id.miprofil -> {


                    changeFragment(ProfileFragment(), "")
                    toolbar.setTitle("Profil");

                }
                R.id.miservice ->{
                    changeFragment(ProfileFragment(),"")
                    toolbar.setTitle("Menu");
                }
            }
            true
        }

        bottomNav.getOrCreateBadge(R.id.mimessage).apply {
            number = 8
            isVisible = true
        }

    }

    private fun changeFragment(fragment: Fragment, name: String) {

        if (name.isEmpty())
            supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit()
        else
            supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack("").commit()

    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){

            R.id.logoutMenu ->{
                val builder = AlertDialog.Builder(this)
                builder.setTitle("logout")
                builder.setMessage("Are you sure you want to logout?")
                builder.setPositiveButton("Yes"){ dialogInterface, which ->
                    getSharedPreferences(PREF_NAME, MODE_PRIVATE).edit().clear().apply()
                    val mainIntent = Intent(this, MainActivity::class.java)


                    startActivity(mainIntent)
                    finish()
                }
                builder.setNegativeButton("No"){dialogInterface, which ->
                    dialogInterface.dismiss()
                }
                builder.create().show()
            }
        }

        return super.onOptionsItemSelected(item)
    }
}