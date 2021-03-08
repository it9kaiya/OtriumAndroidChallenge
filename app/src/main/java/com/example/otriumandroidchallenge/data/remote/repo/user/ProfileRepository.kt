package com.example.otriumandroidchallenge.data.remote.repo.user

import com.apollographql.apollo.api.Response
import com.example.otriumandroidchallenge.UserQuery
import io.reactivex.rxjava3.annotations.NonNull
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Observable

interface ProfileRepository {

    fun getProfileInfo(): Observable<Response<UserQuery.Data>>
}