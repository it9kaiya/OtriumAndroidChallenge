package com.example.otriumandroidchallenge.di.module

import android.app.Application
import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.cache.http.ApolloHttpCache
import com.apollographql.apollo.cache.http.DiskLruHttpCacheStore
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.RequestOptions
import com.example.otriumandroidchallenge.R
import com.example.otriumandroidchallenge.network.AuthorizationInterceptor
import com.example.otriumandroidchallenge.util.Constants
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import javax.inject.Singleton

@Module
class AppModule {

    @Singleton
    @Provides
    fun getContext(application: Application): Context {
        return application.applicationContext
    }

    @Singleton
    @Provides
    fun provideCacheFile(context: Context) : File {
        return File(context.cacheDir, "apolloCache")
    }

    @Singleton
    @Provides
    fun provideCacheStore(file : File) : DiskLruHttpCacheStore {
        return DiskLruHttpCacheStore(file, 1024 * 1024)
    }
    @Singleton
    @Provides
    fun provideAuthorizationInterceptor(context: Context): AuthorizationInterceptor {
        return AuthorizationInterceptor(context)
    }

    @Singleton
    @Provides
    fun provideApolloClient(authorizationInterceptor: AuthorizationInterceptor, cacheStore : DiskLruHttpCacheStore): ApolloClient {
        return ApolloClient.builder()
            .serverUrl("https://api.github.com/graphql")
            .okHttpClient(
                OkHttpClient.Builder()
                    .addInterceptor(authorizationInterceptor)
                    .build()
            )
            .httpCache(ApolloHttpCache(cacheStore))
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofitInstance(): Retrofit? {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideRequestOptions(): RequestOptions? {
        return RequestOptions
            .placeholderOf(R.mipmap.ic_launcher)
            .error(R.mipmap.ic_launcher)
    }

    @Singleton
    @Provides
    fun provideRequestManager(
        application: Application?,
        requestOptions: RequestOptions?
    ): RequestManager? {
        return Glide.with(application!!)
            .setDefaultRequestOptions(requestOptions!!)
    }

    @Singleton
    @Provides
    fun provideDrawable(application: Application?): Drawable? {
        return ContextCompat.getDrawable(application!!, R.mipmap.ic_launcher_round)
    }

    @Singleton
    @Provides
    fun someString(): String {
        return "Test"
    }
}