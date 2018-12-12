package net.raeesaamir.coinz.about;

import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import net.raeesaamir.coinz.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

/**
 * An instrumented test for the about section
 *
 * @author raeesaamir
 */
@RunWith(AndroidJUnit4.class)
public class AboutControllerTest {

    /**
     * The controller for which we want to test
     */
    @Rule
    public ActivityTestRule<AboutController> mActivityTestRule = new ActivityTestRule<>(AboutController.class);

    /**
     * Makes sure the correct text appears in the about section.
     */
    @Test
    public void aboutControllerTest() {

        ViewInteraction textView = onView(
                allOf(withId(R.id.aboutTextFragment), withText("Designed and Developed by Raees Aamir @ www.raeesaamir.net.\n\nCoursework specification and GeoJSON data provided by Stephen Gilmore. \n\nCoinz is an assessed piece of coursework for the BSc (Hons) Computer Science's Informatics Large Practical(INFR09051) class."),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.fragment_container),
                                        0),
                                1),
                        isDisplayed()));
        textView.check(matches(withText(AboutFragment.ABOUT_TEXT)));

    }

    /**
     * Looks at the view tree and returns the child matcher.
     *
     * @param parentMatcher - The matcher for the parent tree.
     * @param position      The position.
     * @return A hamcrest matcher object representing the child tree.
     */
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
