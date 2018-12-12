package net.raeesaamir.coinz;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.mapbox.mapboxsdk.Mapbox;


/**
 * The application class for the Android project.
 *
 * @author raeesaamir
 */
@SuppressWarnings("WeakerAccess")
public class CoinzApplication extends Application {

    /**
     * Checks if there is an available internet connection to send and receive requests.
     * <p>
     * This code is taken from https://stackoverflow.com/questions/39762423/checking-if-there-is-internet-connection
     *
     * @param context - The application context.
     * @return True or false depending on whether there is a connection.
     */
    public static boolean isInternetConnectionAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo;
        if (connectivityManager != null) {
            activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            return activeNetworkInfo.isConnected();
        } else {
            return false;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //printHashKey(this);
        Mapbox.getInstance(getApplicationContext(), getString(R.string.mapbox_access_token));
    }

// --Commented out by Inspection START (29/11/2018, 17:36):
//    public static void printHashKey(Context context) {
//        try {
//            final PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES);
//            for (android.content.pm.Signature signature : info.signatures) {
//                final MessageDigest md = MessageDigest.getInstance("SHA");
//                md.update(signature.toByteArray());
//                final String hashKey = new String(Base64.encode(md.digest(), 0));
//                Log.i("AppLog", "key:" + hashKey + "=");
//            }
//        } catch (Exception e) {
//            Log.e("AppLog", "error:", e);
//        }
//    }
// --Commented out by Inspection STOP (29/11/2018, 17:36)

}