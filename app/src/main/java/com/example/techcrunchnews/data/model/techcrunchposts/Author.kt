package com.example.techcrunchnews.data.model.techcrunchposts


import com.google.gson.annotations.SerializedName

data class Author(
    @SerializedName("embeddable")
    val embeddable: Boolean,
    @SerializedName("href")
    val href: String
)