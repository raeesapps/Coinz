package net.raeesaamir.coinz.splash;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import net.raeesaamir.coinz.R;
import net.raeesaamir.coinz.authentication.LoginController;
import net.raeesaamir.coinz.menu.MenuController;

public class SplashScreenController extends AppCompatActivity {

    private static final int SPLASH_SCREEN_DURATION = 2000;

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen_view);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        final Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Class<?> controller;
                if(mUser != null) {
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
