package com.jennisung.taskmaster;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import org.junit.Rule;
import org.junit.Test;

public class MainActivityTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void displayingUIElementsTest() {
        Espresso.onView(ViewMatchers.withId(R.id.usernameTasksTextView)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withId(R.id.MainActivityAddTaskButton)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withId(R.id.MainActivityAllTasksButton)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void taskDescriptionTest() {Espresso.onView(ViewMatchers.withText("TEST: Task Name")).perform(ViewActions.click());

        Espresso.onView(ViewMatchers.withId(R.id.TaskDetailPageHeading))
                .check(ViewAssertions.matches(ViewMatchers.withText("TEST: Task Name Test")));
    }

    @Test
    public void editUsernameTest() {
        Espresso.onView(ViewMatchers.withId(R.id.MainActivitySettingsButton)).perform(ViewActions.click());

        Espresso.onView(ViewMatchers.withId(R.id.SettingsUsernameInputForm))
                .perform(ViewActions.clearText(), ViewActions.typeText("TEST:NewUsername"));

        Espresso.onView(ViewMatchers.withId(R.id.SettingsAddButton)).perform(ViewActions.click());

        Espresso.pressBack();

        Espresso.onView(ViewMatchers.withId(R.id.usernameTasksTextView))
                .check(ViewAssertions.matches(ViewMatchers.withText("newUsername's Task(MainActivity heading)")));
    }
}


