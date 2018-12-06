package net.raeesaamir.coinz.splash;

import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.google.common.collect.ImmutableSet;
import com.google.firebase.auth.FirebaseAuth;

import net.raeesaamir.coinz.R;
import net.raeesaamir.coinz.menu.MenuItem;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@RunWith(AndroidJUnit4.class)
public class SplashScreenControllerTest {

    @Rule
    public ActivityTestRule<SplashScreenController> mActivityTestRule = new ActivityTestRule<>(SplashScreenController.class);

    private FirebaseAuth mAuth;
    private ImmutableSet<MenuItem> menuItemSet;

    @Before
    public void beforeSplashScreenControllerTest() {
        mAuth = FirebaseAuth.getInstance();

        if(mAuth.getCurrentUser() != null) {
            MenuItem[] items = MenuItem.values();
            menuItemSet = ImmutableSet.copyOf(items);
        }
    }

    @Test
    public void splashScreenControllerTest() {

        try {
            Thread.sleep(SplashScreenController.SPLASH_SCREEN_DURATION + 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if(mAuth.getCurrentUser() != null) {

            menuItemSet.forEach(menuItem -> {
                ViewInteraction checkedTextView = onView(
                        allOf(withId(android.R.id.text1),
                                childAtPosition(
                                        allOf(withId(R.id.listView),
                                                childAtPosition(
                                                        IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                                        3)),
                                        menuItem.ordinal()),
                                isDisplayed()));
                checkedTextView.check(matches(isDisplayed()));
            });

        } else {
            ViewInteraction email = onView(
                    allOf(withId(R.id.email),
                            childAtPosition(
                                    childAtPosition(
                                            withId(R.id.email_input),
                                            0),
                                    0)));
            email.check(matches(withText("")));

        }
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
