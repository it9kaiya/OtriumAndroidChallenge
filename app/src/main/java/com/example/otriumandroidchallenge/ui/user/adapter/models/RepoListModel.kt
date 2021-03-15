package com.example.otriumandroidchallenge.ui.user.adapter.models

import com.example.otriumandroidchallenge.util.Constants

data class RepoListModel(val repos : ArrayList<RepoModel>,
                         val id : Int = Constants.HORIZONTAL_LIST,
                         var title : String = "") {
}