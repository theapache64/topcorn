package com.theapache64.topcorn.ui.activities.feed

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.theapache64.topcorn.data.remote.Movie
import com.theapache64.topcorn.data.repositories.movies.MoviesRepo
import com.theapache64.topcorn.utils.test.MainCoroutineRule
import com.theapache64.topcorn.utils.test.getOrAwaitValue
import com.theapache64.twinkill.network.utils.Resource
import com.winterbe.expekt.should
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import kotlin.time.ExperimentalTime


@ExperimentalCoroutinesApi
@ExperimentalTime
class FeedViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @get:Rule
    val coroutinesRule = MainCoroutineRule()

    @Test
    fun `Check convertToFeed algorithm`() {
        val fakeMovie = mock(Movie::class.java).apply {
            `when`(genre).thenReturn(listOf("Genre 1", "Genre 2"))
        }

        val movies = listOf<Movie>(fakeMovie)
        val feedItems = FeedViewModel.convertToFeed(movies, FeedViewModel.SORT_ORDER_YEAR)

        feedItems.size.should.equal(2)
        feedItems.forEach { it.movies.size.should.equal(1) }
    }


    @Mock
    val fakeRepo: MoviesRepo = mock(MoviesRepo::class.java)

    @Test
    fun `Check local data flow`() {

        // Mocking data
        val fakeMovie = mock(Movie::class.java).apply {
            `when`(genre).thenReturn(listOf("Genre 1", "Genre 2", "Genre 3"))
        }
        val fakeMovie2 = mock(Movie::class.java).apply {
            `when`(genre).thenReturn(listOf("Genre 3", "Genre 2", "Genre 4"))
        }
        val fakeMovies = listOf(fakeMovie, fakeMovie2)

        `when`(fakeRepo.getTop250Movies()).thenReturn(flow {
            emit(Resource.loading<List<Movie>>()) // loading first
            emit(Resource.success(fakeMovies))
        })

        val viewModel = FeedViewModel(fakeRepo)

        val firstData = viewModel.movies.getOrAwaitValue()
        firstData.status.should.equal(Resource.Status.LOADING)

        val secondData = viewModel.movies.getOrAwaitValue()
        secondData.status.should.equal(Resource.Status.SUCCESS)
        val numberOfDistinctGenre = fakeMovies
            .flatMap { it.genre }
            .toSet()
            .size
        secondData.data.should.size(numberOfDistinctGenre)
    }
}