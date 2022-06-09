package tn.edu.esprit.ba9chich

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tn.edu.esprit.ba9chich.TransactionList.TransactionAdapter
import tn.edu.esprit.ba9chich.models.Transaction
import tn.edu.esprit.ba9chich.models.TransactionList
import tn.edu.esprit.ba9chich.utils.ApiInterface

class FragmentTransaction : Fragment() {

    lateinit var recylcerChampion: RecyclerView
    lateinit var recylcerChampionAdapter: TransactionAdapter
    var champList : MutableList<TransactionList> = ArrayList()
    lateinit var btnadd: FloatingActionButton
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_transaction, container, false)

        btnadd = rootView.findViewById(R.id.fab)
        btnadd.setOnClickListener{


        }

        recylcerChampion = rootView.findViewById(R.id.recyclerChampion)



        doLogin()


        recylcerChampionAdapter = TransactionAdapter(champList)
        recylcerChampion.adapter = recylcerChampionAdapter
        recylcerChampion.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL ,false)


        return rootView
    }
    private fun doLogin(){

        val apiInterface = ApiInterface.create()



        apiInterface.getalltransaction().enqueue(object : Callback<MutableList<TransactionList>> {

            override fun onResponse(call: Call<MutableList<TransactionList>>, response: Response<MutableList<TransactionList>>) {

                val user = response.body()

                if (user != null) {
                    champList=user
                    Log.d("list",champList.toString()
                    )
                    recylcerChampionAdapter = TransactionAdapter(champList)
                    recylcerChampion.adapter = recylcerChampionAdapter

                }



            }

            override fun onFailure(call: Call<MutableList<TransactionList>>, t: Throwable) {

            }

        })


    }

}