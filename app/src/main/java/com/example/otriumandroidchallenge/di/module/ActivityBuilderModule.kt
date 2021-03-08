package com.example.otriumandroidchallenge.di.module

import com.example.otriumandroidchallenge.di.module.main.MainFragmentBuilderModule
import com.example.otriumandroidchallenge.di.module.main.MainModule
import com.example.otriumandroidchallenge.ui.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilderModule {

    @ContributesAndroidInjector(
        modules = [
            MainModule::class,
            MainFragmentBuilderModule::class
        ]
    )
    abstract fun contributeMainActivity() : MainActivity
}
