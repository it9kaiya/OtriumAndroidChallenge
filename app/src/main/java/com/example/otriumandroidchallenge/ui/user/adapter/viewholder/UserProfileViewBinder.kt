package com.example.otriumandroidchallenge.ui.user.adapter.viewholder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.otriumandroidchallenge.R
import com.example.otriumandroidchallenge.ui.user.adapter.FeedItemViewBinder
import com.example.otriumandroidchallenge.ui.user.adapter.models.UserProfileModel
import kotlinx.android.synthetic.main.adapter_user_profile.view.*

class UserProfileViewBinder(val block : (data: UserProfileModel) -> Unit) : FeedItemViewBinder<UserProfileModel, UserProfileViewHolder>(
    UserProfileModel::class.java) {

    override fun createViewHolder(parent: ViewGroup): UserProfileViewHolder {
        return UserProfileViewHolder(
            LayoutInflater.from(parent.context).inflate(getFeedItemType(), parent, false),block)
    }

    override fun bindViewHolder(model: UserProfileModel, viewHolder: UserProfileViewHolder) {
        viewHolder.bind(model)
    }

    override fun getFeedItemType() = R.layout.adapter_user_profile

    override fun areContentsTheSame(oldItem: UserProfileModel, newItem: UserProfileModel) = oldItem == newItem

    override fun areItemsTheSame(oldItem: UserProfileModel, newItem: UserProfileModel) : Boolean {
        return oldItem.email == newItem.email
    }
}


class UserProfileViewHolder(val view : View, val block : (data: UserProfileModel) -> Unit)
    : RecyclerView.ViewHolder(view) {

    fun bind(data: UserProfileModel) {

        itemView.setOnClickListener {
            block(data)
        }

        itemView.apply {
            Glide
                .with(itemView.context)
                .load(data.avatarUrl)
                .circleCrop()
                .into(imageViewProfile)

            textViewName.text = data.name
            textViewLogin.text = data.login
            textViewEmail.text = data.email
            textViewFollowers.text = String.format(context.getText(R.string.followers) as String, data.followers)
            textViewFollowing.text = String.format(context.getText(R.string.following) as String, data.following)
        }
    }
}