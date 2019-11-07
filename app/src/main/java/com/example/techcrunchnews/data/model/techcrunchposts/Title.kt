package com.example.techcrunchnews.data.model.techcrunchposts


import com.google.gson.annotations.SerializedName

data class Title(
    @SerializedName("rendered")
    val rendered: String
)