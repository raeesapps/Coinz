package net.raeesaamir.coinz.authentication;

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
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

/**
 * An instrumented test for the login controller section
 *
 * @author raeesaamir
 */
@RunWith(AndroidJUnit4.class)
public class LoginControllerTest {

    /**
     * The controller for which we want to test.
     */
    @Rule
    public ActivityTestRule<LoginController> mActivityTestRule = new ActivityTestRule<>(LoginController.class);

    /**
     * Checks if the login system works with an existing test account.
     */
    @Test
    public void loginControllerTest() {

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.email),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.email_input),
                                        0),
                                0)));
        appCompatEditText.perform(scrollTo(), replaceText("t"), closeSoftKeyboard());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.email), withText("t"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.email_input),
                                        0),
                                0)));
        appCompatEditText2.perform(scrollTo(), replaceText("test@test.com"));

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.email), withText("test@test.com"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.email_input),
                                        0),
                                0),
                        isDisplayed()));
        appCompatEditText3.perform(closeSoftKeyboard());

        ViewInteraction editText = onView(
                allOf(withId(R.id.email), withText("test@test.com"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.email_input),
                                        0),
                                0),
                        isDisplayed()));
        editText.check(matches(withText("test@test.com")));


        ViewInteraction appCompatEditText4 = onView(
                allOf(withId(R.id.password),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.support.design.widget.TextInputLayout")),
                                        0),
                                0)));
        appCompatEditText4.perform(scrollTo(), replaceText("test12"), closeSoftKeyboard());


        ViewInteraction appCompatEditText5 = onView(
                allOf(withId(R.id.password), withText("test12"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.support.design.widget.TextInputLayout")),
                                        0),
                                0)));
        appCompatEditText5.perform(scrollTo(), replaceText("test123"));

        ViewInteraction appCompatEditText6 = onView(
                allOf(withId(R.id.password), withText("test123"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.support.design.widget.TextInputLayout")),
                                        0),
                                0),
                        isDisplayed()));
        appCompatEditText6.check(matches(withText("test123")));

        //pressBack();

        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.authenticate_button), withText("Sign in"),
                        childAtPosition(
                                allOf(withId(R.id.email_authentication_form),
                                        childAtPosition(
                                                withId(R.id.authentication_form),
                                                0)),
                                2)));
        appCompatButton.perform(scrollTo(), click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html

        try {
            Thread.sleep(3496);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction textView = onView(
                allOf(withId(R.id.welcomeMessage), withText("Welcome \ntest!"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.fragment_container),
                                        0),
                                1),
                        isDisplayed()));
        textView.check(matches(withText("Welcome \ntest!")));

    }

    /**
     * Checks if the login system rejects an invalid email.
     */
    @Test
    public void loginControllerInvalidEmailTest() {

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.email),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.email_input),
                                        0),
                                0)));
        appCompatEditText.perform(scrollTo(), replaceText("lo"), closeSoftKeyboard());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.email), withText("lo"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.email_input),
                                        0),
                                0)));
        appCompatEditText2.perform(scrollTo(), replaceText("lol123"));

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.email), withText("lol123"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.email_input),
                                        0),
                                0),
                        isDisplayed()));
        appCompatEditText3.perform(closeSoftKeyboard());

        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.authenticate_button), withText("Sign in"),
                        childAtPosition(
                                allOf(withId(R.id.email_authentication_form),
                                        childAtPosition(
                                                withId(R.id.authentication_form),
                                                0)),
                                2)));
        appCompatButton.perform(scrollTo(), click());

        ViewInteraction editText = onView(
                allOf(withId(R.id.email), withText("lol123"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.email_input),
                                        0),
                                0),
                        isDisplayed()));
        editText.check(matches(withText("lol123")));

    }

    /**
     * Checks if the login system rejects an invalid password.
     */
    @Test
    public void loginControllerInvalidPasswordTest() {

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.email),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.email_input),
                                        0),
                                0)));
        appCompatEditText.perform(scrollTo(), replaceText("lol@"), closeSoftKeyboard());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.email), withText("lol@"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.email_input),
                                        0),
                                0)));
        appCompatEditText2.perform(scrollTo(), replaceText("lol@lol.com"));

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.email), withText("lol@lol.com"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.email_input),
                                        0),
                                0),
                        isDisplayed()));
        appCompatEditText3.perform(closeSoftKeyboard());

        ViewInteraction appCompatEditText4 = onView(
                allOf(withId(R.id.password),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.support.design.widget.TextInputLayout")),
                                        0),
                                0)));
        appCompatEditText4.perform(scrollTo(), replaceText("ll22"), closeSoftKeyboard());

        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.authenticate_button), withText("Sign in"),
                        childAtPosition(
                                allOf(withId(R.id.email_authentication_form),
                                        childAtPosition(
                                                withId(R.id.authentication_form),
                                                0)),
                                2)));
        appCompatButton.perform(scrollTo(), click());

        ViewInteraction editText = onView(
                allOf(withId(R.id.password), withText("ll22"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.instanceOf(android.widget.LinearLayout.class),
                                        0),
                                0),
                        isDisplayed()));
        editText.check(matches(withText("ll22")));

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
