package net.raeesaamir.coinz.wallet;

import android.content.SharedPreferences;

import com.google.android.gms.tasks.Task;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;

import net.raeesaamir.coinz.game.FeatureCollection;
import net.raeesaamir.coinz.game.FirestoreContainer;

import java.util.List;

public class Bank extends FirestoreContainer {

    public static Bank fromDatabase(String uid) {

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference banks = db.collection("Banks");

        Task<QuerySnapshot> snapshotTask = banks.whereEqualTo("userUid", uid).get();

        if(snapshotTask.isSuccessful()) {
            List<DocumentSnapshot> results = snapshotTask.getResult().getDocuments();
            DocumentSnapshot snapshot = results.get(0);

            Object coinsObj = snapshot.get("coins");
            if(!(coinsObj instanceof List)) {
                return null;
            }

            List<String> coins = (List<String>) coinsObj;
            return new Bank(uid, coins);
        } else {
            return new Bank(uid);
        }
    }

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
        return "Banks";
    }

    @Override
    public String getCollectionName() {
        return userUid;
    }

    public void deposit(SharedPreferences sharedPreferences, Gson gson, FeatureCollection.ExchangeRates exchangeRates, String coin, Wallet wallet) {
        wallet.removeCoin(coin);
        wallet.saved(gson, sharedPreferences);

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
