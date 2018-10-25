package net.raeesaamir.coinz.authentication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import net.raeesaamir.coinz.R;
import net.raeesaamir.coinz.menu.MenuController;

/**
 * A login screen that offers login via email/password.
 */
public class RegistrationController extends AuthenticationController {

    private TextView mNameView;
    private TextView mConfirmPasswordView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNameView = findViewById(R.id.name);

        mConfirmPasswordView = findViewById(R.id.confirmPassword);
        mConfirmPasswordView.setOnEditorActionListener((TextView textView, int id, KeyEvent keyEvent) -> {
                    if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                        attemptAuthentication();
                        return true;
                    }
                    return false;
                }
        );

        mPasswordView = findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener((TextView textView, int id, KeyEvent keyEvent) -> {
                    if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                        return mConfirmPasswordView.requestFocus();
                    }
                    return false;
                }
        );
    }

    @Override
    protected void attemptAuthentication() {
        // Reset errors.
        mNameView.setError(null);
        mEmailView.setError(null);
        mPasswordView.setError(null);
        mConfirmPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String name = mNameView.getText().toString();
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();
        String confirmPassword = mConfirmPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        if(TextUtils.isEmpty(confirmPassword)) {
            mConfirmPasswordView.setError(getString(R.string.error_confirm_password));
            focusView = mConfirmPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if(!password.equals(confirmPassword)) {
            mPasswordView.setError(getString(R.string.error_mismatch_password));
            focusView = mPasswordView;
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
            onAuthenticationProcess(name, email, password);
        }
    }

    private void onAuthenticationProcess(String name, String email, String password) {
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener((@NonNull Task<AuthResult> task) -> {
            if(task.isSuccessful()) {

                FirebaseUser user = firebaseAuth.getCurrentUser();
                user.updateProfile(new UserProfileChangeRequest.Builder().setDisplayName(name).build());

                Intent menu = new Intent(getApplicationContext(), MenuController.class);
                startActivity(menu);
            } else {

                try {
                    throw task.getException();
                } catch(FirebaseAuthUserCollisionException collisionException) {
                    throwEmailError(R.string.error_email_exists);
                } catch(Exception ignored) {}
            }

        });
    }

    @Override
    public int getContentView() {
        return R.layout.registration_view;
    }
}

