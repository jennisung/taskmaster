package com.jennisung.taskmaster;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class UserNameTest {

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityRule = new ActivityScenarioRule<MainActivity>(MainActivity.class);

    @Test
    public void editUsernameTest() {
        ViewInteraction settingsButton = onView(allOf(withId(R.id.MainActivitySettingsButton), isDisplayed()));
        settingsButton.perform(click());

        String newUsername = "NewUsername";


        ViewInteraction usernameInputForm = onView(allOf(withId(R.id.SettingsUsernameInputForm), isDisplayed()));
        usernameInputForm.perform(replaceText(newUsername));

        ViewInteraction addButton = onView(allOf(withId(R.id.SettingsAddButton), isDisplayed()));
        addButton.perform(click());

        Espresso.pressBack();

        ViewInteraction usernameTextView = onView(allOf(withId(R.id.usernameTasksTextView), isDisplayed()));
        usernameTextView.check(matches(withText(newUsername + "Tasks")));
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
