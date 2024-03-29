package net.raeesaamir.coinz.authentication;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import net.raeesaamir.coinz.R;
import net.raeesaamir.coinz.menu.MenuController;

import java.util.Objects;

/**
 * A registration screen that lets the user create an account to player the game with.
 *
 * @author raeesaamir
 */
public class RegistrationController extends AuthenticationController {

    /**
     * The display name field.
     */
    private TextView mNameView;

    /**
     * The confirm password field.
     */
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
        mPasswordView.setOnEditorActionListener((TextView textView, int id, KeyEvent keyEvent) -> (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) && mConfirmPasswordView.requestFocus()
        );
    }

    @Override
    void attemptAuthentication() {
        int status = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this);
        if (status != ConnectionResult.SUCCESS) {
            String error;
            if (status == ConnectionResult.SERVICE_VERSION_UPDATE_REQUIRED) {
                error = "Please wait for google play services to update. If it is not updating update it manually by opening google play services in the play store and hitting update";
            } else {
                error = "Please download google play service and come back after downloading.";
            }
            AlertDialog comeBackLaterDialog = new AlertDialog.Builder(this).setTitle("Google Play Services").setMessage(error).setPositiveButton("Ok", (x, y) -> {
            }).create();
            comeBackLaterDialog.show();
        } else {
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
            if (!TextUtils.isEmpty(password) && isPasswordInvalid(password)) {
                mPasswordView.setError(getString(R.string.error_invalid_password));
                focusView = mPasswordView;
                cancel = true;
            }

            if (TextUtils.isEmpty(confirmPassword)) {
                mConfirmPasswordView.setError(getString(R.string.error_confirm_password));
                focusView = mConfirmPasswordView;
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

            if (!password.equals(confirmPassword)) {
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
    }

    /**
     * Called when the user clicks the create account button.
     *
     * @param name     - The user's display name.
     * @param email    - The user's email address.
     * @param password - The user's password.
     */
    private void onAuthenticationProcess(String name, String email, String password) {
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener((@NonNull Task<AuthResult> task) -> {
            if (task.isSuccessful()) {

                FirebaseUser user = firebaseAuth.getCurrentUser();
                Objects.requireNonNull(user).updateProfile(new UserProfileChangeRequest.Builder().setDisplayName(name).build());

                FirestoreUser userDocument = new FirestoreUser(email, user.getUid(), name);
                userDocument.getFuture();

                Intent menu = new Intent(getApplicationContext(), MenuController.class);
                startActivity(menu);
            } else {

                try {
                    throw Objects.requireNonNull(task.getException());
                } catch (FirebaseAuthUserCollisionException collisionException) {
                    throwEmailError();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        });
    }

    @Override
    public int getContentView() {
        return R.layout.registration_view;
    }
}

