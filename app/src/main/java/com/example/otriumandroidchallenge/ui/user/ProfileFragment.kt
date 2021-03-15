package com.example.otriumandroidchallenge.ui.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.otriumandroidchallenge.R
import com.example.otriumandroidchallenge.UserQuery
import com.example.otriumandroidchallenge.data.remote.repo.user.ProfileRepositoryImpl
import com.example.otriumandroidchallenge.ui.base.BaseFragment
import com.example.otriumandroidchallenge.ui.user.adapter.FeedAdapter
import com.example.otriumandroidchallenge.ui.user.adapter.FeedItemBinder
import com.example.otriumandroidchallenge.ui.user.adapter.FeedItemClass
import com.example.otriumandroidchallenge.ui.user.adapter.models.RepoListModel
import com.example.otriumandroidchallenge.ui.user.adapter.models.RepoModel
import com.example.otriumandroidchallenge.ui.user.adapter.models.UserProfileModel
import com.example.otriumandroidchallenge.ui.user.adapter.viewholder.RepoListViewBinder
import com.example.otriumandroidchallenge.ui.user.adapter.viewholder.UserProfileViewBinder
import com.example.otriumandroidchallenge.util.Constants
import kotlinx.android.synthetic.main.fragment_profile.*
import javax.inject.Inject

class ProfileFragment : BaseFragment<ProfileView, ProfilePresenter>(), ProfileView,
    SwipeRefreshLayout.OnRefreshListener {

    companion object {
        private const val TAG = "ProfileFragment"
    }

    private val arrayListRepos: ArrayList<Any> = ArrayList()
    private val arrayListPinnedRepos: ArrayList<RepoModel> = ArrayList()
    private val arrayListTopRepos: ArrayList<RepoModel> = ArrayList()
    private val arrayListStarredRepos: ArrayList<RepoModel> = ArrayList()
    private val profile: ArrayList<UserProfileModel> = ArrayList()

    private var adapter: FeedAdapter? = null
    private var swipeContainer: SwipeRefreshLayout? = null

    @Inject
    lateinit var presenter: ProfilePresenter

    @Inject
    lateinit var profileRepo: ProfileRepositoryImpl

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        subscribeObservers()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        swipeContainer = view.findViewById(R.id.swipeContainer)
        swipeContainer?.setOnRefreshListener(this)
        presenter.getProfileData(false)
    }

    private fun subscribeObservers() {
        presenter.getUserLiveData().observe(this, Observer {
            if (it != null) {
                println("User login: ${it.viewer.login}")
                addData(it, arrayListRepos, arrayListPinnedRepos, arrayListTopRepos, arrayListStarredRepos)
            } else {
                println("User login: Error")
            }
        })

        presenter.getRefreshedUserLiveData().observe(this, Observer {
            if (it != null) {
                println("User login: ${it.viewer.login}")
                addData(it, ArrayList(), ArrayList(), ArrayList(), ArrayList())
            } else {
                println("User login: Error")
            }
        })
    }

    private fun addData(userQuery: UserQuery.Data,
                        list: ArrayList<Any>,
                        listPinnedRepos: ArrayList<RepoModel>,
                        listTopRepos: ArrayList<RepoModel>,
                        listStarredRepos: ArrayList<RepoModel>,
                        ) {

        val userProfileModel = UserProfileModel(
            userQuery.viewer.avatarUrl as String,
            userQuery.viewer.name as String,
            userQuery.viewer.login,
            userQuery.viewer.email,
            userQuery.viewer.followers.totalCount as Int,
            userQuery.viewer.following.totalCount as Int
        )
        profile.add(userProfileModel)
        list.add(profile[0])

        userQuery?.viewer?.pinnedItems?.nodes?.let {
            for (node in it) {
                val repoModel: RepoModel = RepoModel(
                    node?.asRepository?.id,
                    node?.asRepository?.owner?.avatarUrl as? String ?: "n/a",
                    node?.asRepository?.owner?.login as? String ?: "n/a",
                    node?.asRepository?.name as? String ?: "n/a",
                    node?.asRepository?.description as? String ?: "n/a",
                    node?.asRepository?.primaryLanguage as? String ?: "n/a",
                    node?.asRepository?.stargazerCount as? Int ?: -1,
                )
                listPinnedRepos.add(repoModel)
            }

            val pinnedReposListModel: RepoListModel = RepoListModel(
                listPinnedRepos,
                Constants.HORIZONTAL_LIST
            )
            pinnedReposListModel.title = "Pinned"
            list.add(pinnedReposListModel)
        }

        userQuery?.viewer?.topRepositories?.nodes?.let {
            for (node in it) {
                val repoModel: RepoModel = RepoModel(
                    node?.id,
                    node?.owner?.avatarUrl as? String ?: "n/a",
                    node?.owner?.login as? String ?: "n/a",
                    node?.name as? String ?: "n/a",
                    node?.description as? String ?: "n/a",
                    node?.primaryLanguage as? String ?: "n/a",
                    node?.stargazerCount as? Int ?: -1,
                )
                listTopRepos.add(repoModel)
            }

            val topReposListModel: RepoListModel = RepoListModel(
                listTopRepos,
                Constants.HORIZONTAL_LIST
            )
            topReposListModel.title = "Top repositories"
            list.add(topReposListModel)
        }

        userQuery?.viewer?.starredRepositories?.nodes?.let {
            for (node in it) {
                val repoModel: RepoModel = RepoModel(
                    node?.id,
                    node?.owner?.avatarUrl as? String ?: "n/a",
                    node?.owner?.login as? String ?: "n/a",
                    node?.name as? String ?: "n/a",
                    node?.description as? String ?: "n/a",
                    node?.primaryLanguage as? String ?: "n/a",
                    node?.stargazerCount as? Int ?: -1,
                )
                listStarredRepos.add(repoModel)
            }

            val starredReposListModel: RepoListModel = RepoListModel(
                listStarredRepos,
                Constants.HORIZONTAL_LIST
            )
            starredReposListModel.title = "Starred repositories"
            list.add(starredReposListModel)
        }

        showFeedItems(vertical_recyclerview, list)
    }

    fun repoItemClick(data: RepoModel) {

    }

    fun userProfileClick(data: UserProfileModel) {

    }

    private fun showFeedItems(recyclerView: RecyclerView, list: ArrayList<Any>?) {

        if (adapter == null) {
            val viewBinders = mutableMapOf<FeedItemClass, FeedItemBinder>()

            val userProfileViewBinder = UserProfileViewBinder { data: UserProfileModel ->
                userProfileClick(data)
            }

            val repoListViewBinder = RepoListViewBinder { data: RepoModel ->
                repoItemClick(data)
            }

            @Suppress("UNCHECKED_CAST")
            viewBinders.put(
                userProfileViewBinder.modelClass,
                userProfileViewBinder as FeedItemBinder
            )

            @Suppress("UNCHECKED_CAST")
            viewBinders.put(
                repoListViewBinder.modelClass,
                repoListViewBinder as FeedItemBinder
            )

            adapter = FeedAdapter(viewBinders)
        }

        recyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }
        if (recyclerView.adapter == null) {
            recyclerView.adapter = adapter
        }
        (recyclerView.adapter as FeedAdapter).submitList(list ?: emptyList())
    }

    override fun onRefresh() {
        Toast.makeText(context, "Refreshed", Toast.LENGTH_SHORT).show()
        swipeContainer?.isRefreshing = false;
        presenter.getProfileData(true)
    }
}