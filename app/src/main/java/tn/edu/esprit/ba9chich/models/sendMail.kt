package tn.edu.esprit.ba9chich.models


import com.google.gson.annotations.SerializedName

data class sendMail (
    @SerializedName("email")
    var email: String,
    @SerializedName("code")
    var code: String
)