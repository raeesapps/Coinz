package net.raeesaamir.coinz;

import android.os.AsyncTask;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public abstract class DownloadFileTask<T> extends AsyncTask<String, Void, T> {

    protected abstract T readStream(String inputStream);


    @Override
    protected T doInBackground(String... strings) {
        try {
            URL url = new URL(strings[0]);
            URLConnection connection = url.openConnection();
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(15000);
            connection.setDoInput(true);
            if (connection instanceof HttpURLConnection) {
                HttpURLConnection httpConnection = (HttpURLConnection) connection;
                httpConnection.setRequestMethod("GET");
            }

            InputStream inputStream = connection.getInputStream();
            StringBuilder document = new StringBuilder();

            int ptr;
            while ((ptr = inputStream.read()) != -1) {
                document.append((char) ptr);
            }

            return readStream(document.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}