package com.example.ba9chich

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ba9chich.TransactionList.TransactionAdapter
import com.example.ba9chich.expenseList.ExpenseAdapter
import com.example.ba9chich.models.Expense
import com.example.ba9chich.models.Transaction
import com.example.ba9chich.models.User
import com.example.ba9chich.utils.ApiInterface
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TransactionFragment : Fragment() {

    lateinit var recylcerChampion: RecyclerView
    lateinit var recylcerChampionAdapter: TransactionAdapter
    var champList: MutableList<Transaction> = ArrayList()
    lateinit var btnadd: FloatingActionButton
    private lateinit var mSharedPref: SharedPreferences
    lateinit var nowuser: User

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_expense, container, false)
        mSharedPref = this.requireActivity().getSharedPreferences(PREF_NAME, AppCompatActivity.MODE_PRIVATE);
        btnadd = rootView.findViewById(R.id.fab)
        val gson = Gson()
        val  us =  mSharedPref.getString(myuser, "")

        nowuser = gson.fromJson(us,User::class.java)
        btnadd.setOnClickListener {
            val intent = Intent(activity, AddTransaction::class.java)
            startActivity(intent)

        }

        recylcerChampion = rootView.findViewById(R.id.recyclerChampion)



        doLogin()


        recylcerChampionAdapter = TransactionAdapter(champList)
        recylcerChampion.adapter = recylcerChampionAdapter
        recylcerChampion.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)


        return rootView

    }

    private fun doLogin() {

        val apiInterface = ApiInterface.create()



        apiInterface.AllTransaction().enqueue(object : Callback<MutableList<Transaction>> {

            override fun onResponse(
                call: Call<MutableList<Transaction>>,
                response: Response<MutableList<Transaction>>
            ) {

                val user = response.body()

                if (user != null) {
                    for(a in user){
                        if(a.fromAdress == nowuser.email || a.toAdress == nowuser.email ){
                            champList.add(a)
                        }
                    }



                    recylcerChampionAdapter = TransactionAdapter(ArrayList(champList.asReversed()))
                    recylcerChampion.adapter = recylcerChampionAdapter

                }


            }

            override fun onFailure(call: Call<MutableList<Transaction>>, t: Throwable) {

            }

        })


    }
}