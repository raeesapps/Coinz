package net.raeesaamir.coinz.game;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

import net.raeesaamir.coinz.FirestoreDocument;

import java.util.List;
import java.util.function.Consumer;

public abstract class Container extends FirestoreDocument {

    protected List<String> coins = Lists.newArrayList();

    public void addCoin(String coin) {
        Preconditions.checkNotNull(coin);

        coins.add(coin);
    }

    public void removeCoin(String coin) {
        Preconditions.checkNotNull(coin);
        Preconditions.checkArgument(coins.size() > 0);
        Preconditions.checkArgument(coins.contains(coin));

        coins.remove(coin);
    }

    public void forEachCoin(Consumer<? super String> action) {
        coins.forEach(action);
    }
}
