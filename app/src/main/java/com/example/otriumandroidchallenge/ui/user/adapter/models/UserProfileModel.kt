package com.example.otriumandroidchallenge.ui.user.adapter.models

data class UserProfileModel(val avatarUrl : String,
                            val name : String,
                            val login : String,
                            val email : String,
                            val followers : Int,
                            val following : Int)