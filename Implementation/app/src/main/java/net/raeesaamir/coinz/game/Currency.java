package net.raeesaamir.coinz.game;

public enum Currency {

    SHIL,
    DOLR,
    QUID,
    PENY;

    public static Currency fromString(String currency) {
        switch(currency) {
            case "SHIL":
                return SHIL;
            case "DOLR":
                return DOLR;
            case "QUID":
                return QUID;
            case "PENY":
                return PENY;
        }

        return null;
    }

}
