package net.raeesaamir.coinz.wallet;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.Task;
import com.google.common.base.Preconditions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;

import net.raeesaamir.coinz.R;
import net.raeesaamir.coinz.game.FeatureCollection;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

/**
 * The fragment which handles the rendering of all the coins in the wallet.
 *
 * @author raeesaamir
 */
public class WalletFragment extends Fragment {

    /**
     * The date format used in the map's GeoJSON file and the player's wallet.
     */
    @SuppressLint("SimpleDateFormat")
    private static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("yyyy/MM/dd", Locale.UK);

    /**
     * The unique identifier of the shared preferences object
     */
    private static final String SHARED_PREFERENCES_KEY = "FeatureCollection_Shared_Prefs";

    /**
     * The view object returned from onViewCreated.
     */
    private View view;

    /**
     * The context of the fragment.
     */
    private Context context;

    /**
     * The player's bank.
     */
    private Bank bank;

    /**
     * The authenticated user.
     */
    private FirebaseUser mUser;

    /**
     * The features of today's map. This object contains today's exchange rates.
     */
    private FeatureCollection featureCollection;

    /**
     * The text view which contains the bank.
     */
    private TextView bankView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.wallet_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = view;
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        this.mUser = mAuth.getCurrentUser();
        Gson gson = new Gson();
        SharedPreferences sharedPreferences = Objects.requireNonNull(getActivity()).getSharedPreferences(SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE);
        long date = new Date().getTime();
        String dateFormatted = DATE_FORMATTER.format(date);

        try {
            featureCollection = FeatureCollection.fromWebsite(sharedPreferences, gson, dateFormatted);
        } catch (Exception e) {
            e.printStackTrace();
        }
        populateWallet();
        populateBank();
    }

    /**
     * Populates the list view with the coins from the player's wallet.
     */
    private void populateWallet() {
        long date = new Date().getTime();
        String dateFormatted = DATE_FORMATTER.format(date);
        Wallet.loadWallet(mUser.getUid(), dateFormatted, (Wallet wallet) -> {

            for (String coin : wallet.getCoins()) {
                System.out.println("[WalletFragment]: " + coin);
            }
            ListView walletView = view.findViewById(R.id.wallet_items);

            ArrayAdapter<String> integerArrayAdapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, wallet.getCoins());
            walletView.setAdapter(integerArrayAdapter);

            walletView.setOnItemClickListener((AdapterView<?> adapterView, View view, int i, long l) -> {
                String coin = wallet.getCoins().get(i);

                bank.deposit(featureCollection.getRates(), coin, wallet);
                refreshBank();
                integerArrayAdapter.remove(coin);
            });
        });
    }

    /**
     * Populates the text view with the total amount of gold in the player's bank.
     */
    private void populateBank() {
        this.bankView = view.findViewById(R.id.bank);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference banks = db.collection("Banks");

        System.out.println("[BANK] FROM DATABASE");
        System.out.println("[BANK]: UID=" + mUser.getUid());


        banks.get().addOnCompleteListener((@NonNull Task<QuerySnapshot> task) -> {
            if (!task.isSuccessful()) {
                System.out.println("[BANK]: NOT SUCCESSFUL");
                return;
            }
            this.bank = new Bank(mUser.getUid());
            for (DocumentSnapshot snapshot : Objects.requireNonNull(task.getResult())) {

                if (!snapshot.contains("userUid") || !snapshot.contains("coins")) {
                    continue;
                }

                System.out.println("BANK" + snapshot.get("userUid"));

                if (!Objects.requireNonNull(snapshot.get("userUid")).equals(mUser.getUid())) {
                    continue;
                }
                Object coinsObj = snapshot.get("coins");
                if (!(coinsObj instanceof List)) {
                    return;
                }

                //noinspection unchecked
                List<String> coins = (List<String>) coinsObj;
                this.bank = new Bank(mUser.getUid(), coins);
                break;

            }
            refreshBank();


        });
    }

    /**
     * Refreshes the bank.
     */
    private void refreshBank() {
        Preconditions.checkNotNull(this.bankView);
        Preconditions.checkNotNull(this.bank);
        bankView.setText(String.format("TOTAL GOLD: %s", bank.totalGold()));
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }
}
