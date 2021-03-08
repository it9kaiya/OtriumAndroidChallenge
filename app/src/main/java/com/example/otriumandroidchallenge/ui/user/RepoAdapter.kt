package com.example.otriumandroidchallenge.ui.user

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.otriumandroidchallenge.R
import com.example.otriumandroidchallenge.UserQuery

enum class REPO_TYPE{
    PINNED, TOP, STARRED
}

internal class RepoAdapter(val context: Context): RecyclerView.Adapter<RepoAdapter.MyViewHolder>() {

    var userQuery: UserQuery.Data? = null
    var repoType: REPO_TYPE? = null

    internal inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var imageViewProfile: ImageView = view.findViewById(R.id.imageViewProfile)
        var textViewLogin: TextView = view.findViewById(R.id.textViewLogin)
        var textViewRepoName: TextView = view.findViewById(R.id.textViewRepoName)
        var textViewRepoDesc: TextView = view.findViewById(R.id.textViewRepoDesc)
        var textViewStars: TextView = view.findViewById(R.id.textViewStars)
        var textViewPrimaryLanguage: TextView = view.findViewById(R.id.textViewPrimaryLanguage)
    }

    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_repo, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        var avatarUrl: String? = null
        var login: String? = null
        var repoName: String? = null
        var repoDesc: String? = null
        var primaryLanguage: String? = null
        var starCount: String? = null

        if (repoType == REPO_TYPE.PINNED) {
            val pinnedRepos = userQuery!!.viewer.pinnedItems.nodes?.get(position)
            if (pinnedRepos != null) {
                avatarUrl = pinnedRepos.asRepository?.owner?.avatarUrl.toString()
                login = pinnedRepos.asRepository?.owner?.login.toString()
                repoName = pinnedRepos.asRepository?.name.toString()
                repoDesc = pinnedRepos.asRepository?.description.toString()
                primaryLanguage = pinnedRepos.asRepository?.primaryLanguage?.name.toString()
                starCount = pinnedRepos.asRepository?.stargazerCount.toString()
            };
        } else if (repoType == REPO_TYPE.TOP) {
            val topRepos = userQuery!!.viewer.topRepositories.nodes?.get(position)
            if (topRepos != null) {
                avatarUrl = topRepos.owner.avatarUrl.toString()
                login = topRepos?.owner?.login.toString()
                repoName = topRepos?.name.toString()
                repoDesc = topRepos?.description.toString()
                primaryLanguage = topRepos?.primaryLanguage?.name.toString()
                starCount = topRepos?.stargazerCount.toString()
            };
        } else {
            val starredRepos = userQuery!!.viewer.starredRepositories.nodes?.get(position)
            if (starredRepos != null) {
                avatarUrl = starredRepos.owner.avatarUrl.toString()
                login = starredRepos?.owner?.login.toString()
                repoName = starredRepos?.name.toString()
                repoDesc = starredRepos?.description.toString()
                primaryLanguage = starredRepos?.primaryLanguage?.name.toString()
                starCount = starredRepos?.stargazerCount.toString()
            };
        }

        Glide.with(context)
            .load(avatarUrl)
            .circleCrop()
            .into(holder.imageViewProfile);
        holder.textViewLogin.setText(login)
        holder.textViewRepoName.setText(repoName)
        holder.textViewRepoDesc.setText(repoDesc)
        holder.textViewPrimaryLanguage.setText(primaryLanguage)
        holder.textViewStars.setText(starCount)
    }

    override fun getItemCount(): Int {
        if (repoType == null)
            return 0
        else {
            if (repoType == REPO_TYPE.PINNED)
                return if(userQuery == null || userQuery!!.viewer == null || userQuery!!.viewer.pinnedItems == null || userQuery!!.viewer.pinnedItems.nodes == null) {
                    0
                } else {
                    userQuery!!.viewer.pinnedItems.nodes!!.size
                }
            else if (repoType == REPO_TYPE.TOP) {
                return if(userQuery == null || userQuery!!.viewer == null || userQuery!!.viewer.topRepositories == null || userQuery!!.viewer.topRepositories.nodes == null) {
                    0
                } else {
                    userQuery!!.viewer.topRepositories.nodes!!.size
                }
            } else {
                return if(userQuery == null || userQuery!!.viewer == null || userQuery!!.viewer.starredRepositories == null || userQuery!!.viewer.starredRepositories.nodes == null) {
                    0
                } else {
                    userQuery!!.viewer.starredRepositories.nodes!!.size
                }
            }
        }
    }

    fun setData(userQuery: UserQuery.Data, repoType: REPO_TYPE) {
        this.userQuery = userQuery
        this.repoType = repoType
        notifyDataSetChanged()
    }
}