package com.pbp_b_kelompok_1.ticketplease;


import androidx.test.espresso.DataInteraction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import static androidx.test.InstrumentationRegistry.getInstrumentation;
import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.assertion.ViewAssertions.*;
import static androidx.test.espresso.matcher.ViewMatchers.*;

import com.pbp_b_kelompok_1.ticketplease.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class LoginActivityTest2 {

    @Rule
    public ActivityTestRule<LoginActivity> mActivityTestRule = new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void loginActivityTest2() {
        ViewInteraction materialButton = onView(
                allOf(withId(R.id.btnLogin), withText("Login"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.layoutLogin),
                                        1),
                                2),
                        isDisplayed()));
        materialButton.perform(click());

        ViewInteraction textInputEditText = onView(
                allOf(childAtPosition(
                        childAtPosition(
                                withId(R.id.inputLoginUsername),
                                0),
                        0),
                        isDisplayed()));
        textInputEditText.perform(replaceText("chr2"), closeSoftKeyboard());

        ViewInteraction materialButton2 = onView(
                allOf(withId(R.id.btnLogin), withText("Login"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.layoutLogin),
                                        1),
                                2),
                        isDisplayed()));
        materialButton2.perform(click());

        ViewInteraction textInputEditText2 = onView(
                allOf(withText("chr2"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.inputLoginUsername),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText2.perform(replaceText("chr1"));

        ViewInteraction textInputEditText3 = onView(
                allOf(withText("chr1"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.inputLoginUsername),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText3.perform(closeSoftKeyboard());

        ViewInteraction materialButton3 = onView(
                allOf(withId(R.id.btnLogin), withText("Login"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.layoutLogin),
                                        1),
                                2),
                        isDisplayed()));
        materialButton3.perform(click());

        ViewInteraction textInputEditText4 = onView(
                allOf(childAtPosition(
                        childAtPosition(
                                withId(R.id.inputLoginPassword),
                                0),
                        0),
                        isDisplayed()));
        textInputEditText4.perform(replaceText("chr"), closeSoftKeyboard());

        ViewInteraction materialButton4 = onView(
                allOf(withId(R.id.btnLogin), withText("Login"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.layoutLogin),
                                        1),
                                2),
                        isDisplayed()));
        materialButton4.perform(click());

        ViewInteraction textInputEditText5 = onView(
                allOf(withText("chr"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.inputLoginPassword),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText5.perform(replaceText("chriii"));

        ViewInteraction textInputEditText6 = onView(
                allOf(withText("chriii"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.inputLoginPassword),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText6.perform(closeSoftKeyboard());

        ViewInteraction materialButton5 = onView(
                allOf(withId(R.id.btnLogin), withText("Login"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.layoutLogin),
                                        1),
                                2),
                        isDisplayed()));
        materialButton5.perform(click());

        ViewInteraction textInputEditText7 = onView(
                allOf(withText("chriii"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.inputLoginPassword),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText7.perform(replaceText("christian"));

        ViewInteraction textInputEditText8 = onView(
                allOf(withText("christian"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.inputLoginPassword),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText8.perform(closeSoftKeyboard());

        ViewInteraction materialButton6 = onView(
                allOf(withId(R.id.btnLogin), withText("Login"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.layoutLogin),
                                        1),
                                2),
                        isDisplayed()));
        materialButton6.perform(click());
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
