package net.raeesaamir.coinz.game;

import com.google.common.collect.Lists;

import net.raeesaamir.coinz.FirestoreDocument;

import java.util.List;

public abstract class FirestoreContainer extends FirestoreDocument implements Container {

    protected List<String> coins = Lists.newArrayList();

    @Override
    public void addCoin(String coin) {
        ContainerUtils.addCoin(coins, coin);
    }

    @Override
    public void removeCoin(String coin) {
        ContainerUtils.addCoin(coins, coin);
    }
}
