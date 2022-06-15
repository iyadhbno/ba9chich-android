package com.example.ba9chich.models

import com.google.gson.annotations.SerializedName

data class Expense (
    @SerializedName("_id")
    var id: String,
    @SerializedName("item")
    var item: String,
    @SerializedName("price")
    var price: String,
    @SerializedName("userId")
    var userId: String

)
