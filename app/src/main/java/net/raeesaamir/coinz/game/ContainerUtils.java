package net.raeesaamir.coinz.game;

import com.google.common.base.Preconditions;

import java.util.List;

/**
 * A utility class containing implementations of addition and removal operations.
 *
 * @author raeesaamir
 */
class ContainerUtils {

    /**
     * Adds a coin to a list of coins.
     *
     * @param coins - The list containing the coins.
     * @param coin  - The coin to remove.
     * @return - True or false depending on whether the coin was added successfully.
     */
    public static boolean addCoin(List<String> coins, String coin) {
        Preconditions.checkNotNull(coin);

        return coins.add(coin);
    }

    /**
     * Removes a coin from a list of coins.
     *
     * @param coins The list containing the coins.
     * @param coin  The coin to remove.
     */
    public static void removeCoin(List<String> coins, String coin) {
        Preconditions.checkNotNull(coin);

        coins.remove(coin);
    }

}
