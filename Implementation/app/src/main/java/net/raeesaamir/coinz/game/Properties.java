package net.raeesaamir.coinz.game;

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