package com.example.ba9chich.models

import com.google.gson.annotations.SerializedName

data class User (
    @SerializedName("_id")
    var id: String,
    @SerializedName("nom")
    var nom: String,
    @SerializedName("email")
    var email: String,
    @SerializedName("password")
    var password: String,
    @SerializedName("imageUrl")
    var imageUrl: String,
    @SerializedName("phone")
    var phone: String
)