package net.raeesaamir.coinz.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.VisibleForTesting;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import net.raeesaamir.coinz.R;
import net.raeesaamir.coinz.authentication.LoginController;
import net.raeesaamir.coinz.menu.MenuController;

/**
 * The splash screen is loaded here and the user is segued to either the menu view or the login view depending on if the user is logged in.
 *
 * @author raeesaamir
 */
public class SplashScreenController extends AppCompatActivity {

    /**
     * The duration for which the splash screen will appear.
     */
    @VisibleForTesting
    static final int SPLASH_SCREEN_DURATION = 2000;

    /**
     * The authenticated user.
     */
    private FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen_view);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        final Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Class<?> controller;
                if (mUser != null) {
                    controller = MenuController.class;
                } else {
                    controller = LoginController.class;
                }

                Intent activity = new Intent(getApplicationContext(), controller);
                startActivity(activity);
                handler.removeCallbacks(this);
            }
        }, SPLASH_SCREEN_DURATION);
    }
}
