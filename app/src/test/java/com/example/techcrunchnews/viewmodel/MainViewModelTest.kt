package com.example.techcrunchnews.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.techcrunchnews.data.model.techcrunchposts.TechCrunchPosts
import com.example.techcrunchnews.data.model.techcrunchposts.Title
import com.example.techcrunchnews.ui.activity.MainActivity
import com.mycakes.data.repository.PostsRepositoryImpl
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.reactivex.Single
import org.junit.*

import org.junit.Assert.*
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.junit.runners.BlockJUnit4ClassRunner
import org.mockito.Mockito.mock
import java.net.UnknownHostException

@RunWith(BlockJUnit4ClassRunner::class)
class MainViewModelTest {


    @Rule
    @JvmField
    var rule: TestRule = InstantTaskExecutorRule()

    @MockK
    lateinit var postsRepository: PostsRepositoryImpl
    lateinit var mainViewModel: MainViewModel

    private val author = 123456
    private val date = "2019-11-05T04:03:25"
    private val jetpackFeaturedMediaUrl = "https://techcrunch.com/wp-content/uploads/2019/01/Drone-over-field.png"
    private val title = Title("Techcrunch news posts")


    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        mainViewModel = MainViewModel(postsRepository)
    }

    @Test
    fun fetchPosts_with_success() {
        val posts =
            mutableListOf(TechCrunchPosts(author, date, jetpackFeaturedMediaUrl,title))

        val expectedPosts =
            mutableListOf<TechCrunchPosts>(TechCrunchPosts(123456, "2019-11-05T04:03:25","https://techcrunch.com/wp-content/uploads/2019/01/Drone-over-field.png", title))
        every {postsRepository.fetchPosts()} returns (Single.just(posts))

        mainViewModel.fetchPosts()

        assertEquals(expectedPosts, mainViewModel.posts.value)
        assertEquals(MainViewModel.LoadingState.SUCCESS, mainViewModel.loadingState.value)
        assertEquals(null, mainViewModel.errorMessage.value)
    }

    @Test
    fun fetchPosts_without_success_Nothing_Returned() {
        val posts =
            mutableListOf<TechCrunchPosts>()
        every {postsRepository.fetchPosts()} returns (Single.just(posts))

        mainViewModel.fetchPosts()

        assertEquals(null, mainViewModel.posts.value)
        assertEquals(MainViewModel.LoadingState.ERROR, mainViewModel.loadingState.value)
        assertEquals("No Post Found", mainViewModel.errorMessage.value)
    }


    @Test
    fun fetchPosts_with_NetworkError() {
        every {postsRepository.fetchPosts()} returns (Single.error(UnknownHostException("Something Wrong")))

        mainViewModel.fetchPosts()

        assertEquals(null, mainViewModel.posts.value)
        assertEquals(MainViewModel.LoadingState.ERROR, mainViewModel.loadingState.value)
        assertEquals("No Network", mainViewModel.errorMessage.value)
    }

    @Test
    fun fetchPosts_otherError() {
        every {postsRepository.fetchPosts()} returns (Single.error(RuntimeException("Something Wrong")))

        mainViewModel.fetchPosts()

        assertEquals(null, mainViewModel.posts.value)
        assertEquals(MainViewModel.LoadingState.ERROR, mainViewModel.loadingState.value)
        assertEquals("Something Wrong", mainViewModel.errorMessage.value)
    }

    @Test
    fun getActivityTest(){
        val activityClass = mainViewModel.getActivity()
        assertTrue(activityClass == MainActivity::class.java)
    }

    @After
    fun tearDown() {
    }
}