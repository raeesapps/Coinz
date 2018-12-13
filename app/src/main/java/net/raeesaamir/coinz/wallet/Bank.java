package net.raeesaamir.coinz.wallet;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.Task;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import net.raeesaamir.coinz.game.Container;
import net.raeesaamir.coinz.game.ExchangeRates;

import java.util.List;
import java.util.Objects;

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
    private Bank(String userUid) {
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

    /**
     * Loads the bank of the player or it loads the bank of the other player.
     *
     * @param uid         - The player's UID.
     * @param listener    - The bank listener.
     * @param otherPlayer - True if we want to load the bank of the other player, otherwise false.
     */
    public static void loadBank(String uid, BankListener listener, boolean otherPlayer) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference banks = db.collection("Banks");

        if (otherPlayer) {
            if (Banks.getOtherBank() != null) {
                listener.onComplete(Banks.getOtherBank());
                return;
            }
        } else {
            if (Banks.getBank() != null) {
                listener.onComplete(Banks.getBank());
                return;
            }
        }

        banks.get().addOnCompleteListener((@NonNull Task<QuerySnapshot> task) -> {
            if (task.isSuccessful()) {
                Bank bank = new Bank(uid);
                for (DocumentSnapshot snapshot : Objects.requireNonNull(task.getResult())) {

                    if (!snapshot.contains("userUid") || !snapshot.contains("coins")) {
                        continue;
                    }

                    System.out.println("BANK" + snapshot.get("userUid"));

                    if (!Objects.requireNonNull(snapshot.get("userUid")).equals(uid)) {
                        continue;
                    }
                    Object coinsObj = snapshot.get("coins");
                    if (!(coinsObj instanceof List)) {
                        return;
                    }

                    //noinspection unchecked
                    List<String> coins = (List<String>) coinsObj;
                    bank = new Bank(uid, coins);
                    break;

                }
                if (otherPlayer) {
                    Banks.setOtherBank(bank);
                } else {
                    Banks.setBank(bank);
                }

                listener.onComplete(bank);
            }

        });
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
     * Deposits a coin into the bank based on today's exchange rates.
     *
     * @param exchangeRates - The exchange rates.
     * @param coin          - The coin to deposit.
     */
    public void deposit(ExchangeRates exchangeRates, String coin) {
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

    /**
     * Represents the listener that is called when the wallet is loaded.
     *
     * @author raeesaamir
     */
    public interface BankListener {

        /**
         * Called when the bank is loaded.
         *
         * @param bank - The bank.
         */
        void onComplete(Bank bank);
    }

}
