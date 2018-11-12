package net.raeesaamir.coinz.game;

public final class Coin {

    private final double value;
    private final String currency;

    public Coin(double value, String currency) {
        this.value = value;
        this.currency = currency;
    }

    public String getCurrency() {
        return currency;
    }

    public double getValue() {
        return value;
    }
}
