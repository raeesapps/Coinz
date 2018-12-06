package net.raeesaamir.coinz.game;

import com.google.common.base.MoreObjects;

/**
 * Represents the location of the coin in terms of latitude and longitude
 *
 * @author raeesaamir
 */
public final class Geometry {

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