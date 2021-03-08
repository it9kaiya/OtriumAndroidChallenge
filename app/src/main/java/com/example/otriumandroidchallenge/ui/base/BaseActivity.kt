package com.example.otriumandroidchallenge.ui.base

import dagger.android.support.DaggerAppCompatActivity

abstract class BaseActivity<V, P : BasePresenter<V>> : DaggerAppCompatActivity() {
}