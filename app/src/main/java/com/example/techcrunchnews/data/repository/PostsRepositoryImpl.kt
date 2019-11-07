package com.mycakes.data.repository

import com.example.techcrunchnews.data.model.techcrunchposts.TechCrunchPosts
import com.example.techcrunchnews.data.remote.WebServices
import com.example.techcrunchnews.data.repository.PostsRepository
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

open class PostsRepositoryImpl(private val webServices: WebServices) : PostsRepository {
    override fun fetchPosts(): Single<List<TechCrunchPosts>> {
            return webServices.fetchPostsWebService()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}