package com.theapache64.topcorn.ui.activities.feed

import android.app.Activity
import android.app.Instrumentation
import android.content.Context
import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.Intents.intending
import androidx.test.espresso.intent.matcher.IntentMatchers.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.theapache64.topcorn.App
import com.theapache64.topcorn.R
import com.theapache64.topcorn.data.remote.Movie
import com.theapache64.topcorn.data.repositories.movies.MoviesRepo
import com.theapache64.topcorn.di.components.AppComponent
import com.theapache64.topcorn.di.components.DaggerAppComponent
import com.theapache64.topcorn.di.modules.RepoModule
import com.theapache64.topcorn.ui.activities.movie.MovieActivity
import com.theapache64.topcorn.utils.test.IdlingRule
import com.theapache64.topcorn.utils.test.monitorActivity
import com.theapache64.twinkill.di.modules.ContextModule
import com.theapache64.twinkill.network.di.modules.BaseNetworkModule
import com.theapache64.twinkill.network.utils.Resource
import it.cosenonjaviste.daggermock.DaggerMock
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers.allOf
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import kotlin.time.ExperimentalTime


@ExperimentalCoroutinesApi
@ExperimentalTime
@RunWith(AndroidJUnit4::class)
class FeedActivityInsMockTest {

    @get:Rule
    val idlingRule = IdlingRule()

    private val app: App get() = InstrumentationRegistry.getInstrumentation().targetContext.applicationContext as App

    @get:Rule
    val daggerRule = DaggerMock.rule<AppComponent>(RepoModule()) {
        set { component -> component.inject(app) }
        customizeBuilder<DaggerAppComponent.Builder> {
            it.contextModule(ContextModule(app))
                .baseNetworkModule(BaseNetworkModule(App.BASE_URL))
        }
    }

    private lateinit var ac: ActivityScenario<FeedActivity>

    private val fakeMovie = Movie(
        listOf("Actor 1, Actor 2, Actor 3"),
        "This is description",
        listOf("Director 1", "Director 2"),
        listOf("Genre 1"),
        "https://avatars1.githubusercontent.com/u/1135007?s=460&v=4",
        "https://avatars1.githubusercontent.com/u/1135007?s=460&v=4",
        "/title/tt8629748/",
        "The Fake Movie",
        8.5f,
        2020
    )

    private var fakeRepo: MoviesRepo = mock(MoviesRepo::class.java).apply {
        `when`(getTop250Movies()).thenReturn(
            flowOf(
                Resource.success(
                    listOf(
                        fakeMovie
                    )
                )
            )
        )
    }

    @ExperimentalCoroutinesApi
    @Test
    fun fakeRepoTest() = runBlockingTest {
        assertEquals("The Fake Movie", fakeRepo.getTop250Movies().first().data!!.first().name)
    }

    @ExperimentalCoroutinesApi
    @Before
    fun before() = runBlockingTest {

        // given : Creating input data with onHeartClicked
        val context = getApplicationContext<Context>()
        val feedIntent = FeedActivity.getStartIntent(context)
        ac = ActivityScenario.launch(feedIntent)
        idlingRule.dataBindingIdlingResource.monitorActivity(ac)
    }

    @After
    fun after() {
        ac.close()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun feed_onHeartClicked_goToGitHub() = runBlockingTest {
        Intents.init()
        val intentResult = Instrumentation.ActivityResult(Activity.RESULT_OK, Intent())
        val sendingIntent = allOf(
            hasAction(Intent.ACTION_VIEW),
            hasData(FeedActivity.GITHUB_URL)
        )
        intending(sendingIntent).respondWith(intentResult)
        onView(withId(R.id.ib_heart)).check(matches(isDisplayed())).perform(click())
        intended(sendingIntent)
        Intents.release()
    }


    @ExperimentalCoroutinesApi
    @Test
    fun feed_load_click() {

        // Checking view visibility
        onView(withText(R.string.app_name)).check(matches(isDisplayed()))
        onView(withId(R.id.ib_heart)).check(matches(isDisplayed()))
        onView(withId(R.id.ib_dark_mode)).check(matches(isDisplayed()))

        onView(withId(R.id.rv_movies))
            .check(matches(isDisplayed()))
            .check(rowCount(1))

        Intents.init()
        onView(withId(R.id.rv_movies)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                click()
            )
        );
        intended(
            allOf(
                hasComponent(MovieActivity::class.java.name),
                hasExtra(MovieActivity.KEY_MOVIE, fakeMovie)
            )
        )
        Intents.release()
    }

    @Suppress("SameParameterValue")
    private fun rowCount(count: Int): ViewAssertion {
        return ViewAssertion { view, _ ->
            view as RecyclerView
            assertEquals(count, view.adapter!!.itemCount)
        }
    }
}
