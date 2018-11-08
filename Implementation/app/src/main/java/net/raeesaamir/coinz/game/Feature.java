package net.raeesaamir.coinz.game;

import com.google.common.collect.ImmutableList;

public class Feature {

    public static final class Properties {

        private final String id;
        private final String value;
        private final Currency currency;
        private final String markerSymbol;
        private final String markerColor;

        public Properties(String id, String value, Currency currency, String markerSymbol, String markerColor) {
            this.id = id;
            this.value = value;
            this.currency = currency;
            this.markerSymbol = markerSymbol;
            this.markerColor = markerColor;
        }

        public String getId() {
            return id;
        }

        public String getValue() {
            return value;
        }

        public Currency getCurrency() {
            return currency;
        }

        public String getMarkerSymbol() {
            return markerSymbol;
        }

        public String getMarkerColor() {
            return markerColor;
        }
    }

    public static final class Geometry {
        private final String type;
        private final ImmutableList<Double> coordinates;

        public Geometry(String type, ImmutableList<Double> coordinates) {
            this.type = type;
            this.coordinates = coordinates;
        }

        public String getType() {
            return type;
        }

        public ImmutableList<Double> getCoordinates() {
            return coordinates;
        }
    }

    private String type;
    private Properties properties;
    private Geometry geometry;

    public Feature(String type, Properties properties, Geometry geometry) {
        this.type = type;
        this.properties = properties;
        this.geometry = geometry;
    }

    public String getType() {
        return type;
    }

    public Geometry getGeometry() {
        return geometry;
    }

    public Properties getProperties() {
        return properties;
    }
}
