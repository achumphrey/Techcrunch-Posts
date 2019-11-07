package com.example.techcrunchnews.data.model.AuthorDetails


import com.google.gson.annotations.SerializedName

data class AuthorNameModel(

    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String
)