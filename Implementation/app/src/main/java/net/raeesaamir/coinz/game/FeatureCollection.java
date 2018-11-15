package net.raeesaamir.coinz.game;

import com.google.common.base.MoreObjects;
import com.google.gson.annotations.SerializedName;

public class FeatureCollection {

    public static class ExchangeRates {

        @SerializedName("SHIL")
        private double shil;

        @SerializedName("DOLR")
        private double dolr;

        @SerializedName("QUID")
        private double quid;

        @SerializedName("PENY")
        private double peny;

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
    private String dateGenerated;

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
