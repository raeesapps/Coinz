package net.raeesaamir.coinz.game;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.support.annotation.VisibleForTesting;

import com.google.common.base.MoreObjects;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import net.raeesaamir.coinz.DownloadFileTask;

import java.util.concurrent.ExecutionException;

/**
 * Represents today's map of coins used for the gameplay. As such features of the map are defined here.
 *
 * @author raeesaamir
 */
public final class FeatureCollection {

    /**
     * The url where the maps are stored.
     */
    private static final String FEATURE_COLLECTION_URL = "http://homepages.inf.ed.ac.uk/stg/coinz/";
    /**
     * The type of JSON object.
     */
    private final String type;
    /**
     * The date the map was generated
     */
    @SerializedName("date-generated")
    @VisibleForTesting
    final String dateGenerated;
    /**
     * The time the map was generated
     */
    @SerializedName("time-generated")
    @VisibleForTesting
    final String timeGenerated;
    /**
     * The time left.
     */
    @SerializedName("approximate-time-remaining")
    @VisibleForTesting
    final String approximateTimeRemaining;
    /**
     * Today's exchange rates of the types of coins on the map.
     */
    private final ExchangeRates rates;
    /**
     * Features of the markers on the map.
     */
    private final Feature[] features;

    /**
     * Constructs a new feature collection instance
     *
     * @param type                     - The type of JSON object
     * @param dateGenerated            - The date the map was generated
     * @param timeGenerated            - The time the map was generated
     * @param approximateTimeRemaining - The time left
     * @param rates                    - The exchange rates
     * @param features                 - The features of the markers
     */
    private FeatureCollection(String type, String dateGenerated, String timeGenerated, String approximateTimeRemaining, ExchangeRates rates, Feature[] features) {
        this.type = type;
        this.dateGenerated = dateGenerated;
        this.timeGenerated = timeGenerated;
        this.approximateTimeRemaining = approximateTimeRemaining;
        this.rates = rates;
        this.features = features;
    }

    /**
     * Uses GSON to download today's map from the informatics server.
     *
     * @param preferences The shared preferences object that will be used to store the map.
     * @param gson        The Gson instance that will be used to encode and decode the map.
     * @param date        The date of the map.
     * @return A feature collection object representing today's map.
     * @throws ExecutionException   If the download fails
     * @throws InterruptedException If something disrupts the download from happening smoothly.
     */
    @SuppressLint("ApplySharedPref")
    public static FeatureCollection fromWebsite(SharedPreferences preferences, Gson gson, String uid, String date) throws ExecutionException, InterruptedException {

        String url = FEATURE_COLLECTION_URL + date + "/coinzmap.geojson";
        FeatureCollection featureCollection;
        String key = uid + "/" + date;

        if (preferences.contains(key)) {
            String json = preferences.getString(key, "");
            System.out.println("[FeatureCollection json not null] " + json);
            featureCollection = gson.fromJson(json, FeatureCollection.class);

        } else {

            featureCollection = new GeoJsonDownloadTask().execute(url).get();
            String json = gson.toJson(featureCollection);
            System.out.println("[FeatureCollection json not exists] " + json);
            preferences.edit().putString(key, json).commit();
        }
        return featureCollection;
    }

    /**
     * Gets the exchange rates of the types of coins.
     *
     * @return An object containing the exchange rates.
     */
    public ExchangeRates getRates() {
        return rates;
    }

    /**
     * Gets the features of the markers.
     *
     * @return An array of feature objects
     */
    public Feature[] getFeatures() {
        return features;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("type", type).add("dateGenerated", dateGenerated)
                .add("timeGenerated", timeGenerated).add("approximateTimeRemaining", approximateTimeRemaining).
                        add("rates", rates).add("features", features).toString();
    }

    /**
     * Decodes the feature collection data into an object for easy access of the information.
     *
     * @author raeesaamir
     */
    protected static class GeoJsonDownloadTask extends DownloadFileTask<FeatureCollection> {

        @Override
        public FeatureCollection readStream(String json) {

            return new Gson().fromJson(json, FeatureCollection.class);
        }
    }
}
