package net.raeesaamir.coinz.wallet;

import android.content.SharedPreferences;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;

import net.raeesaamir.coinz.game.FeatureCollection;
import net.raeesaamir.coinz.game.FirestoreContainer;

import java.util.List;

public class Bank extends FirestoreContainer {

    private String userUid;

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

        double exchangeRate = -1;
        if(coinType.equals("SHIL")) {
            exchangeRate = exchangeRates.getShil();
        } else if(coinType.equals("DOLR")) {
            exchangeRate = exchangeRates.getDolr();
        } else if(coinType.equals("PENY")) {
            exchangeRate = exchangeRates.getPeny();
        } else {
            exchangeRate = exchangeRates.getQuid();
        }

        Preconditions.checkArgument(exchangeRate != -1);

        double goldAmount = coinValue * exchangeRate;
        addCoin("GOLD "+goldAmount);
        getFuture();
    }

    public double totalGold() {
        double totalGold = 0;

        for(String coin: coins) {
            String[] coinAttributes = coin.split(" ");

            Preconditions.checkArgument(coinAttributes[0].equals("GOLD"));
            totalGold += Double.parseDouble(coinAttributes[1]);
        }

        return totalGold;
    }

    @Override
    public void removeCoin(String coin) {
        throw new UnsupportedOperationException("NOT IMPLEMENTED FOR BANKS");
    }
}
