package com.example.otriumandroidchallenge.ui.base

import androidx.annotation.CallSuper
import androidx.lifecycle.ViewModel

open class BasePresenter<V>() : ViewModel() {

    private var view: V? = null

    protected fun getView() : V? {
        return view
    }

    protected fun isViewAttached() : Boolean {
        return view != null
    }

    @CallSuper
    protected fun onViewAttached(view : V) {
        this.view = view
    }

    @CallSuper
    protected fun onViewDetached() {
        this.view = null
    }
}