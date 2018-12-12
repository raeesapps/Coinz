package net.raeesaamir.coinz.menu;

import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.google.common.collect.ImmutableSet;

import net.raeesaamir.coinz.R;

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
import static org.hamcrest.Matchers.allOf;

/**
 * An instrumented test for the menu system.
 *
 * @author raeesaamir
 */
@RunWith(AndroidJUnit4.class)
public class MenuControllerTest {

    /**
     * The controller which we want to test.
     */
    @Rule
    public ActivityTestRule<MenuController> mActivityTestRule = new ActivityTestRule<>(MenuController.class);

    /**
     * The items in the menu
     */
    private ImmutableSet<MenuItem> menuItemSet;

    /**
     * Adds the items into the menu before the test begins.
     */
    @Before
    public void beforeMenuControllerTest() {
        MenuItem[] items = MenuItem.values();
        menuItemSet = ImmutableSet.copyOf(items);
    }

    /**
     * Checks if each menu item is displayed in the menu.
     */
    @Test
    public void menuControllerTest() {

        menuItemSet.forEach(menuItem -> {
            ViewInteraction checkedTextView = onView(
                    allOf(withId(android.R.id.text1),
                            childAtPosition(
                                    allOf(withId(R.id.listView),
                                            childAtPosition(
                                                    IsInstanceOf.instanceOf(android.widget.LinearLayout.class),
                                                    3)),
                                    menuItem.ordinal()),
                            isDisplayed()));
            checkedTextView.check(matches(isDisplayed()));
        });

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
