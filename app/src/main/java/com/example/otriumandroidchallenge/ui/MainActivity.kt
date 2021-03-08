package com.example.otriumandroidchallenge.ui

import android.content.Context
import android.os.Bundle
import com.example.otriumandroidchallenge.R
import dagger.android.support.DaggerAppCompatActivity
import okhttp3.Interceptor
import okhttp3.Response

class MainActivity : DaggerAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    private class AuthorizationInterceptor(val context: Context): Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val request = chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer c48d4e200d95a90378cfa32d8ad88cf0183ebae5")
                    .build()


            return chain.proceed(request)
        }
    }
}
