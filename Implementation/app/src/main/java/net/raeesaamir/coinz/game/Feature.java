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
     * The type of JSON object
     */
    @SuppressWarnings("unused")
    private String type;
    /**
     * The properties of the coin. e.g. the of the currency and it's monetary worth
     */
    @SuppressWarnings("unused")
    private Properties properties;
    /**
     * The location of the coin
     */
    @SuppressWarnings("unused")
    private Geometry geometry;

    /**
     * Gets the location of the coin
     *
     * @return A geometry object storing the location of the coin
     */
    public Geometry getGeometry() {
        return geometry;
    }

    /**
     * Gets the properties of the coin.
     *
     * @return A properties object storing the attributes of the coin such as value and monetary worth.
     */
    public Properties getProperties() {
        return properties;
    }

    @SuppressWarnings("unused")
    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("type", type).add("geometry", geometry).add("properties", properties).toString();
    }

    /**
     * Represents the properties of the coin such as how much it's worth.
     *
     * @author raeesaamir
     */
    public static final class Properties {

        /**
         * The UUID of the coin
         */
        @SuppressWarnings("unused")
        private String id;

        /**
         * The monetary value of the coin
         */
        @SuppressWarnings("unused")
        private String value;

        /**
         * The currency of the monetary value
         */
        @SuppressWarnings("unused")
        private String currency;

        /**
         * The symbol of the marker
         */
        @SuppressWarnings("unused")
        @SerializedName("marker-symbol")
        private String markerSymbol;

        /**
         * The color of the marker
         */
        @SuppressWarnings("unused")
        @SerializedName("marker-color")
        private String markerColor;

        /**
         * Gets the monetary value
         *
         * @return The monetary value represented as a string
         */
        public String getValue() {
            return value;
        }

        /**
         * Gets the currency of the monetary value
         *
         * @return A string representing the currency
         */
        public String getCurrency() {
            return currency;
        }

        /**
         * Gets the color of the marker
         *
         * @return A string representing the color of the marker.
         */
        public String getMarkerColor() {
            return markerColor;
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
        @SuppressWarnings("unused")
        private String type;

        /**
         * The coordinates stored as an array where idx=0 is the latitude and
         * idx=1 is the longitude
         */
        @SuppressWarnings("unused")
        private double[] coordinates;

        /**
         * Gets the latitude and longitude.
         *
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
}
