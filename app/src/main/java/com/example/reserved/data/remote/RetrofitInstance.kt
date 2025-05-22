package com.example.reserved.data.remote

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private fun getRetrofitInstance(token: String? = null): Retrofit {
        val client = OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(token))
            .build()

        return Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getApi(token: String? = null): EstablishmentApi {
        return getRetrofitInstance(token).create(EstablishmentApi::class.java)
    }
}