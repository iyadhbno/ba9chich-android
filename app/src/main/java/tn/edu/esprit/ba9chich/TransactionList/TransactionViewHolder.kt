package tn.edu.esprit.ba9chich.TransactionList

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import tn.edu.esprit.ba9chich.R


class TransactionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val typeTransaction : TextView
    val EmailTransaction: TextView = itemView.findViewById<TextView>(R.id.EmailTransaction)

    val Amount: TextView = itemView.findViewById<TextView>(R.id.Amount)

    init {

        typeTransaction = itemView.findViewById<TextView>(R.id.typeTransaction)
    }

}