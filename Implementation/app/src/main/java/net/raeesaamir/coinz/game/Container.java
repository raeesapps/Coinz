package net.raeesaamir.coinz.game;

import com.google.common.collect.Lists;

import net.raeesaamir.coinz.FirestoreDocument;

import java.util.List;

public abstract class Container extends FirestoreDocument {

    protected List<String> coins = Lists.newArrayList();

    public void addCoin(String coin) {
        ContainerUtils.addCoin(coins, coin);
    }

    public void removeCoin(String coin) {
        ContainerUtils.removeCoin(coins, coin);
        this.getFuture();
    }
}
