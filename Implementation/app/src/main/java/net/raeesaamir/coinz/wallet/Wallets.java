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
     * The other player's wallet.
     */
    private static Wallet otherWallet = null;

    /**
     * Gets the other wallet.
     *
     * @return The other wallet.
     */
    public static Wallet getOtherWallet() {
        return otherWallet;
    }

    /**
     * Sets the other player's wallet.
     *
     * @param otherWallet - The wallet object to set.
     */
    public static void setOtherWallet(Wallet otherWallet) {
        Wallets.otherWallet = otherWallet;
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
