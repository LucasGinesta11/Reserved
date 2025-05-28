package com.example.reserved.data.remote.interceptor

import okhttp3.Interceptor
import okhttp3.Response

// Obtiene el token
class AuthInterceptor(private val token: String?) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()
        token?.let {
            requestBuilder.addHeader("Authorization", "Bearer $it")
        }
        return chain.proceed(requestBuilder.build())
    }
}