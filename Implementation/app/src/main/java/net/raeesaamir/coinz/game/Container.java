package net.raeesaamir.coinz.game;

/**
 * Represents a container that can store coins.
 *
 * @author raeesaamir
 */
public interface Container {

    /**
     * Add a coin to the container
     * @param coin - The coin to add
     */
    void addCoin(String coin);

    /**
     * Remove a coin from the container
     * @param coin - The coin to remove
     */
    void removeCoin(String coin);
}
