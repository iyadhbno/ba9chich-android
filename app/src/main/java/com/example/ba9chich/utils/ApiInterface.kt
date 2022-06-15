package com.example.ba9chich.utils

import com.example.ba9chich.models.Expense
import com.example.ba9chich.models.Transaction
import com.example.ba9chich.models.User
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.util.concurrent.TimeUnit

interface ApiInterface {

    @POST("loginClient")
    fun seConnecter(@Body info: RequestBody): Call<User>



    @POST("createuser")
    fun SignIn(@Body info: RequestBody): Call<User>

    @PUT("updateuser/{id}")
    fun UpdateUser(@Body info: RequestBody, @Path("id") id : String): Call<User>

    @PUT("updateuserpass/{id}")
    fun updateusernotpass(@Body info: RequestBody, @Path("id") id : String): Call<User>

    @GET("expense/allExpenses")
    fun AllExpense(): Call<MutableList<Expense>>
    @GET("allusers")
    fun allusers(): Call<MutableList<User>>

    @GET("getuserEmail/{email}")
    fun getuserbyemail( @Path("email") email : String): Call<MutableList<User>>

    @GET("transactions/allTransactions")
    fun AllTransaction(): Call<MutableList<Transaction>>



    @POST("expense/createExpense")
    fun createexpense(@Body info: RequestBody): Call<Expense>

    @POST("transactions/createTransaction")
    fun createTransaction(@Body info: RequestBody): Call<Transaction>

    @Multipart
    @POST("/updateImageClient/{id}")
    fun upload(@Part image: MultipartBody.Part, @Path("id") id : String): Call<User>
    companion object {

        var BASE_URL = "http://192.168.100.11:3000/"

        fun create() : ApiInterface {
            val httpClient = OkHttpClient.Builder()
                .callTimeout(60, TimeUnit.MINUTES)
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .baseUrl(BASE_URL)
                .build()

            return retrofit.create(ApiInterface::class.java)
        }
    }
}