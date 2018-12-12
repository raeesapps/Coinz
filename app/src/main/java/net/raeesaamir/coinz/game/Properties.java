package net.raeesaamir.coinz.game;

import android.support.annotation.VisibleForTesting;

import com.google.common.base.Objects;
import com.google.gson.annotations.SerializedName;

/**
 * Represents the properties of the coin such as how much it's worth.
 *
 * @author raeesaamir
 */
public final class Properties {

    /**
     * The UUID of the coin
     */
    @VisibleForTesting
    String id;

    /**
     * The monetary value of the coin
     */
    @VisibleForTesting
    String value;

    /**
     * The currency of the monetary value
     */
    @VisibleForTesting
    String currency;

    /**
     * The symbol of the marker
     */
    @VisibleForTesting
    @SerializedName("marker-symbol")
    String markerSymbol;

    /**
     * The color of the marker
     */
    @VisibleForTesting
    @SerializedName("marker-color")
    String markerColor;

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

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (obj instanceof Properties) {
            Properties otherProperties = (Properties) obj;
            return Objects.equal(id, otherProperties.id) && Objects.equal(value, otherProperties.value) &&
                    Objects.equal(currency, otherProperties.currency) && Objects.equal(markerColor, otherProperties.markerColor)
                    && Objects.equal(markerSymbol, otherProperties.markerSymbol);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, value, currency, markerColor, markerSymbol);
    }
}