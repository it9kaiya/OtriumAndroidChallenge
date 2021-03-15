package com.example.otriumandroidchallenge.ui.user.adapter.viewholder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.otriumandroidchallenge.R
import com.example.otriumandroidchallenge.ui.user.adapter.FeedAdapter
import com.example.otriumandroidchallenge.ui.user.adapter.FeedItemBinder
import com.example.otriumandroidchallenge.ui.user.adapter.FeedItemClass
import com.example.otriumandroidchallenge.ui.user.adapter.FeedItemViewBinder
import com.example.otriumandroidchallenge.ui.user.adapter.models.RepoListModel
import com.example.otriumandroidchallenge.ui.user.adapter.models.RepoModel
import kotlinx.android.synthetic.main.adapter_repo_list.view.*

class RepoListViewBinder(val block : (data: RepoModel) -> Unit)
    : FeedItemViewBinder<RepoListModel, RepoListViewHolder>(
    RepoListModel::class.java) {

    override fun createViewHolder(parent: ViewGroup): RepoListViewHolder {
        return RepoListViewHolder(
            LayoutInflater.from(parent.context).inflate(getFeedItemType(), parent, false),block)
    }

    override fun bindViewHolder(model: RepoListModel, viewHolder: RepoListViewHolder) {
        viewHolder.bind(model)
    }

    override fun getFeedItemType() = R.layout.adapter_repo_list

    override fun areContentsTheSame(oldItem: RepoListModel, newItem: RepoListModel) = oldItem == newItem

    override fun areItemsTheSame(oldItem: RepoListModel, newItem: RepoListModel) : Boolean {
        return oldItem.id == newItem.id
    }
}


class RepoListViewHolder(val view : View, val block : (data: RepoModel) -> Unit)
    : RecyclerView.ViewHolder(view) {

    fun bind(data: RepoListModel) {

        var adapter : FeedAdapter? = null

        itemView.setOnClickListener {

        }

        itemView.apply {
            if (adapter == null) {
                val repoViewBinder = RepoViewBinder { repoModel : RepoModel ->
                    block(repoModel)}
                val viewBinders = mutableMapOf<FeedItemClass, FeedItemBinder>()
                @Suppress("UNCHECKED_CAST")
                viewBinders.put(
                    repoViewBinder.modelClass,
                    repoViewBinder as FeedItemBinder)
                adapter = FeedAdapter(viewBinders)
            }

            tv_horizontal_header?.text = data.title
            adapter_recycllerview?.apply {

                layoutManager = LinearLayoutManager(adapter_recycllerview?.context,
                    LinearLayoutManager.HORIZONTAL,false)
                if (adapter_recycllerview?.adapter == null) {
                    adapter_recycllerview?.adapter = adapter
                }
                (adapter_recycllerview?.adapter as FeedAdapter).submitList(
                    data.repos as List<Any>? ?: emptyList())
            }
        }
    }
}