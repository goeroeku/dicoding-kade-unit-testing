package ic.aiczone.cifbmatchapps

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.Espresso.pressBack
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import ic.aiczone.cifbmatchapps.R.id.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    @Rule
    @JvmField
    var mActivityRule: ActivityTestRule<MainActivity> = object : ActivityTestRule<MainActivity>(MainActivity::class.java) {}

    @Test
    fun testMainActivity() {

        // check recycler view
        onView(withId(R.id.rv))
                .check(matches(isDisplayed()))

        Thread.sleep(3000)

        // click 1st card
        onView(withId(R.id.rv)).perform(
                actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click())
        )

        Thread.sleep(3000)

        // check favorite button
        onView(withId(R.id.favorite_add))
                .check(matches(isDisplayed()))

        // click favorite button
        onView(withId(R.id.favorite_add))
                .perform(click())

        onView(withText("Added to favorite"))
                .check(matches(isDisplayed()))

        Thread.sleep(3000)

        // click back button
        pressBack()

        // check favorite navigation on bottom
        onView(withId(nav_favorite))
                .check(matches(isDisplayed()))
        // click favorite navigation
        onView(withId(nav_favorite))
                .perform(click())

        // click 1st card
        onView(withId(R.id.rv)).perform(
                actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click())
        )

        Thread.sleep(3000)

        // check favorite button
        onView(withId(R.id.favorite_add))
                .check(matches(isDisplayed()))

        // click favorite button
        onView(withId(R.id.favorite_add))
                .perform(click())

        onView(withText("Removed from favorite"))
                .check(matches(isDisplayed()))

        Thread.sleep(3000)

        // click back button
        pressBack()

        // check next navigation
        onView(withId(nav_next))
                .check(matches(isDisplayed()))
        // click next navigation
        onView(withId(nav_next))
                .perform(click())

        Thread.sleep(3000)
    }
}