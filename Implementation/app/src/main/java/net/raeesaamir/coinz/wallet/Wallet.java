package net.raeesaamir.coinz.wallet;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.Task;
import com.google.common.base.Objects;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import net.raeesaamir.coinz.game.Container;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Represents the player's wallet. Stored in cloud-firestore.
 *
 * @author raeesaamir
 */
public class Wallet extends Container {

    /**
     * The date format used in the map's GeoJSON file and the player's wallet.
     */
    @SuppressLint("SimpleDateFormat")
    private static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("yyyy/MM/dd", Locale.UK);

    /**
     * The date of the wallet.
     */
    private final String date;

    /**
     * The UUID of the player.
     */
    private final String userUid;

    /**
     * The UUID of the wallet.
     */
    private final String walletUid;

    /**
     * The purpose of the wallet.
     */
    private final WalletType walletType;

    /**
     * Constructs an empty wallet for today's date based on a defined type..
     *
     * @param userUid    - The user's UUID
     * @param walletUid  - The player's UUID
     * @param walletType - The type of wallet.
     */
    private Wallet(String userUid, String walletUid, WalletType walletType) {
        this(userUid, DATE_FORMATTER.format(new Date().getTime()), Lists.newArrayList(), walletUid, walletType);
    }

    /**
     * Constructs a wallet based on a list of coins.
     *
     * @param userUid   - The player's UUID.
     * @param date      - The date of the wallet.
     * @param coins     - The coins in the wallet.
     * @param walletUid - The wallet's UUID.
     */
    private Wallet(String userUid, String date, List<String> coins, String walletUid) {
        this(userUid, date, coins, walletUid, WalletType.MAIN_WALLET);
    }

    /**
     * Constructs a wallet based on a list of coins and type.
     *
     * @param userUid    - The player's UUID.
     * @param date       - The date of the wallet.
     * @param coins      - The coins in the wallet.
     * @param walletUid  - The wallet's UUID.
     * @param walletType - The type of wallet.
     */
    private Wallet(String userUid, String date, List<String> coins, String walletUid, WalletType walletType) {
        this.userUid = userUid;
        this.date = date;
        this.coins = coins;
        this.walletUid = walletUid;
        this.walletType = walletType;
    }

    /**
     * Gets the wallet type from an integer value representing it's declaration order.
     *
     * @param ordinal - The type declaration order
     * @return A wallet type.
     */
    private static WalletType fromOrdinal(int ordinal) {
        return ordinal == 0 ? WalletType.MAIN_WALLET : WalletType.SPARE_CHANGE_WALLET;
    }

    /**
     * Loads this player's wallet.
     *
     * @param uid      - The UUID of this player.
     * @param date     - The date of the wallet we want.
     * @param listener - The on complete listener.
     */
    public static void loadWallet(String uid, String date, WalletListener listener) {
        loadWallet(uid, date, WalletType.MAIN_WALLET, listener, false);
    }

    /**
     * Loads this player's wallet.
     *
     * @param uid        - The UUID of this player.
     * @param date       - The date of the wallet we want.
     * @param walletType - The type of wallet to load
     * @param listener   - The on complete listener.
     */
    public static void loadWallet(String uid, String date, WalletType walletType, WalletListener listener) {
        loadWallet(uid, date, walletType, listener, false);
    }

    /**
     * Loads the wallet of this player or the wallet of the other player.
     *
     * @param uid         - The UUID of the player.
     * @param date        - The date of the wallet we want.
     * @param walletType  - The type of wallet to load.
     * @param listener    - The on complete listener.
     * @param otherPlayer - A boolean value which is true if we want to load the other player's wallet.
     */
    @SuppressWarnings("unchecked")
    public static void loadWallet(String uid, String date, WalletType walletType, WalletListener listener, boolean otherPlayer) {

        System.out.println("[Wallet] loadWallet");

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference wallets = db.collection("Wallets");
        DocumentReference reference = wallets.document();

        if (otherPlayer) {
            if (Wallets.getOtherWallet() != null) {
                System.out.println("[Wallet] not null!");
                listener.onComplete(Wallets.getOtherWallet());
                return;
            }
        } else {
            if (walletType.equals(WalletType.MAIN_WALLET)) {
                if (Wallets.getWallet() != null) {
                    System.out.println("[Wallet] not null!");
                    listener.onComplete(Wallets.getWallet());
                    return;
                }
            } else {
                if (Wallets.getSpareWallet() != null) {
                    System.out.println("[Wallet] not null!");
                    listener.onComplete(Wallets.getSpareWallet());
                    return;
                }
            }
        }

        wallets.get().addOnCompleteListener((@NonNull Task<QuerySnapshot> task) -> {

            Wallet wallet = new Wallet(uid, reference.getId(), walletType);
            if (task.isSuccessful()) {
                for (DocumentSnapshot snapshot : java.util.Objects.requireNonNull(task.getResult())) {

                    if (!snapshot.contains("userUid") || !snapshot.contains("date")
                            || !snapshot.contains("walletUid") || !snapshot.contains("coins")
                            || !snapshot.contains("walletType")) {
                        continue;
                    }

                    if (!java.util.Objects.requireNonNull(snapshot.get("userUid")).equals(uid) || !java.util.Objects.requireNonNull(snapshot.get("date")).equals(date)) {
                        continue;
                    }

                    WalletType actualWalletType = fromOrdinal(java.util.Objects.requireNonNull(snapshot.getLong("walletType")).intValue());

                    if (!actualWalletType.equals(walletType)) {
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
                    Wallets.setOtherWallet(wallet);
                } else {
                    if (walletType.equals(WalletType.MAIN_WALLET)) {
                        Wallets.setWallet(wallet);
                    } else {
                        Wallets.setSpareWallet(wallet);
                    }
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

    /**
     * Gets the coins in the wallet.
     *
     * @return A list containing coins.
     */
    public List<String> getCoins() {
        return coins;
    }

    @Override
    public ImmutableMap<String, Object> getDocument() {
        return ImmutableMap.<String, Object>builder().put("userUid", userUid).put("coins", coins).put("walletUid", walletUid).put("walletType", walletType.ordinal()).put("date", date).build();
    }

    @Override
    public String getDocumentName() {
        return walletUid;
    }

    @Override
    public String getCollectionName() {
        return "Wallets";
    }

    /**
     * Returns the number of coins in the wallet.
     *
     * @return The number of coins.
     */
    public int numberOfCoins() {
        return coins.size();
    }

    /**
     * Represents the listener that is called when the wallet is loaded.
     *
     * @author raeesaamir
     */
    public interface WalletListener {

        /**
         * Called when the wallet is loaded.
         *
         * @param wallet - The wallet object.
         */
        void onComplete(Wallet wallet);
    }
}
