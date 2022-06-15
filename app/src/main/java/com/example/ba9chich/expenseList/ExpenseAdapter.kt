package com.example.ba9chich.expenseList

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ba9chich.R
import com.example.ba9chich.models.Expense

class ExpenseAdapter (val QuestionList: MutableList<Expense>) : RecyclerView.Adapter<ExpenseViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.itemquestion, parent, false)

        return ExpenseViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExpenseViewHolder, position: Int) {


        val description = QuestionList[position].price
        val subject = QuestionList[position].item

        val idClient = QuestionList[position].userId

        holder.QuestionDescription.text = description
        holder.QuestionSubject.text = subject







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