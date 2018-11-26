package net.raeesaamir.coinz;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Base64;
import android.util.Log;

import com.mapbox.mapboxsdk.Mapbox;

import java.security.MessageDigest;

public class CoinzApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //printHashKey(this);
        Mapbox.getInstance(getApplicationContext(), getString(R.string.mapbox_access_token));
    }

    public static void printHashKey(Context context) {
        try {
            final PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES);
            for (android.content.pm.Signature signature : info.signatures) {
                final MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                final String hashKey = new String(Base64.encode(md.digest(), 0));
                Log.i("AppLog", "key:" + hashKey + "=");
            }
        } catch (Exception e) {
            Log.e("AppLog", "error:", e);
        }
    }


}
