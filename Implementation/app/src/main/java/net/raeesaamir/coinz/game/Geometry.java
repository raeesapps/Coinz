package net.raeesaamir.coinz.game;

import android.support.annotation.VisibleForTesting;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import java.util.Arrays;

/**
 * Represents the location of the coin in terms of latitude and longitude
 *
 * @author raeesaamir
 */
public final class Geometry {

    /**
     * The type of JSON object.
     */
    @VisibleForTesting
    String type;

    /**
     * The coordinates stored as an array where idx=0 is the latitude and
     * idx=1 is the longitude
     */
    @VisibleForTesting
    double[] coordinates;

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

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (obj instanceof Geometry) {
            Geometry otherGeometry = (Geometry) obj;
            return Objects.equal(type, otherGeometry.type) && Arrays.equals(coordinates, otherGeometry.coordinates);
        } else {
            return false;
        }

    }

    @Override
    public int hashCode() {
        return Objects.hashCode(type, coordinates);
    }
}