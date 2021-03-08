package com.example.otriumandroidchallenge.network

import android.content.Context
import okhttp3.Interceptor
import okhttp3.Response

class AuthorizationInterceptor(val context: Context): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer c48d4e200d95a90378cfa32d8ad88cf0183ebae5")
            .build()

        return chain.proceed(request)
    }
}