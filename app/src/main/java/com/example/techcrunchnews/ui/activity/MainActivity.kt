package com.example.techcrunchnews.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.techcrunchnews.R
import com.example.techcrunchnews.data.model.techcrunchposts.TechCrunchPosts
import com.example.techcrunchnews.data.remote.WebServices
import com.example.techcrunchnews.ui.adapter.PostsAdapter
import com.example.techcrunchnews.ui.dialog.PostsDetailDialog
import com.example.techcrunchnews.viewmodel.MainViewModel
import com.example.techcrunchnews.viewmodel.factory.MainViewModelFactory
import com.mycakes.data.repository.PostsRepositoryImpl
import com.mycakes.ui.adapter.listener.PostsClickListener
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: MainViewModel
    lateinit var postsAdapter: PostsAdapter
    private val postsClickListener: PostsClickListener = object : PostsClickListener {
        override fun onClick(posts: TechCrunchPosts) {
            val postsDetailDialog = PostsDetailDialog.instance(posts)
            postsDetailDialog.show(supportFragmentManager,"POST_DETAIL")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupRecyclerView()

        viewModel = ViewModelProviders.of(this, MainViewModelFactory(PostsRepositoryImpl(WebServices.instance)))
            .get(MainViewModel::class.java)

        viewModel.posts.observe(this, Observer {
            postsAdapter.posts.clear()
            postsAdapter.posts.addAll(it)
            postsAdapter.notifyDataSetChanged()

        })

        viewModel.errorMessage.observe(this, Observer {
            tvMessage.text = it
        })

        viewModel.loadingState.observe(this, Observer {
            when (it) {
                MainViewModel.LoadingState.LOADING -> displayProgressbar()
                MainViewModel.LoadingState.SUCCESS -> displayList()
                MainViewModel.LoadingState.ERROR -> displayMessageContainer()
                else -> displayMessageContainer()
            }
        })
        if (viewModel.lastFetchedTime == null) {
            viewModel.fetchPosts()
        }

        btnRetry.setOnClickListener {
            viewModel.fetchPosts()
        }

    }

    private fun displayProgressbar() {
        progressbar.visibility = View.VISIBLE
        rvPosts.visibility = View.GONE
        llMessageContainer.visibility = View.GONE
    }

    private fun displayMessageContainer() {
        llMessageContainer.visibility = View.VISIBLE
        rvPosts.visibility = View.GONE
        progressbar.visibility = View.GONE
    }

    private fun displayList() {

        llMessageContainer.visibility = View.GONE
        rvPosts.visibility = View.VISIBLE
        progressbar.visibility = View.GONE
    }

    private fun setupRecyclerView() {
        rvPosts.layoutManager = LinearLayoutManager(this)
        postsAdapter = PostsAdapter(mutableListOf(), postsClickListener)
        rvPosts.adapter = postsAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.activity_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_refresh -> {
                viewModel.fetchPosts()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}

