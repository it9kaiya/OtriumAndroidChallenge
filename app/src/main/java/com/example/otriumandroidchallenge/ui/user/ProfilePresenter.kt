package com.example.otriumandroidchallenge.ui.user

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.otriumandroidchallenge.UserQuery
import com.example.otriumandroidchallenge.data.remote.repo.user.ProfileRepositoryImpl
import com.example.otriumandroidchallenge.ui.base.BasePresenter
import io.reactivex.rxjava3.disposables.CompositeDisposable

class ProfilePresenter(val repo: ProfileRepositoryImpl) : BasePresenter<ProfileView>() {

    private val TAG = "ProfilePresenter"

    private val mCompositeDisposable = CompositeDisposable()

    private val userLiveData: MutableLiveData<UserQuery.Data> = MutableLiveData()
    private val refreshedUserLiveData: MutableLiveData<UserQuery.Data> = MutableLiveData()

    fun getProfileData(isRefresh: Boolean) {
        mCompositeDisposable.add(
            repo.getProfileInfo()
                .doOnError {
                    userLiveData.postValue(null)
                }
                .subscribe(
                    {
                        if (!isRefresh)
                            userLiveData.postValue(it.data)
                        else
                            refreshedUserLiveData.postValue(it.data)
                    },
                    { t ->
                        if (!isRefresh)
                            userLiveData.postValue(null)
                        else
                            refreshedUserLiveData.postValue(null)
                        Log.e(TAG, t.message)
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

    fun getRefreshedUserLiveData(): MutableLiveData<UserQuery.Data> {
        return refreshedUserLiveData
    }
}