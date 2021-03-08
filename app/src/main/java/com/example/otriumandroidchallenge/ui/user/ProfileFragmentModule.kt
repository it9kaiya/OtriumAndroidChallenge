package com.example.otriumandroidchallenge.ui.user

import androidx.annotation.NonNull
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.otriumandroidchallenge.data.remote.repo.user.ProfileRepositoryImpl
import dagger.Module
import dagger.Provides

@Module
class ProfileFragmentModule {

    class Factory internal constructor(val repo: ProfileRepositoryImpl) : ViewModelProvider.NewInstanceFactory() {
        @NonNull
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return ProfilePresenter(repo) as T
        }
    }

    @Provides
    fun provideView(fragment: ProfileFragment ) : ProfileView {
        return fragment;
    }

    @Provides
    fun providePresenter(view: ProfileView, fragment: ProfileFragment, repo: ProfileRepositoryImpl) : ProfilePresenter {
        return ViewModelProvider(fragment, Factory(repo)).get(ProfilePresenter::class.java)
    }
}