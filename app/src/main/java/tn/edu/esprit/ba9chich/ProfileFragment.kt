package tn.edu.esprit.ba9chich

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import tn.edu.esprit.ba9chich.models.User

class ProfileFragment : Fragment() {

    private lateinit var txtFullName: TextView
    private lateinit var txtName: TextView
    private lateinit var txtemail: TextView
    private lateinit var txtPhone: TextView
    private lateinit var buttonChangepassword: TextView
    lateinit var nowuser: User
    private lateinit var mSharedPref: SharedPreferences

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        var rootView : View = inflater.inflate(R.layout.fragment_profile, container, false)
        txtFullName = rootView.findViewById(R.id.idfullname)
        txtFullName.isEnabled = false
        mSharedPref = this.requireActivity()?.getSharedPreferences(PREF_NAME, AppCompatActivity.MODE_PRIVATE);
        txtName = rootView.findViewById(R.id.idAddress)
        txtName.isEnabled = false
        txtPhone = rootView.findViewById(R.id.idphone)
        txtPhone.isEnabled = false
        txtemail = rootView.findViewById(R.id.idEmail)
        txtemail.isEnabled = false

        buttonChangepassword= rootView.findViewById(R.id.edit)
        val gson = Gson()
        val  us =  mSharedPref.getString(myuser, "")

        nowuser = gson.fromJson(us,User::class.java)
        val name = nowuser.nom
        val ph = nowuser.phone
        val emaill =nowuser.email
        txtFullName.text = " $name"
        txtName.text = " $name"
        txtPhone.text = " $ph"
        txtemail.text = " $emaill"



        return rootView
    }


}