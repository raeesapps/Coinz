package net.raeesaamir.coinz.wallet;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.common.base.Preconditions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;

import net.raeesaamir.coinz.CoinzApplication;
import net.raeesaamir.coinz.R;
import net.raeesaamir.coinz.game.FeatureCollection;
import net.raeesaamir.coinz.menu.MenuFragment;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

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
     * The parent activity.
     */
    private FragmentActivity activity;

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

        if (!CoinzApplication.isInternetConnectionAvailable(context)) {
            activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MenuFragment()).commit();

            AlertDialog internetConnectionHangedDialog = new AlertDialog.Builder(activity).setTitle("Game").setMessage("Your internet connection has hanged. Try again later when it's backup and running!").setPositiveButton("Close", (x, y) -> {
            }).create();
            internetConnectionHangedDialog.show();
        } else {
            SharedPreferences sharedPreferences = activity.getSharedPreferences(SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE);
            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            Gson gson = new Gson();
            this.mUser = mAuth.getCurrentUser();

            long date = new Date().getTime();
            String dateFormatted = DATE_FORMATTER.format(date);

            try {
                featureCollection = FeatureCollection.fromWebsite(sharedPreferences, gson, mAuth.getUid(), dateFormatted);

                if (featureCollection == null) {
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MenuFragment()).commit();

                    AlertDialog internetConnectionHangedDialog = new AlertDialog.Builder(activity).setTitle("Game").setMessage("Your internet connection has hanged. Try again later when it's backup and running!").setPositiveButton("Close", (x, y) -> {
                    }).create();
                    internetConnectionHangedDialog.show();
                } else {
                    populateWallet();
                    populateBank();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
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

                if (CoinzApplication.isInternetConnectionAvailable(context)) {
                    String coin = wallet.getCoins().get(i);
                    wallet.removeCoin(coin);
                    bank.deposit(featureCollection.getRates(), coin);
                    refreshBank();
                    integerArrayAdapter.remove(coin);

                } else {
                    AlertDialog internetConnectionHangedDialog = new AlertDialog.Builder(activity).setTitle("Game").setMessage("Your internet connection has hanged. Try again later when it's backup and running!").setPositiveButton("Close", (x, y) -> {
                    }).create();
                    internetConnectionHangedDialog.show();
                }
            });
        });
    }

    /**
     * Populates the text view with the total amount of gold in the player's bank.
     */
    private void populateBank() {
        this.bankView = view.findViewById(R.id.bank);
        Bank.loadBank(mUser.getUid(), (Bank bank) -> {
            this.bank = bank;
            refreshBank();
        }, false);
    }

    /**
     * Refreshes the bank.
     */
    private void refreshBank() {
        Preconditions.checkNotNull(this.bankView);
        Preconditions.checkNotNull(this.bank);
        bankView.setText(String.format("TOTAL GOLD IN BANK: %s", bank.totalGold()));
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;

        if (context instanceof FragmentActivity) {
            this.activity = (FragmentActivity) context;
        }
    }
}
