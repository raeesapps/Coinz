package net.raeesaamir.coinz.wallet;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.common.base.Objects;
import com.google.common.collect.ImmutableMap;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import net.raeesaamir.coinz.game.FirestoreContainer;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Wallet extends FirestoreContainer {

    public interface WalletListener {
        void onComplete();
    }

    public static class WalletSingleton {
        private static Wallet wallet = null;
        private static Wallet otherWallet = null;

        public static Wallet getWallet(){
            return wallet;
        }

        public static Wallet getOtherWallet() {
            return otherWallet;
        }

        public static void setWallet(Wallet wallet) {
            WalletSingleton.wallet = wallet;
        }

        public static void setOtherWallet(Wallet otherWallet) {
            WalletSingleton.otherWallet = otherWallet;
        }
    }

    public static void loadWallet(String uid, String date, WalletListener listener) {
        loadWallet(uid, date, listener, false);
    }

    public static void loadWallet(String uid, String date, WalletListener listener, boolean otherPlayer) {

        System.out.println("[Wallet] loadWallet");

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference wallets = db.collection("Wallets");
        DocumentReference reference = wallets.document();

        if(otherPlayer) {
            if(WalletSingleton.otherWallet != null) {
                System.out.println("[Wallet] not null!");
                listener.onComplete();
                return;
            }
        } else {
            if(WalletSingleton.wallet != null) {
                System.out.println("[Wallet] not null!");
                listener.onComplete();
                return;
            }
        }

        wallets.get().addOnCompleteListener((@NonNull Task<QuerySnapshot> task) -> {

            Wallet wallet = new Wallet(uid, reference.getId());
            if(task.isSuccessful()) {
                for(DocumentSnapshot snapshot: task.getResult()) {

                    if(!snapshot.contains("userUid") || !snapshot.contains("date")
                            ||!snapshot.contains("walletUid") ||!snapshot.contains("coins")) {
                        continue;
                    }

                    if(!snapshot.get("userUid").equals(uid) || !snapshot.get("date").equals(date)) {
                        continue;
                    }

                    String walletUid = snapshot.getString("walletUid");

                    Object coinsObj = snapshot.get("coins");
                    if(coinsObj instanceof List) {
                        List<String> coins = (List<String>) coinsObj;
                        wallet = new Wallet(uid, date, coins, walletUid);
                        break;
                    }
                }

                if(otherPlayer) {
                    WalletSingleton.setOtherWallet(wallet);
                } else {
                    WalletSingleton.setWallet(wallet);

                }
                listener.onComplete();
            }
        });
    }

    private static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("yyyy/MM/dd");

    private String date;
    private String userUid;
    private String walletUid;

    public Wallet(String userUid, String walletUid) {
        long date = new Date().getTime();

        this.date = DATE_FORMATTER.format(date);
        this.userUid = userUid;
        this.walletUid = walletUid;
    }

    public Wallet(String userUid, String date, List<String> coins, String walletUid) {
        this.userUid = userUid;
        this.date = date;
        this.coins = coins;
        this.walletUid = walletUid;
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
    public List<String> getCoins() {
        return coins;
    }

    @Override
    public ImmutableMap<String, Object> getDocument() {
        return ImmutableMap.<String, Object>builder().put("userUid", userUid).put("coins", coins).put("walletUid", walletUid).put("date", date).build();
    }

    @Override
    public String getDocumentName() {
        return walletUid;
    }

    @Override
    public String getCollectionName() {
        return "Wallets";
    }
}
