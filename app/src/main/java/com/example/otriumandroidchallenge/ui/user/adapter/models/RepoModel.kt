package com.example.otriumandroidchallenge.ui.user.adapter.models

data class RepoModel(
    val id: String?,
    val avatarUrl: String,
    val login: String,
    val repoName: String,
    val repoDesc: String,
    val primaryLanguage: String,
    val starCount: Int
)