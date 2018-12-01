package net.raeesaamir.coinz.authentication;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import net.raeesaamir.coinz.R;

/**
 * An authentication controller that can be used to implement a login or registration form.
 */
public abstract class AuthenticationController extends AppCompatActivity {

    /**
     * Google's Firebase authentication API
     */
    FirebaseAuth firebaseAuth;

    /**
     * The email input field.
     */
    EditText mEmailView;

    /**
     * The password input field.
     */
    EditText mPasswordView;

    /**
     * The progress spinner.
     */
    private View mProgressView;

    /**
     * The authentication form.
     */
    private View mAuthenticationFormView;

    /**
     * Gets the content view number.
     *
     * @return -1
     */
    int getContentView() {
        return -1;
    }

    /**
     * If only two fields, email and password, are required then override this method.
     *
     * @param email    - The email address
     * @param password - The password
     */
    void onAuthenticationProcess(String email, String password) {

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());
        // Set up the login form.
        mEmailView = findViewById(R.id.email);

        mPasswordView = findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener((TextView textView, int id, KeyEvent keyEvent) -> {
                    if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                        attemptAuthentication();
                        return true;
                    }
                    return false;
                }
        );

        Button mEmailSignInButton = findViewById(R.id.authenticate_button);
        mEmailSignInButton.setOnClickListener((View view) -> attemptAuthentication());

        mAuthenticationFormView = findViewById(R.id.authentication_form);
        mProgressView = findViewById(R.id.authentication_progress);

        firebaseAuth = FirebaseAuth.getInstance();
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    void attemptAuthentication() {

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && isPasswordInvalid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (isEmailInvalid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            onAuthenticationProcess(email, password);
        }
    }

    /**
     * Throws an error if an incorrect password is entered.
     */
    void throwPasswordError() {
        mPasswordView.setError(getString(R.string.error_incorrect_password));
        mPasswordView.requestFocus();
        showProgress(false);
    }

    /**
     * Throws an error if an invalid email is entered.
     */
    void throwEmailError() {
        mEmailView.setError(getString(R.string.error_email_exists));
        mEmailView.requestFocus();
        showProgress(false);
    }

    /**
     * Checks if an email address is invalid.
     *
     * @param email - The email address to examine
     * @return either true or false depending on the input
     */
    boolean isEmailInvalid(String email) {
        //TODO: Replace this with your own logic
        return !email.contains("@");
    }

    /**
     * Checks if a password is invalid.
     *
     * @param password - The password to examine
     * @return - either true or false depending on the input.
     */
    boolean isPasswordInvalid(String password) {
        //TODO: Replace this with your own logic
        return password.length() <= 4;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    void showProgress(final boolean show) {
        int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

        mAuthenticationFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        mAuthenticationFormView.animate().setDuration(shortAnimTime).alpha(
                show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mAuthenticationFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            }
        });

        mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
        mProgressView.animate().setDuration(shortAnimTime).alpha(
                show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            }
        });
    }
}
