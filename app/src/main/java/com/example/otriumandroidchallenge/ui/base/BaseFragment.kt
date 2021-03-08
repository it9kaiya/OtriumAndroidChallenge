package com.example.otriumandroidchallenge.ui.base

import dagger.android.support.DaggerFragment

abstract class BaseFragment<V, P : BasePresenter<V>> : DaggerFragment() {
}