package com.theapache64.topcorn.utils.test

import android.view.View
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.matcher.ViewMatchers.isRoot
import org.hamcrest.Matcher

/**
 * To pause the execution for the given time.
 */
fun waitFor(delay: Long): ViewAction? {

    return object : ViewAction {
        override fun getConstraints(): Matcher<View> {
            return isRoot()
        }

        override fun getDescription(): String {
            return "wait for " + delay + "milliseconds"
        }

        override fun perform(uiController: UiController, view: View?) {
            uiController.loopMainThreadForAtLeast(delay)
        }
    }
}