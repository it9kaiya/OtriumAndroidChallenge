package com.example.otriumandroidchallenge.ui.user.adapter.viewholder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.otriumandroidchallenge.R
import com.example.otriumandroidchallenge.ui.user.adapter.FeedItemViewBinder
import com.example.otriumandroidchallenge.ui.user.adapter.models.RepoModel
import kotlinx.android.synthetic.main.adapter_repo.view.*

class RepoViewBinder(val block : (data: RepoModel) -> Unit) : FeedItemViewBinder<RepoModel, RepoViewHolder>(
    RepoModel::class.java) {

    override fun createViewHolder(parent: ViewGroup): RepoViewHolder {
        return RepoViewHolder(
            LayoutInflater.from(parent.context).inflate(getFeedItemType(), parent, false),block)
    }

    override fun bindViewHolder(model: RepoModel, viewHolder: RepoViewHolder) {
        viewHolder.bind(model)
    }

    override fun getFeedItemType() = R.layout.adapter_repo

    override fun areContentsTheSame(oldItem: RepoModel, newItem: RepoModel) = oldItem == newItem

    override fun areItemsTheSame(oldItem: RepoModel, newItem: RepoModel) : Boolean {
        return oldItem.id == newItem.id
    }
}


class RepoViewHolder(val view : View, val block : (data: RepoModel) -> Unit)
    : RecyclerView.ViewHolder(view) {

    fun bind(data: RepoModel) {

        itemView.setOnClickListener {
            block(data)
        }

        itemView.apply {
            Glide
                .with(itemView.context)
                .load(data.avatarUrl)
                .circleCrop()
                .into(imageViewProfileImg)
            textViewLogin.text = data.login
            textViewRepoName.text = data.repoName
            textViewRepoDesc.text = data.repoDesc
            textViewStars.text = if(data.starCount != -1) data.starCount.toString() else "n/a"
            textViewPrimaryLanguage.text = data.primaryLanguage

        }
    }
}