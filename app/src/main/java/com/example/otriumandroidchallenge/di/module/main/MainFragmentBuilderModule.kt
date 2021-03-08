package com.example.otriumandroidchallenge.di.module.main

import com.example.otriumandroidchallenge.ui.user.ProfileFragment
import com.example.otriumandroidchallenge.ui.user.ProfileFragmentModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainFragmentBuilderModule {

    @ContributesAndroidInjector(
        modules = [
            ProfileFragmentModule::class
        ]
    )
    abstract fun contributeProfileFragment() : ProfileFragment
}