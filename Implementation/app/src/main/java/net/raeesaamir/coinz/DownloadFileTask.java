package net.raeesaamir.coinz;

import android.os.AsyncTask;

import java.util.Objects;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * An asynchronous file downloader that has a read callback. Based on the code from journaldev.com/13629/okhttp-android-example-tutorial
 *
 * @param <T> - The type of object to decode the stream into.
 * @author raeesaamir
 */
public abstract class DownloadFileTask<T> extends AsyncTask<String, Void, T> {

    /**
     * The HTTP client used for downloading the data.
     */
    private final OkHttpClient client = new OkHttpClient();

    /**
     * Decodes the stream into an object of type T.
     *
     * @param inputStream - The stream to decode.
     * @return An object of type T decoded from the stream.
     */
    protected abstract T readStream(String inputStream);

    @Override
    protected T doInBackground(String... params) {

        Request.Builder builder = new Request.Builder();
        builder.url(params[0]);
        Request request = builder.build();

        try {
            Response response = client.newCall(request).execute();
            return readStream(Objects.requireNonNull(response.body()).string());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}