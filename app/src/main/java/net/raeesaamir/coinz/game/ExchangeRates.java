package net.raeesaamir.coinz.game;

import android.support.annotation.VisibleForTesting;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
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
    @VisibleForTesting
    @SerializedName("SHIL")
    double shil;

    /**
     * The exchange rate of dolr to gold.
     */
    @VisibleForTesting
    @SerializedName("DOLR")
    double dolr;

    /**
     * The exchange rate of quid to gold.
     */
    @VisibleForTesting
    @SerializedName("QUID")
    double quid;

    /**
     * The exchange rate of peny to gold.
     */
    @VisibleForTesting
    @SerializedName("PENY")
    double peny;

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("shil", shil)
                .add("dolr", dolr).add("quid", quid).add("peny", peny)
                .toString();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(shil, dolr, quid, peny);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) {
            return false;
        }

        if(obj instanceof ExchangeRates) {
            ExchangeRates otherExchangeRates = (ExchangeRates) obj;
            return Objects.equal(shil, otherExchangeRates.shil) && Objects.equal(dolr, otherExchangeRates.dolr)
                    && Objects.equal(quid, otherExchangeRates.quid) && Objects.equal(peny, otherExchangeRates.peny);
        } else {
            return false;
        }
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
