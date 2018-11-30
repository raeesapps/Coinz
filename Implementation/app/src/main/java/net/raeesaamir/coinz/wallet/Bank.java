package net.raeesaamir.coinz.wallet;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;

import net.raeesaamir.coinz.game.Container;
import net.raeesaamir.coinz.game.FeatureCollection;

import java.util.List;

public class Bank extends Container {

    private final String userUid;

    public Bank(String userUid) {
        this.userUid = userUid;
    }

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
