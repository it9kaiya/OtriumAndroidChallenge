package com.example.otriumandroidchallenge.ui.user

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.otriumandroidchallenge.UserQuery
import com.example.otriumandroidchallenge.data.remote.repo.user.ProfileRepositoryImpl
import com.example.otriumandroidchallenge.ui.base.BasePresenter
import io.reactivex.rxjava3.disposables.CompositeDisposable

class ProfilePresenter(val repo: ProfileRepositoryImpl): BasePresenter<ProfileView>() {

    private val mCompositeDisposable = CompositeDisposable()

    private val userLiveData: MutableLiveData<UserQuery.Data> = MutableLiveData()

    fun test() {
        Log.d("TAG", "test: Profile presenter is working")

        mCompositeDisposable.add(
            repo.getProfileInfo()
                .doOnError {
                    println("The Number Is: ${it.message}")
                    userLiveData.postValue(null)
                }
                .subscribe(
                    {
//                        if (it.errors?.size  == 0) {
                            userLiveData.postValue(it.data)
//                        } else {
//                            userLiveData.postValue(null)
//                        }
                    },
                    { t ->
                        userLiveData.postValue(null)
                        print(t.message)
                    }
                )
        );
    }

    override fun onCleared() {
        mCompositeDisposable.clear()
        super.onCleared()
    }

    fun getUserLiveData(): MutableLiveData<UserQuery.Data> {
        return userLiveData
    }
}