package net.raeesaamir.coinz.wallet;

import android.content.SharedPreferences;

import com.google.common.base.Objects;
import com.google.gson.Gson;

import net.raeesaamir.coinz.game.LocalContainer;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Wallet extends LocalContainer {

    public static Wallet fromSharedPreferences(SharedPreferences sharedPreferences, Gson gson, String uid, String date) {

        String key = uid + "_" + date;

        System.out.println("[fromSharedPreferences]: "+sharedPreferences.contains(key));

        if(sharedPreferences.contains(key)) {
            String json = sharedPreferences.getString(key, "");
            System.out.println("[JSONLOL]: "+json);
            Wallet wallet = gson.fromJson(json, Wallet.class);
            return wallet;
        } else {
            return new Wallet(uid);
        }
    }

    /*
    public static Wallet fromDatabase(String uid, String date) {

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference wallets = db.collection("Wallets");

        Task<QuerySnapshot> snapshotTask = wallets.whereEqualTo("date", date).whereEqualTo("userUid", uid).get();

        if(snapshotTask.isSuccessful()) {
            List<DocumentSnapshot> results = snapshotTask.getResult().getDocuments();
            DocumentSnapshot snapshot = results.get(0);

            Object coinsObj = snapshot.get("coins");
            if(!(coinsObj instanceof List)) {
                return null;
            }

            List<String> coins = (List<String>) coinsObj;
            return new Wallet(uid, date, coins);
        } else {
            return new Wallet(uid);
        }
    }*/

    private static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("yyyy/MM/dd");

    private String date;
    private String userUid;

    public Wallet(String userUid) {
        long date = new Date().getTime();

        this.date = DATE_FORMATTER.format(date);
        this.userUid = userUid;
    }

    public Wallet(String userUid, String date, List<String> coins) {
        this.userUid = userUid;
        this.date = date;
        this.coins = coins;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) {
            return false;
        }

        if(!(obj instanceof Wallet)) {
            return false;
        }

        Wallet otherWallet = (Wallet) obj;
        return Objects.equal(this.date, otherWallet.date) && Objects.equal(this.userUid, otherWallet.userUid);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(date, userUid);
    }

    public boolean saved(Gson gson, SharedPreferences sharedPreferences) {
        String key = userUid + "_" + date;
        String json = gson.toJson(this);
        return sharedPreferences.edit().putString(key, json).commit();
    }
}
