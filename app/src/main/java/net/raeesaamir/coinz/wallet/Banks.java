package net.raeesaamir.coinz.wallet;

/**
 * Stores instances of the player's bank and the other player's bank.
 *
 * @author raeesaamir
 */
public final class Banks {

    /**
     * The player's bank.
     */
    private static Bank bank = null;

    /**
     * The other player's bank.
     */
    private static Bank otherBank = null;

    /**
     * Gets the player's bank.
     *
     * @return The bank.
     */
    public static Bank getBank() {
        return bank;
    }

    /**
     * Sets the player's bank.
     *
     * @param bank The player's bank.
     */
    public static void setBank(Bank bank) {
        Banks.bank = bank;
    }

    /**
     * Gets the other player's bank.
     *
     * @return The other player's bank.
     */
    public static Bank getOtherBank() {
        return otherBank;
    }

    /**
     * Sets the other player's bank.
     *
     * @param otherBank - The other player's bank.
     */
    public static void setOtherBank(Bank otherBank) {
        Banks.otherBank = otherBank;
    }
}
