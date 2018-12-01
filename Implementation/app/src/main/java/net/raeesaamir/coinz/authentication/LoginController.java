package net.raeesaamir.coinz.authentication;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

import net.raeesaamir.coinz.R;
import net.raeesaamir.coinz.menu.MenuController;

/**
 * A login screen that offers login via email/password.
 *
 * @author raeesaamir
 */
public class LoginController extends AuthenticationController {

    @Override
    public void onAuthenticationProcess(String email, String password) {
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener((@NonNull Task<AuthResult> task) -> {
            if (!task.isSuccessful()) {
                throwPasswordError();
            } else {
                Intent menu = new Intent(this, MenuController.class);
                startActivity(menu);
            }
        });
    }

    @Override
    public int getContentView() {
        return R.layout.login_view;
    }

    /**
     * Segues the user to the registration form if they don't have an account.
     *
     * @param view The view object. Unused but required for the method signature.
     */
    public void goToRegistration(@SuppressWarnings("unused") View view) {
        Intent registration = new Intent(this, RegistrationController.class);
        startActivity(registration);
    }
}
