package com.jennisung.taskmaster;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import com.jennisung.taskmaster.activities.SettingsActivity;
import org.junit.Rule;
import org.junit.Test;

public class SettingsActivityTest {

    @Rule
    public ActivityScenarioRule<SettingsActivity> activityScenarioRule = new ActivityScenarioRule<>(SettingsActivity.class);

    @Test
    public void checkUserNameTest() {
        Espresso.onView(ViewMatchers.withId(R.id.SettingsUsernameInputForm))
                .check(ViewAssertions.matches(ViewMatchers.withText("TEST Setting: Username")));
    }

    @Test
    public void saveButtonTest() {
        Espresso.onView(ViewMatchers.withId(R.id.SettingsUsernameInputForm))
                .perform(ViewActions.clearText());

        Espresso.onView(ViewMatchers.withId(R.id.SettingsUsernameInputForm))
                .perform(ViewActions.typeText("TEST Setting:NewUsername"));

        Espresso.onView(ViewMatchers.withId(R.id.SettingsAddButton))
                .perform(ViewActions.click());

        Espresso.onView(ViewMatchers.withId(R.id.SettingsUsernameInputForm))
                .check(ViewAssertions.matches(ViewMatchers.withText("Test Setting: NewUsername")));
    }
}

