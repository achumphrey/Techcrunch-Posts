package com.example.techcrunchnews.data.repository

import com.example.techcrunchnews.data.model.techcrunchposts.TechCrunchPosts
import io.reactivex.Single

interface PostsRepository {
    fun fetchPosts(): Single<List<TechCrunchPosts>>
}