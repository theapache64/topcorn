package com.theapache64.topcorn.ui.activities.movie

import android.app.Activity
import android.app.Instrumentation
import android.content.Intent
import android.net.Uri
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.Intents.intending
import androidx.test.espresso.intent.matcher.IntentMatchers.hasAction
import androidx.test.espresso.intent.matcher.IntentMatchers.hasData
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.theapache64.topcorn.R
import com.theapache64.topcorn.data.remote.Movie
import com.theapache64.topcorn.utils.test.IdlingRule
import com.theapache64.topcorn.utils.test.monitorActivity
import org.hamcrest.CoreMatchers.allOf
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class MovieActivityTest {

    private lateinit var ac: ActivityScenario<MovieActivity>

    @get:Rule
    val idlingRule = IdlingRule()

    private val fakeMovie = Movie(
        listOf("Actor 1, Actor 2, Actor 3"),
        "This is description",
        listOf("Director 1", "Director 2"),
        listOf("Genre 1", "Genre 2"),
        "https://avatars1.githubusercontent.com/u/1135007?s=460&v=4",
        "https://avatars1.githubusercontent.com/u/1135007?s=460&v=4",
        "/title/tt8629748/",
        "The Fake Movie",
        8.5f,
        2020
    )

    @Before
    fun before() {

        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val movieIntent = MovieActivity.getStartIntent(context, fakeMovie)
        this.ac = ActivityScenario.launch<MovieActivity>(movieIntent)
        idlingRule.dataBindingIdlingResource.monitorActivity(ac)
        Intents.init()
    }

    @Test
    fun movie_shows_success() {
        // Checking actors are displayed
        onView(withId(R.id.tv_rating)).check(matches(withText(fakeMovie.rating.toString())))
        onView(withId(R.id.tv_director)).check(matches(withText(fakeMovie.directors.joinToString(","))))
        onView(withId(R.id.tv_actor)).check(matches(withText(fakeMovie.actors.joinToString(","))))
        onView(withId(R.id.tv_genre)).check(matches(withText(fakeMovie.genre.joinToString(","))))
        onView(withId(R.id.tv_title)).check(matches(withText(fakeMovie.name)))
        onView(withId(R.id.tv_description)).check(matches(withText(fakeMovie.desc)))
    }

    @Test
    fun movie_imdbButtonClicked_browserOpen() {

        val imdbIntent = allOf(
            hasAction(Intent.ACTION_VIEW),
            hasData(Uri.parse("${MovieActivity.IMDB_DOT_COM}${fakeMovie.imdbUrl}"))
        )

        intending(imdbIntent).respondWith(
            Instrumentation.ActivityResult(
                Activity.RESULT_OK,
                Intent()
            )
        )

        onView(withId(R.id.b_movie_open_imdb)).perform(click())
        intended(imdbIntent)
    }

    @After
    fun after() {
        Intents.release()
        ac.close()
    }
}