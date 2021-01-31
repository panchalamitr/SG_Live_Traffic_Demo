package com.panchalamitr.sglivetraffic

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.panchalamitr.sglivetraffic.view.MapsActivity
import org.hamcrest.CoreMatchers
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@LargeTest
class MapsActivityTest {

    @get:Rule
    var mapsActivityTestRule: ActivityScenarioRule<MapsActivity>
            = ActivityScenarioRule(MapsActivity::class.java)


    @Test
    fun changeText_sameActivity() {
        onView(withId(R.id.mapFragment)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        onView(withId(R.id.progressBar)).check(ViewAssertions.matches(CoreMatchers.not(ViewMatchers.isDisplayed())))
    }
}