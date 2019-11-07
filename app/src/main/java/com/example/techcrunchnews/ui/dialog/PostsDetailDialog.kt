package com.example.techcrunchnews.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.techcrunchnews.R
import com.example.techcrunchnews.data.model.techcrunchposts.TechCrunchPosts
import kotlinx.android.synthetic.main.dialog_post_detail.*

class PostsDetailDialog : DialogFragment() {
    companion object {
        const val ARG_CAKE = "arg_post"
        fun instance(posts: TechCrunchPosts): PostsDetailDialog {
            val cakeDetailDialog = PostsDetailDialog()
            val arguments = Bundle()
            arguments.putString(ARG_CAKE, posts.title.rendered)
            cakeDetailDialog.arguments = arguments
            return cakeDetailDialog
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_post_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val post = arguments?.getString(ARG_CAKE)
        post?.apply {
            tvPostDetail.text = post
        }
        btnOk.setOnClickListener { dismiss() }
    }
}