package net.raeesaamir.coinz.game;

import com.google.common.collect.Lists;

import net.raeesaamir.coinz.FirestoreDocument;

import java.util.List;

/**
 * A database based coin storage container for adding and removing coins.
 *
 * @author raeesaamir
 */
public abstract class Container extends FirestoreDocument {

    /**
     * The coins in the container are stored here in the list.
     */
    protected List<String> coins = Lists.newArrayList();

    /**
     * Adds a coin to the list of coins.
     *
     * @param coin - The coin to add.
     */
    public void addCoin(String coin) {
        ContainerUtils.addCoin(coins, coin);
    }


    /**
     * Removes a coin from the list of coins.
     *
     * @param coin - The coin to remove.
     */
    public void removeCoin(String coin) {
        ContainerUtils.removeCoin(coins, coin);
        this.getFuture();
    }
}
