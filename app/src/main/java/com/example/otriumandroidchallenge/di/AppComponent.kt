package com.example.otriumandroidchallenge.di

import android.app.Application
import com.example.otriumandroidchallenge.app.BaseApplication
import com.example.otriumandroidchallenge.di.module.ActivityBuilderModule
import com.example.otriumandroidchallenge.di.module.AppModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        ActivityBuilderModule::class,
        AppModule::class,
    ]
)
interface AppComponent: AndroidInjector<BaseApplication> {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder
        fun build(): AppComponent
    }
}