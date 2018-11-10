package net.raeesaamir.coinz.game;

public final class Coin {

    private final double value;
    private final Currency currency;

    public Coin(double value, Currency currency) {
        this.value = value;
        this.currency = currency;
    }

    public Currency getCurrency() {
        return currency;
    }

    public double getValue() {
        return value;
    }
}
