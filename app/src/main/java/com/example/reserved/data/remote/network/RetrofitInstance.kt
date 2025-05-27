package com.example.reserved.data.remote.network

import com.example.reserved.data.remote.interceptor.AuthInterceptor
import com.example.reserved.data.remote.api.EstablishmentApi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private fun getRetrofitInstance(token: String? = null): Retrofit {
        val client = OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(token))
            .build()

        return Retrofit.Builder()
            .baseUrl("http://reserved-env.eba-pr3h34mr.us-east-1.elasticbeanstalk.com:8080/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getApi(token: String? = null): EstablishmentApi {
        return getRetrofitInstance(token).create(EstablishmentApi::class.java)
    }
}