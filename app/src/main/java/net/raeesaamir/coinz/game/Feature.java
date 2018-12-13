package net.raeesaamir.coinz.game;

import com.google.common.base.MoreObjects;

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
}