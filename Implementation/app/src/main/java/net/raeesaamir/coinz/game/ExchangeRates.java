package net.raeesaamir.coinz.game;

import com.google.common.base.MoreObjects;
import com.google.gson.annotations.SerializedName;

/**
 * Represents the exchange rates of each type of coin on the map.
 *
 * @author raeesaamir
 */
public final class ExchangeRates {

    /**
     * The exchange rate of shil to gold.
     */
    @SuppressWarnings("unused")
    @SerializedName("SHIL")
    private double shil;

    /**
     * The exchange rate of dolr to gold.
     */
    @SuppressWarnings("unused")
    @SerializedName("DOLR")
    private double dolr;

    /**
     * The exchange rate of quid to gold.
     */
    @SuppressWarnings("unused")
    @SerializedName("QUID")
    private double quid;

    /**
     * The exchange rate of peny to gold.
     */
    @SuppressWarnings("unused")
    @SerializedName("PENY")
    private double peny;

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("shil", shil)
                .add("dolr", dolr).add("quid", quid).add("peny", peny)
                .toString();
    }

    /**
     * Returns the dolr exchange rate.
     *
     * @return The dolr exchange rate.
     */
    public double getDolr() {
        return dolr;
    }

    /**
     * Returns the peny exchange rate.
     *
     * @return The peny exchange rate.
     */
    public double getPeny() {
        return peny;
    }

    /**
     * Returns the quid exchange rate.
     *
     * @return The quid exchange rate.
     */
    public double getQuid() {
        return quid;
    }

    /**
     * Returns the shil exchange rate.
     *
     * @return The shil exchange rate.
     */
    public double getShil() {
        return shil;
    }
}
