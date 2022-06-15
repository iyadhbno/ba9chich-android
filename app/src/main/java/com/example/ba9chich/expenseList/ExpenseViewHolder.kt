package com.example.ba9chich.expenseList

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ba9chich.R

class ExpenseViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {

    val QuestionDescription : TextView
    val QuestionSubject: TextView = itemView.findViewById<TextView>(R.id.Subject)

    init {

        QuestionDescription = itemView.findViewById<TextView>(R.id.Description)
    }

}