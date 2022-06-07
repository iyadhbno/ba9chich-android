package tn.edu.esprit.ba9chich.models

import com.google.gson.annotations.SerializedName


data class Transaction (

    @SerializedName("_id")
    var id: String,
    @SerializedName("fromAddress")
    var fromAddress: String,
    @SerializedName("toAddress")
    var toAddress: String,
    @SerializedName("amount")
    var amount: Number,
)