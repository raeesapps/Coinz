package net.raeesaamir.coinz;

import android.app.Application;

import com.mapbox.mapboxsdk.Mapbox;

public class CoinzApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Mapbox.getInstance(getApplicationContext(), getString(R.string.mapbox_access_token));
    }
}
