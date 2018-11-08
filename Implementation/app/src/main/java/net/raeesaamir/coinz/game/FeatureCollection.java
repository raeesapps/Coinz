package net.raeesaamir.coinz.game;

import com.google.common.collect.Maps;

import java.util.Map;

public class FeatureCollection {

    private final String type;
    private final String dateGenerated;
    private final String timeGenerated;
    private final String approximateTimeRemaining;
    private final Map<Currency, Double> rates;

    public FeatureCollection(String type, String dateGenerated, String timeGenerated, String approximateTimeRemaining, Map<Currency, Double> rates) {
        this.type = type;
        this.dateGenerated = dateGenerated;
        this.timeGenerated = timeGenerated;
        this.approximateTimeRemaining = approximateTimeRemaining;
        this.rates = rates;
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

    public Map<Currency, Double> getRates() {
        return rates;
    }
}
