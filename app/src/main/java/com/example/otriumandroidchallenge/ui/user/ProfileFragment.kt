package com.example.otriumandroidchallenge.ui.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.example.otriumandroidchallenge.R
import com.example.otriumandroidchallenge.UserQuery
import com.example.otriumandroidchallenge.data.remote.repo.user.ProfileRepositoryImpl
import com.example.otriumandroidchallenge.ui.base.BaseFragment
import com.example.otriumandroidchallenge.util.PeekingLinearLayoutManager
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class ProfileFragment : BaseFragment<ProfileView, ProfilePresenter>(), ProfileView {

    lateinit var imageViewProfile: ImageView
    lateinit var textViewName: TextView
    lateinit var textViewLogin: TextView
    lateinit var textViewEmail: TextView
    lateinit var textViewFollowers: TextView
    lateinit var textViewFollowing: TextView

    private lateinit var adapterPinned: RepoAdapter
    private lateinit var adapterTopped: RepoAdapter
    private lateinit var adapterStarred: RepoAdapter

    @Inject lateinit var presenter: ProfilePresenter
    @Inject lateinit var profileRepo: ProfileRepositoryImpl

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        subscribeObservers()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        imageViewProfile = view.findViewById(R.id.imageViewProfile)
        textViewName = view.findViewById(R.id.textViewName)
        textViewLogin = view.findViewById(R.id.textViewLogin)
        textViewEmail = view.findViewById(R.id.textViewEmail)
        textViewFollowers = view.findViewById(R.id.textViewFollowers)
        textViewFollowing = view.findViewById(R.id.textViewFollowing)

        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerViewPinnedRepo)
        val layoutManager = LinearLayoutManager(context)
        adapterPinned = RepoAdapter(this.requireContext())
        recyclerView.layoutManager = layoutManager
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.adapter = adapterPinned
        recyclerView.isNestedScrollingEnabled = false

        val recyclerViewTopRepo: RecyclerView = view.findViewById(R.id.recyclerViewTopRepo)
        adapterTopped = RepoAdapter(this.requireContext())
        recyclerViewTopRepo.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerViewTopRepo.itemAnimator = DefaultItemAnimator()
        recyclerViewTopRepo.adapter = adapterTopped
        recyclerViewTopRepo.isNestedScrollingEnabled = false

        val recyclerViewStarredRepo: RecyclerView = view.findViewById(R.id.recyclerViewStarredRepo)
        adapterStarred = RepoAdapter(this.requireContext())
        recyclerViewStarredRepo.layoutManager = PeekingLinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerViewStarredRepo.itemAnimator = DefaultItemAnimator()
        recyclerViewStarredRepo.adapter = adapterStarred
        recyclerViewStarredRepo.isNestedScrollingEnabled = false

        presenter.test()
    }

    fun subscribeObservers() {
        presenter.getUserLiveData().observe(this, Observer {
            if (it != null) {
                println("User login: ${it.viewer.login}")
                initUiLabels(it)
            } else {
                println("User login: Error")
            }
        })
    }

    private fun initUiLabels(userQuery: UserQuery.Data) {

        Glide.with(this)
            .load(userQuery.viewer.avatarUrl)
            .circleCrop()
            .into(imageViewProfile);
        textViewName.setText(userQuery.viewer.name)
        textViewLogin.setText(userQuery.viewer.login)
        textViewEmail.setText(userQuery.viewer.email)
        textViewFollowers.setText("${userQuery.viewer.followers.totalCount} Followers")
        textViewFollowing.setText("${userQuery.viewer.following.totalCount} Following")

        adapterPinned.setData(userQuery, REPO_TYPE.PINNED)
        adapterTopped.setData(userQuery, REPO_TYPE.TOP)
        adapterStarred.setData(userQuery, REPO_TYPE.STARRED)
    }

    companion object {
        private const val TAG = "ProfileFragment"
    }
}