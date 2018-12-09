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

@RunWith(AndroidJUnit4.class)
public class RegistrationControllerTest {

    @Rule
    public ActivityTestRule<RegistrationController> mActivityTestRule = new ActivityTestRule<>(RegistrationController.class);

    @Test
    public void registrationControllerInvalidEmailTest() {

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.name),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.support.design.widget.TextInputLayout")),
                                        0),
                                0)));
        appCompatEditText.perform(scrollTo(), replaceText("ayy"), closeSoftKeyboard());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.name), withText("ayy"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.support.design.widget.TextInputLayout")),
                                        0),
                                0)));
        appCompatEditText2.perform(scrollTo(), replaceText("ayylmao"));

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.name), withText("ayylmao"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.support.design.widget.TextInputLayout")),
                                        0),
                                0),
                        isDisplayed()));
        appCompatEditText3.perform(closeSoftKeyboard());

        ViewInteraction appCompatEditText4 = onView(
                allOf(withId(R.id.email),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.support.design.widget.TextInputLayout")),
                                        0),
                                0)));
        appCompatEditText4.perform(scrollTo(), replaceText("ayylmao"), closeSoftKeyboard());

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.authenticate_button), withText("Register"),
                        childAtPosition(
                                allOf(withId(R.id.email_authentication_form),
                                        childAtPosition(
                                                withId(R.id.authentication_form),
                                                0)),
                                4)));
        appCompatButton2.perform(scrollTo(), click());

        ViewInteraction editText = onView(
                allOf(withId(R.id.email), withText("ayylmao"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                        0),
                                0),
                        isDisplayed()));
        editText.check(matches(withText("ayylmao")));

    }

    @Test
    public void registrationControllerInvalidPasswordTest() {

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.name),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.support.design.widget.TextInputLayout")),
                                        0),
                                0)));
        appCompatEditText.perform(scrollTo(), replaceText("ayylmao"), closeSoftKeyboard());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.email),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.support.design.widget.TextInputLayout")),
                                        0),
                                0)));
        appCompatEditText2.perform(scrollTo(), replaceText("ayylmao@"), closeSoftKeyboard());

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.email), withText("ayylmao@"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.support.design.widget.TextInputLayout")),
                                        0),
                                0)));
        appCompatEditText3.perform(scrollTo(), replaceText("ayylmao@aayylmao.com"));

        ViewInteraction appCompatEditText4 = onView(
                allOf(withId(R.id.email), withText("ayylmao@aayylmao.com"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.support.design.widget.TextInputLayout")),
                                        0),
                                0),
                        isDisplayed()));
        appCompatEditText4.perform(closeSoftKeyboard());

        ViewInteraction appCompatEditText5 = onView(
                allOf(withId(R.id.password),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.support.design.widget.TextInputLayout")),
                                        0),
                                0)));
        appCompatEditText5.perform(scrollTo(), replaceText("ll22"), closeSoftKeyboard());

        ViewInteraction appCompatEditText6 = onView(
                allOf(withId(R.id.confirmPassword),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.support.design.widget.TextInputLayout")),
                                        0),
                                0)));
        appCompatEditText6.perform(scrollTo(), replaceText("ll22"), closeSoftKeyboard());

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.authenticate_button), withText("Register"),
                        childAtPosition(
                                allOf(withId(R.id.email_authentication_form),
                                        childAtPosition(
                                                withId(R.id.authentication_form),
                                                0)),
                                4)));
        appCompatButton2.perform(scrollTo(), click());


        ViewInteraction appCompatEditText7 = onView(
                allOf(withId(R.id.password), withText("ll22"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.support.design.widget.TextInputLayout")),
                                        0),
                                0)));
        appCompatEditText7.check(matches(withText("ll22")));


    }

    @Test
    public void registrationControllerDifferentPasswordsTest() {


        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.name),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.support.design.widget.TextInputLayout")),
                                        0),
                                0)));
        appCompatEditText.perform(scrollTo(), replaceText("ayylmao"), closeSoftKeyboard());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.email),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.support.design.widget.TextInputLayout")),
                                        0),
                                0)));
        appCompatEditText2.perform(scrollTo(), replaceText("ayylmao"), closeSoftKeyboard());


        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.email), withText("ayylmao"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.support.design.widget.TextInputLayout")),
                                        0),
                                0)));
        appCompatEditText3.perform(scrollTo(), replaceText("ayylmao@ayylmao.com"));

        ViewInteraction appCompatEditText4 = onView(
                allOf(withId(R.id.email), withText("ayylmao@ayylmao.com"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.support.design.widget.TextInputLayout")),
                                        0),
                                0),
                        isDisplayed()));
        appCompatEditText4.perform(closeSoftKeyboard());

        ViewInteraction appCompatEditText5 = onView(
                allOf(withId(R.id.password),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.support.design.widget.TextInputLayout")),
                                        0),
                                0)));
        appCompatEditText5.perform(scrollTo(), replaceText("ll1234"), closeSoftKeyboard());

        ViewInteraction appCompatEditText6 = onView(
                allOf(withId(R.id.confirmPassword),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.support.design.widget.TextInputLayout")),
                                        0),
                                0)));
        appCompatEditText6.perform(scrollTo(), replaceText("ll12345"), closeSoftKeyboard());


        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.authenticate_button), withText("Register"),
                        childAtPosition(
                                allOf(withId(R.id.email_authentication_form),
                                        childAtPosition(
                                                withId(R.id.authentication_form),
                                                0)),
                                4)));
        appCompatButton2.perform(scrollTo(), click());

        ViewInteraction editText = onView(
                allOf(withId(R.id.confirmPassword), withText("ll12345"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                        0),
                                0),
                        isDisplayed()));
        editText.check(matches(withText("ll12345")));

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
