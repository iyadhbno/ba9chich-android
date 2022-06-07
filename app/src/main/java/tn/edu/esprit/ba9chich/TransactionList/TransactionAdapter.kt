package tn.edu.esprit.ba9chich.TransactionList

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import tn.edu.esprit.ba9chich.R
import tn.edu.esprit.ba9chich.models.Transaction
import tn.edu.esprit.ba9chich.models.TransactionList


class TransactionAdapter(val TransactionList: MutableList<TransactionList>) : RecyclerView.Adapter<TransactionViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_transaction, parent, false)

        return TransactionViewHolder(view)
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {


        val amount = TransactionList[position].transaction.amount
        val fromAddress = TransactionList[position].transaction.fromAddress

        val toAddress = TransactionList[position].transaction.toAddress

        holder.typeTransaction.text = fromAddress
        holder.Amount.text = amount.toString()
holder.EmailTransaction.text = toAddress
        holder.itemView.setOnClickListener{ v ->


        }




    }

    override fun getItemCount() = TransactionList.size

}