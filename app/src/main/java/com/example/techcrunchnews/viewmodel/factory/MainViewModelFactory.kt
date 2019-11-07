package com.example.techcrunchnews.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.techcrunchnews.data.repository.PostsRepository
import com.example.techcrunchnews.viewmodel.MainViewModel

class MainViewModelFactory constructor(private val postsRepository: PostsRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(postsRepository) as T
    }

}