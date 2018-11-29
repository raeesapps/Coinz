package net.raeesaamir.coinz.game;

import com.google.common.base.MoreObjects;
import com.google.gson.annotations.SerializedName;

/**
 * Represents a feature in a collection of features. The feature is a pinpoint on the map
 * containing a coin at a particular location.
 *
 * @author raeesaamir
 */
public final class Feature {

    /**
     * Represents the properties of the coin such as how much it's worth.
     *
     * @author raeesaamir
     */
    public static final class Properties {

        /**
         * The UUID of the coin
         */
        private final String id;

        /**
         * The monetary value of the coin
         */
        private final String value;

        /**
         * The currency of the monetary value
         */
        private final String currency;

        /**
         * The symbol of the marker
         */
        @SerializedName("marker-symbol")
        private final String markerSymbol;

        /**
         * The color of the marker
         */
        @SerializedName("marker-color")
        private final String markerColor;

        /**
         * A constructor to create a feature instance
         * @param id - the UUID of the coin
         * @param value - the monetary value of the coin
         * @param currency - the currency the value of the coin is expressed in
         * @param markerSymbol - the symbol of the coin's marker
         * @param markerColor - the color of the coin's marker
         */
        public Properties(String id, String value, String currency, String markerSymbol, String markerColor) {
            this.id = id;
            this.value = value;
            this.currency = currency;
            this.markerSymbol = markerSymbol;
            this.markerColor = markerColor;
        }

        /**
         * Gets the UUID
         * @return the UUID
         */
        public String getId() {
            return id;
        }

        /**
         * Gets the monetary value
         * @return The monetary value represented as a string
         */
        public String getValue() {
            return value;
        }

        /**
         * Gets the currency of the monetary value
         * @return A string representing the currency
         */
        public String getCurrency() {
            return currency;
        }

        /**
         * Gets the color of the marker
         * @return A string representing the color of the marker.
         */
        public String getMarkerColor() {
            return markerColor;
        }

        @Override
        public String toString() {
            return MoreObjects.toStringHelper(this).add("id", id).add("value", value)
                    .add("currency", currency).add("markerSymbol", markerSymbol).add("markerColor", markerColor).toString();
        }
    }

    /**
     * Represents the location of the coin in terms of latitude and longitude
     *
     * @author raeesaamir
     */
    public static final class Geometry {

        /**
         * The type of JSON object.
         */
        private final String type;

        /**
         * The coordinates stored as an array where idx=0 is the latitude and
         * idx=1 is the longitude
         */
        private final double[] coordinates;

        /**
         * Creates a new geometry instance
         * @param type - the type of JSON object
         * @param coordinates - the coordinates stored as a double array
         */
        public Geometry(String type, double[] coordinates) {
            this.type = type;
            this.coordinates = coordinates;
        }

        /**
         * Gets the type of JSON object
         * @return The JSON object represented as a string.
         */
        public String getType() {
            return type;
        }

        /**
         * Gets the latitude and longitude.
         * @return A double array containing the latitude and longitude.
         */
        public double[] getCoordinates() {
            return coordinates;
        }

        @Override
        public String toString() {
            return MoreObjects.toStringHelper(this).add("type", type).add("coordinates", coordinates).toString();
        }
    }

    /**
     * The type of JSON object
     */
    private final String type;

    /**
     * The properties of the coin. e.g. the of the currency and it's monetary worth
     */
    private final Properties properties;

    /**
     * The location of the coin
     */
    private final Geometry geometry;

    /**
     * Creates a new feature instance
     * @param type - the type of JSON object
     * @param properties - the properties of the coin
     * @param geometry - the location of the coin
     */
    public Feature(String type, Properties properties, Geometry geometry) {
        this.type = type;
        this.properties = properties;
        this.geometry = geometry;
    }

    /**
     * Gets the type of JSON object
     * @return A string representing the type
     */
    public String getType() {
        return type;
    }

    /**
     * Gets the location of the coin
     * @return A geometry object storing the location of the coin
     */
    public Geometry getGeometry() {
        return geometry;
    }

    /**
     * Gets the properties of the coin.
     * @return A properties object storing the attributes of the coin such as value and monetary worth.
     */
    public Properties getProperties() {
        return properties;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("type", type).add("geometry", geometry).add("properties", properties).toString();
    }
}
