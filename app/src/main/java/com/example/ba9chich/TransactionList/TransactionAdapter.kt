package com.example.ba9chich.TransactionList

import android.content.SharedPreferences
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.ba9chich.PREF_NAME
import com.example.ba9chich.R
import com.example.ba9chich.expenseList.ExpenseViewHolder
import com.example.ba9chich.models.Expense
import com.example.ba9chich.models.Transaction
import com.example.ba9chich.models.User
import com.example.ba9chich.myuser
import com.google.gson.Gson

class TransactionAdapter  (val QuestionList: MutableList<Transaction>) : RecyclerView.Adapter<TransactionViewHolder>() {
    lateinit var nowuser: User
    private lateinit var mSharedPref: SharedPreferences
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.itemtransaction, parent, false)
        mSharedPref =view.context.getSharedPreferences(PREF_NAME, AppCompatActivity.MODE_PRIVATE);
        val gson = Gson()
        val  us =  mSharedPref.getString(myuser, "")

        nowuser = gson.fromJson(us,User::class.java)
        return TransactionViewHolder(view)
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {


        val description = QuestionList[position].amount
        val subject = QuestionList[position].toAdress
        val idClient = QuestionList[position].fromAdress

        if(nowuser.email == subject){
            holder.itemView.setBackgroundColor(Color.GREEN)
            holder.QuestionSubject.text = idClient
        }else{
            holder.itemView.setBackgroundColor(Color.RED)
            holder.QuestionSubject.text = subject
        }
        holder.QuestionDescription.text = description








        holder.itemView.setOnClickListener{ v ->

            // val intent = Intent(v.context, QuestionDetails::class.java)
            //  intent.putExtra("description",description);
            //  intent.putExtra("subject",subject);
            // intent.putExtra("idClient",idClient);
            //  intent.putExtra("idQuestion",idQuestion);
            //  v.context.startActivity(intent)

        }




    }

    override fun getItemCount() = QuestionList.size

}