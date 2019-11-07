package com.example.techcrunchnews.ui.adapter.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.techcrunchnews.data.model.techcrunchposts.TechCrunchPosts
import com.example.techcrunchnews.utils.loadImage
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.cardview_rv.view.*

class PostsViewHolder(item: View) : RecyclerView.ViewHolder(item) {
    fun bindItem(posts: TechCrunchPosts) {
        itemView.tvPostDate.text = posts.date
        itemView.tvPostTitle.text = posts.title.rendered
        Glide.with(itemView.imgPost).load(posts.jetpackFeaturedMediaUrl).into(itemView.imgPost)
    //   itemView.imgPost.loadImage(posts.jetpackFeaturedMediaUrl)
    }
}