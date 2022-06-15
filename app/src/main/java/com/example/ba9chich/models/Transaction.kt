package com.example.ba9chich.models

import com.google.gson.annotations.SerializedName

data class Transaction (
    @SerializedName("_id")
    var id: String,
    @SerializedName("fromAdress")
    var fromAdress: String,
    @SerializedName("toAdress")
    var toAdress: String,
    @SerializedName("amount")
    var amount: String
)
