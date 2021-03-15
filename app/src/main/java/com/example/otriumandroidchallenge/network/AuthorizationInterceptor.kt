package com.example.otriumandroidchallenge.network

import android.content.Context
import okhttp3.Interceptor
import okhttp3.Response

class AuthorizationInterceptor(val context: Context): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer 431d6020db3ab0f68d925fcda3610515b1432a6d")
            .build()

        return chain.proceed(request)
    }
}