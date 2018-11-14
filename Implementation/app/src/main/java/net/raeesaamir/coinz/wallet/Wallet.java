package net.raeesaamir.coinz.wallet;

import com.google.android.gms.tasks.Task;
import com.google.common.base.Objects;
import com.google.common.collect.ImmutableMap;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import net.raeesaamir.coinz.game.Container;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Wallet extends Container {

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
    }

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
    public ImmutableMap<String, Object> getDocument() {
        return new ImmutableMap.Builder<String, Object>().put("userUid", userUid)
                .put("date", date).put("coins", coins).build();
    }

    @Override
    public String getCollectionName() {
        return "Wallets";
    }

    @Override
    public String getDocumentName() {
        return Integer.toString(hashCode());
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
}
