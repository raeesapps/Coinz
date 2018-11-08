package net.raeesaamir.coinz.game;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

public class FeatureCollection {

    private final String type;
    private final String dateGenerated;
    private final String timeGenerated;
    private final String approximateTimeRemaining;
    private final ImmutableMap<Currency, Double> rates;
    private final ImmutableList<Feature> features;

    public FeatureCollection(String type, String dateGenerated, String timeGenerated, String approximateTimeRemaining, ImmutableMap<Currency, Double> rates, ImmutableList<Feature> features) {
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

    public ImmutableMap<Currency, Double> getRates() {
        return rates;
    }

    public ImmutableList<Feature> getFeatures() {
        return features;
    }
}
