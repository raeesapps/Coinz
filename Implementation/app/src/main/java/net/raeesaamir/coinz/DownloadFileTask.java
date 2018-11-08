package net.raeesaamir.coinz;

import android.os.AsyncTask;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import javax.net.ssl.HttpsURLConnection;

public abstract class DownloadFileTask extends AsyncTask<String, Void, String> {

    public abstract String readStream(InputStream inputStream);


    @Override
    protected String doInBackground(String... strings) {
        try {
            URL url = new URL(strings[0]);
            URLConnection connection = url.openConnection();
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(15000);
            connection.setDoInput(true);
            if(connection instanceof HttpURLConnection) {
                HttpURLConnection httpConnection = (HttpURLConnection) connection;
                httpConnection.setRequestMethod("GET");
            } else if(connection instanceof HttpsURLConnection) {
                HttpsURLConnection httpsConnection = (HttpsURLConnection) connection;
                httpsConnection.setRequestMethod("GET");
            }

            return readStream(connection.getInputStream());
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}