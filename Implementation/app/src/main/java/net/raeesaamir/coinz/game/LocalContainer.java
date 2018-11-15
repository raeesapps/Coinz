package net.raeesaamir.coinz.game;

import com.google.common.collect.Lists;

import java.util.List;

public class LocalContainer implements Container {

    protected List<String> coins = Lists.newArrayList();

    @Override
    public void addCoin(String coin) {
        ContainerUtils.addCoin(coins, coin);
    }

    @Override
    public void removeCoin(String coin) {
        ContainerUtils.removeCoin(coins, coin);
    }
}
