package com.example.techcrunchnews.viewmodel

import android.app.Activity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.techcrunchnews.data.model.techcrunchposts.TechCrunchPosts
import com.example.techcrunchnews.data.repository.PostsRepository
import com.example.techcrunchnews.ui.activity.MainActivity
import io.reactivex.disposables.CompositeDisposable
import java.net.UnknownHostException
import java.util.*

class MainViewModel constructor(private val postsRepository: PostsRepository) : ViewModel() {
    private val disposable = CompositeDisposable()

    fun fetchPosts() {
        loadingState.value = LoadingState.LOADING
        disposable.add(
            postsRepository.fetchPosts()

                .subscribe({
                    lastFetchedTime = Date()
                    if (it.isEmpty()) {
                        errorMessage.value = "No Post Found"
                        loadingState.value = LoadingState.ERROR
                    } else {
                        posts.value = it
                        loadingState.value = LoadingState.SUCCESS
                    }
                }, {
                    lastFetchedTime = Date()

                    it.printStackTrace()
                    when (it) {
                        is UnknownHostException -> errorMessage.value = "No Network"
                        else -> errorMessage.value = it.localizedMessage
                    }

                    loadingState.value = LoadingState.ERROR
                })
        )

    }

    var lastFetchedTime: Date? = null

    val posts: MutableLiveData<List<TechCrunchPosts>> = MutableLiveData()

    val errorMessage: MutableLiveData<String> = MutableLiveData()


    val loadingState = MutableLiveData<LoadingState>()

    enum class LoadingState {
        LOADING,
        SUCCESS,
        ERROR
    }

    override fun onCleared() {
        disposable.dispose()
        super.onCleared()
    }

    fun getActivity(): Class<out Activity>{
        return MainActivity::class.java
    }
}