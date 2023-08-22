package com.jennisung.taskmaster;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

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

@LargeTest
@RunWith(AndroidJUnit4.class)
class TaskMasterExpressoTest {

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void UIElementsDisplayedTest() {
        ViewInteraction addTaskButton = onView(
                allOf(withId(R.id.MainActivityAddTaskButton), isDisplayed()));
        addTaskButton.check(matches(isDisplayed()));

        ViewInteraction allTasksButton = onView(
                allOf(withId(R.id.MainActivityAllTasksButton), isDisplayed()));
        allTasksButton.check(matches(isDisplayed()));

        ViewInteraction settingsButton = onView(
                allOf(withId(R.id.MainActivitySettingsButton), isDisplayed()));
        settingsButton.check(matches(isDisplayed()));

        ViewInteraction usernameTextView = onView(
                allOf(withId(R.id.usernameTasksTextView), isDisplayed()));
        usernameTextView.check(matches(isDisplayed()));
    }

    @Test
    public void taskDetailTest() {
        ViewInteraction taskRecyclerView = onView(
                allOf(withId(R.id.MainActivityTaskRecyclerView), isDisplayed()));
        taskRecyclerView.perform(click());

        ViewInteraction taskDetailHeading = onView(
                allOf(withId(R.id.TaskDetailPageHeading), isDisplayed()));
        taskDetailHeading.check(matches(isDisplayed()));

        ViewInteraction taskDetailTexts = onView(
                allOf(withId(R.id.TaskDetailPageTexts), isDisplayed()));
        taskDetailTexts.check(matches(isDisplayed()));
    }

    @Test
    public void editUsernameAndDisplayedTest() {
        ViewInteraction settingsButton = onView(
                allOf(withId(R.id.MainActivitySettingsButton), isDisplayed()));
        settingsButton.perform(click());

        ViewInteraction usernameInputForm = onView(
                allOf(withId(R.id.SettingsUsernameInputForm), isDisplayed()));
        usernameInputForm.perform(replaceText("NewUsername"));

        ViewInteraction addButton = onView(
                allOf(withId(R.id.SettingsAddButton), isDisplayed()));
        addButton.perform(click());

        ViewInteraction allTasksButton = onView(
                allOf(withId(R.id.MainActivityAllTasksButton), isDisplayed()));
        allTasksButton.perform(click());

        ViewInteraction usernameTextView = onView(
                allOf(withId(R.id.usernameTasksTextView), isDisplayed()));
        usernameTextView.check(matches(withText("NewUsername's Tasks")));
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

