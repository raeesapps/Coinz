package net.raeesaamir.coinz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashSceenController extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_sceen);

        System.out.println("Segue completed");
    }
}
