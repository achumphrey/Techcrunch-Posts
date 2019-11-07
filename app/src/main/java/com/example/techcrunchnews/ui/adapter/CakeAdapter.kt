package com.example.techcrunchnews.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.techcrunchnews.R
import com.example.techcrunchnews.data.model.techcrunchposts.TechCrunchPosts
import com.example.techcrunchnews.ui.adapter.viewholder.PostsViewHolder
import com.example.techcrunchnews.utils.inflate
import com.mycakes.ui.adapter.listener.PostsClickListener

class PostsAdapter constructor(val posts: MutableList<TechCrunchPosts>, private val postsClickListener: PostsClickListener) :
    RecyclerView.Adapter<PostsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostsViewHolder{

        val view: View = parent.inflate(R.layout.cardview_rv, false)
        return PostsViewHolder(view)
    }

    override fun getItemCount() = posts.size

    override fun onBindViewHolder(holder: PostsViewHolder, position: Int) {
        holder.bindItem(posts[position])
        holder.itemView.setOnClickListener {
            postsClickListener.onClick(posts[holder.adapterPosition])
        }
    }
}
