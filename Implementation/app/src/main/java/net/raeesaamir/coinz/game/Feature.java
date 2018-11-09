package net.raeesaamir.coinz.game;

import com.google.common.base.MoreObjects;
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

        @Override
        public String toString() {
            return MoreObjects.toStringHelper(this).add("id", id).add("value", value)
                    .add("currency", currency).add("markerSymbol", markerSymbol).add("markerColor", markerColor).toString();
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

        @Override
        public String toString() {
            return MoreObjects.toStringHelper(this).add("type", type).add("coordinates", coordinates).toString();
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

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("type", type).add("geometry", geometry).add("properties", properties).toString();
    }
}
