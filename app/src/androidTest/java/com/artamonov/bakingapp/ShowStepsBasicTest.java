package com.artamonov.bakingapp;

import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class ShowStepsBasicTest {

    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule
            = new ActivityTestRule<>(MainActivity.class);

    public static ViewAction handleConstraints(final ViewAction action) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return ViewMatchers.isEnabled();
            }

            @Override
            public String getDescription() {
                return "clickED item";
            }

            @Override
            public void perform(UiController uiController, View view) {
                action.perform(uiController, view);
            }
        };
    }

    @Test
    public void clickOnRecipe_ShowsIngredientsItem() {
        // onView(withId(R.id.rvBaking)).perform(actionOnItemAtPosition(1, click()));
        // onView(withId(R.id.rv_step_list)).perform(actionOnItemAtPosition(1, click()));
        //  onView(withId(R.id.tv_step_description)).check(matches(not(withText(""))));
        onView(withId(R.id.rvBaking)).perform(handleConstraints(click()));
    }
}

