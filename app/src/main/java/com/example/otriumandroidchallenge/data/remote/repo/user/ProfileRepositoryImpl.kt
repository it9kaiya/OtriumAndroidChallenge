package com.example.otriumandroidchallenge.data.remote.repo.user

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.rx3.rxQuery
import com.example.otriumandroidchallenge.UserQuery
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val apolloClient: ApolloClient
) : ProfileRepository {

    override fun getProfileInfo(): Observable<Response<UserQuery.Data>> {

        // Create a query object
        val query = UserQuery()
        // Directly create Observable with Kotlin extension
        val observable = apolloClient.rxQuery(query)
        return observable.observeOn(Schedulers.io()).subscribeOn(AndroidSchedulers.mainThread())
    }
}