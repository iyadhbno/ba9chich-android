package tn.edu.esprit.ba9chich.models

import com.google.gson.annotations.SerializedName

data class TransactionList (

    @SerializedName("_id")
    var id: String,
    @SerializedName("transaction")
    var transaction: Transaction

)