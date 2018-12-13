package net.raeesaamir.coinz.wallet;

/**
 * Stores instances of the player's wallet and the other player's wallet.
 *
 * @author raeesaamir
 */
public final class Wallets {

    /**
     * This player's wallet.
     */
    private static Wallet wallet = null;

    /**
     * This player's spare wallet.
     */
    private static Wallet spareWallet = null;

    /**
     * The history of the coins the player has collected today.
     */
    private static Wallet history = null;

    /**
     * Get's the players history.
     *
     * @return - The history.
     */
    public static Wallet getHistory() {
        return history;
    }

    /**
     * Sets the player history.
     *
     * @param history - The history.
     */
    public static void setHistory(Wallet history) {
        Wallets.history = history;
    }

    /**
     * Gets the spare wallet.
     *
     * @return The spare wallet.
     */
    public static Wallet getSpareWallet() {
        return spareWallet;
    }

    /**
     * Sets this player's spare change wallet.
     *
     * @param spareWallet - The wallet object.
     */
    public static void setSpareWallet(Wallet spareWallet) {
        Wallets.spareWallet = spareWallet;
    }

    /**
     * Gets the wallet.
     *
     * @return The wallet.
     */
    public static Wallet getWallet() {
        return wallet;
    }

    /**
     * Sets this player's wallet.
     *
     * @param wallet - The wallet object.
     */
    public static void setWallet(Wallet wallet) {
        Wallets.wallet = wallet;
    }
}
