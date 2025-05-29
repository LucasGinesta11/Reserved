package com.example.reserved.data.remote.interceptor

import com.example.reserved.data.session.SessionManager
import okhttp3.Interceptor
import okhttp3.Response

// Obtiene el token
class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = SessionManager.token
        val requestBuilder = chain.request().newBuilder()
        token?.let {
            requestBuilder.addHeader("Authorization", "Bearer $it")
        }
        return chain.proceed(requestBuilder.build())
    }
}
