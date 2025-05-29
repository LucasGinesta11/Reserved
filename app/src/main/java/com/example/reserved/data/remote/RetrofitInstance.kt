package com.example.reserved.data.remote

import com.example.reserved.data.remote.interceptor.AuthInterceptor
import com.example.reserved.data.remote.api.ReservedApi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    // Localhost: 10.0.2.2
    private const val BASE_URL =
        "http://reserved-env.eba-pr3h34mr.us-east-1.elasticbeanstalk.com:8080/"

    private var client = buildClient()
    private var retrofit = buildRetrofit()

    private fun buildClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor())
            .build()
    }

    private fun buildRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getApi(): ReservedApi {
        return retrofit.create(ReservedApi::class.java)
    }

    fun reset() {
        client = buildClient()
        retrofit = buildRetrofit()
    }
}
