package net.raeesaamir.coinz.game;

import android.content.SharedPreferences;

import com.google.common.base.MoreObjects;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.concurrent.ExecutionException;

public final class FeatureCollection {

    /**
     * The url where the maps are stored.
     */
    private static final String FEATURE_COLLECTION_URL = "http://homepages.inf.ed.ac.uk/stg/coinz/";

    /**
     * Uses GSON to download today's map from the informatics server.
     * @param preferences The shared preferences object that will be used to store the map.
     * @param gson The Gson instance that will be used to encode and decode the map.
     * @param date The date of the map.
     * @return A feature collection object representing today's map.
     * @throws ExecutionException If the download fails
     * @throws InterruptedException If something disrupts the download from happening smoothly.
     */
    public static FeatureCollection fromWebsite(SharedPreferences preferences, Gson gson, String date) throws ExecutionException, InterruptedException{

        String url = FEATURE_COLLECTION_URL + date + "/coinzmap.geojson";
        FeatureCollection featureCollection;

        if(preferences.contains(date)) {
            String json = preferences.getString(date, "");
            System.out.println("[FeatureCollection json not null] " + json);
            featureCollection = gson.fromJson(json, FeatureCollection.class);

        } else {
            featureCollection = new GameFragment.GeoJsonDownloadTask().execute(url).get();
            String json = gson.toJson(featureCollection);
            System.out.println("[FeatureCollection json not exists] " + json);
            preferences.edit().putString(date, json).commit();
        }

        return featureCollection;
    }

    public static class ExchangeRates {

        @SerializedName("SHIL")
        private final double shil;

        @SerializedName("DOLR")
        private final double dolr;

        @SerializedName("QUID")
        private final double quid;

        @SerializedName("PENY")
        private final double peny;

        public ExchangeRates(double shil, double dolr, double quid, double peny) {
            this.shil = shil;
            this.dolr = dolr;
            this.quid = quid;
            this.peny = peny;
        }

        @Override
        public String toString() {
            return MoreObjects.toStringHelper(this).add("shil",shil)
                    .add("dolr",dolr).add("quid",quid).add("peny",peny)
                    .toString();
        }

        public double getDolr() {
            return dolr;
        }

        public double getPeny() {
            return peny;
        }

        public double getQuid() {
            return quid;
        }

        public double getShil() {
            return shil;
        }
    }

    private final String type;

    @SerializedName("date-generated")
    private final String dateGenerated;

    @SerializedName("time-generated")
    private final String timeGenerated;

    @SerializedName("approximate-time-remaining")
    private final String approximateTimeRemaining;

    private final ExchangeRates rates;

    private final Feature[] features;

    public FeatureCollection(String type, String dateGenerated, String timeGenerated, String approximateTimeRemaining, ExchangeRates rates, Feature[] features) {
        this.type = type;
        this.dateGenerated = dateGenerated;
        this.timeGenerated = timeGenerated;
        this.approximateTimeRemaining = approximateTimeRemaining;
        this.rates = rates;
        this.features = features;
    }

    public String getType() {
        return type;
    }

    public String getDateGenerated() {
        return dateGenerated;
    }

    public String getTimeGenerated() {
        return timeGenerated;
    }

    public String getApproximateTimeRemaining() {
        return approximateTimeRemaining;
    }

    public ExchangeRates getRates() {
        return rates;
    }

    public Feature[] getFeatures() {
        return features;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("type", type).add("dateGenerated", dateGenerated)
                .add("timeGenerated", timeGenerated).add("approximateTimeRemaining", approximateTimeRemaining).
                add("rates", rates).add("features", features).toString();
    }
}
