package com.example.techcrunchnews.data.model.techcrunchposts


import com.google.gson.annotations.SerializedName

data class TechCrunchPosts(

    @SerializedName("author")
    val author: Int,

    @SerializedName("date")
    val date: String,

    @SerializedName("jetpack_featured_media_url")
    val jetpackFeaturedMediaUrl: String,

    @SerializedName("title")
    val title: Title
)