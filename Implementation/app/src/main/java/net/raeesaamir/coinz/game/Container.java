package net.raeesaamir.coinz.game;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

import java.util.List;
import java.util.function.Consumer;

public class Container {

    private final List<Coin> coins = Lists.newArrayList();
    private Coin[] coinsArray;

    private void addCoin(Coin coin) {
        Preconditions.checkNotNull(coin);

        coins.add(coin);
    }

    private void removeCoin(Coin coin) {
        Preconditions.checkNotNull(coin);
        Preconditions.checkArgument(coins.size() > 0);
        Preconditions.checkArgument(coins.contains(coin));

        coins.remove(coin);
    }

    public void forEachCoin(Consumer<? super Coin> action) {
        coins.forEach(action);
    }

    public double calculateTotalValue() {
        double value = 0;

        for(Coin coin: coins) {
            value += coin.getValue();
        }

        return value;
    }

    public Coin[] allCoins() {
        if(coinsArray == null) {
            coinsArray = coins.toArray(new Coin[coins.size()]);
        }

        return coinsArray;
    }
}
