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
}
