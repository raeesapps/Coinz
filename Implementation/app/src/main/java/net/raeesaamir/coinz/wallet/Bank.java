package net.raeesaamir.coinz.wallet;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;

import net.raeesaamir.coinz.game.Container;
import net.raeesaamir.coinz.game.FeatureCollection;

import java.util.List;

/**
 * Represent's the player's bank of gold. Stored in cloud-firestore.
 *
 * @author raeesaamir
 */
public class Bank extends Container {

    /**
     * The UUID of the player.
     */
    private final String userUid;

    /**
     * Constructs an empty bank.
     *
     * @param userUid - The player's UUID.
     */
    public Bank(String userUid) {
        this.userUid = userUid;
    }

    /**
     * Constructs a bank based on a list of gold.
     *
     * @param userUid - The player's UUID
     * @param coins   - The list of gold.
     */
    public Bank(String userUid, List<String> coins) {
        this.userUid = userUid;
        this.coins = coins;
    }

    @Override
    public ImmutableMap<String, Object> getDocument() {
        return new ImmutableMap.Builder<String, Object>().put("userUid", userUid).put("coins", coins).build();
    }

    @Override
    public String getDocumentName() {
        return userUid;
    }

    @Override
    public String getCollectionName() {
        return "Banks";
    }

    /**
     * Deposits a coin from the player's a wallet into the bank based on today's exchange rates.
     *
     * @param exchangeRates - The exchange rates.
     * @param coin          - The coin to deposit.
     * @param wallet        - The wallet the coin is from.
     */
    public void deposit(FeatureCollection.ExchangeRates exchangeRates, String coin, Wallet wallet) {
        wallet.removeCoin(coin);

        String[] coinAttributes = coin.split(" ");

        String coinType = coinAttributes[0];
        double coinValue = Double.parseDouble(coinAttributes[1]);

        double exchangeRate;
        switch (coinType) {
            case "SHIL":
                exchangeRate = exchangeRates.getShil();
                break;
            case "DOLR":
                exchangeRate = exchangeRates.getDolr();
                break;
            case "PENY":
                exchangeRate = exchangeRates.getPeny();
                break;
            default:
                exchangeRate = exchangeRates.getQuid();
                break;
        }

        Preconditions.checkArgument(exchangeRate != -1);

        double goldAmount = coinValue * exchangeRate;
        addCoin("GOLD " + goldAmount);
        getFuture();
    }

    /**
     * Calculates the total amount of gold in the player's bank.
     *
     * @return - The total amount of gold.
     */
    public double totalGold() {
        double totalGold = 0;

        for (String coin : coins) {
            String[] coinAttributes = coin.split(" ");

            Preconditions.checkArgument(coinAttributes[0].equals("GOLD"));
            totalGold += Double.parseDouble(coinAttributes[1]);
        }

        return totalGold;
    }

}
