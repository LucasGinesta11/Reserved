package com.example.reserved.data.remote

import com.example.reserved.data.remote.interceptor.AuthInterceptor
import com.example.reserved.data.remote.api.ReservedApi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private  const val BASE_URL = "http://reserved-env.eba-pr3h34mr.us-east-1.elasticbeanstalk.com:8080/"
    // Localhost: http://10.0.2.2:8080/

    private fun getRetrofitInstance(token: String? = null): Retrofit {
        val client = OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(token))
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getApi(token: String? = null): ReservedApi {
        return getRetrofitInstance(token).create(ReservedApi::class.java)
    }
}