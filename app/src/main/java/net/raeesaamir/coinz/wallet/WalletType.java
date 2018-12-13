package net.raeesaamir.coinz.wallet;

/**
 * Represent's the type of wallet.
 *
 * @author raeesaamir
 */
public enum WalletType {

    /**
     * If a wallet is the main wallet then the coins the player collects will go there.
     */
    MAIN_WALLET,

    /**
     * If the wallet is a spare change wallet then the coins the player cannot collect will go there.
     * The player can trade coins in his/her spare change wallet to other players.
     */
    SPARE_CHANGE_WALLET
}