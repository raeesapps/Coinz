package net.raeesaamir.coinz;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashScreenController extends AppCompatActivity {

    private static final int SPLASH_SCREEN_DURATION = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen_view);

        final Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent menu = new Intent(getApplicationContext(), MenuController.class);
                startActivity(menu);
                handler.removeCallbacks(this);
            }
        }, SPLASH_SCREEN_DURATION);
    }
}
