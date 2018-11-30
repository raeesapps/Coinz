package net.raeesaamir.coinz.wallet;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.Task;
import com.google.common.base.Objects;
import com.google.common.collect.ImmutableMap;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import net.raeesaamir.coinz.game.Container;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Wallet extends Container {

    private static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("yyyy/MM/dd");
    private final String date;
    private final String userUid;
    private final String walletUid;

    private Wallet(String userUid, String walletUid) {
        long date = new Date().getTime();

        this.date = DATE_FORMATTER.format(date);
        this.userUid = userUid;
        this.walletUid = walletUid;
    }

    private Wallet(String userUid, String date, List<String> coins, String walletUid) {
        this.userUid = userUid;
        this.date = date;
        this.coins = coins;
        this.walletUid = walletUid;
    }

    public static void loadWallet(String uid, String date, WalletListener listener) {
        loadWallet(uid, date, listener, false);
    }

    @SuppressWarnings("unchecked")
    public static void loadWallet(String uid, String date, WalletListener listener, boolean otherPlayer) {

        System.out.println("[Wallet] loadWallet");

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference wallets = db.collection("Wallets");
        DocumentReference reference = wallets.document();

        if (otherPlayer) {
            if (WalletSingleton.otherWallet != null) {
                System.out.println("[Wallet] not null!");
                listener.onComplete(WalletSingleton.otherWallet);
                return;
            }
        } else {
            if (WalletSingleton.wallet != null) {
                System.out.println("[Wallet] not null!");
                listener.onComplete(WalletSingleton.wallet);
                return;
            }
        }

        wallets.get().addOnCompleteListener((@NonNull Task<QuerySnapshot> task) -> {

            Wallet wallet = new Wallet(uid, reference.getId());
            if (task.isSuccessful()) {
                for (DocumentSnapshot snapshot : java.util.Objects.requireNonNull(task.getResult())) {

                    if (!snapshot.contains("userUid") || !snapshot.contains("date")
                            || !snapshot.contains("walletUid") || !snapshot.contains("coins")) {
                        continue;
                    }

                    if (!java.util.Objects.requireNonNull(snapshot.get("userUid")).equals(uid) || !java.util.Objects.requireNonNull(snapshot.get("date")).equals(date)) {
                        continue;
                    }

                    String walletUid = snapshot.getString("walletUid");

                    Object coinsObj = snapshot.get("coins");
                    if (coinsObj instanceof List) {
                        List<String> coins = (List<String>) coinsObj;
                        wallet = new Wallet(uid, date, coins, walletUid);
                        break;
                    }
                }

                if (otherPlayer) {
                    WalletSingleton.setOtherWallet(wallet);
                } else {
                    WalletSingleton.setWallet(wallet);

                }
                listener.onComplete(wallet);
            }
        });
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (!(obj instanceof Wallet)) {
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

    public interface WalletListener {
        void onComplete(Wallet wallet);
    }

    public static class WalletSingleton {
        private static Wallet wallet = null;
        private static Wallet otherWallet = null;

        static void setWallet(Wallet wallet) {
            WalletSingleton.wallet = wallet;
        }

        public static void setOtherWallet(Wallet otherWallet) {
            WalletSingleton.otherWallet = otherWallet;
        }
    }
}
