package net.raeesaamir.coinz.wallet;

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

public class WalletFragment extends Fragment {

    private static final String SHARED_PREFERENCES_KEY = "FeatureCollection_Shared_Preferences";
    private static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("yyyy/MM/dd");

    private View view;
    private Bank bank;
    private Gson gson;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private SharedPreferences sharedPreferences;
    private FeatureCollection featureCollection;

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
        this.mAuth = FirebaseAuth.getInstance();
        this.mUser = mAuth.getCurrentUser();
        this.gson = new Gson();
        this.sharedPreferences = getActivity().getSharedPreferences(SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE);
        long date = new Date().getTime();
        String dateFormatted = DATE_FORMATTER.format(date);

        try {
            featureCollection = FeatureCollection.fromWebsite(sharedPreferences, gson, dateFormatted);
        } catch(Exception e) {
            e.printStackTrace();
        }
        populateWallet();
        populateBank();
    }

    private void populateWallet() {
        long date = new Date().getTime();
        String dateFormatted = DATE_FORMATTER.format(date);
        Wallet.loadWallet(mUser.getUid(), dateFormatted, () -> {

            Wallet wallet = Wallet.WalletSingleton.getWallet();

            for(String coin: wallet.getCoins()) {
                System.out.println("[WalletFragment]: "+ coin);
            }
            ListView walletView = view.findViewById(R.id.wallet_items);

            ArrayAdapter<String> integerArrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, wallet.getCoins());
            walletView.setAdapter(integerArrayAdapter);

            walletView.setOnItemClickListener((AdapterView<?> adapterView, View view, int i, long l) -> {
                String coin = wallet.getCoins().get(i);

                bank.deposit(sharedPreferences, gson, featureCollection.getRates(), coin, wallet);
                refreshBank();
                integerArrayAdapter.remove(coin);
            });
        });
    }

    private void populateBank() {
        this.bankView = view.findViewById(R.id.bank);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference banks = db.collection("Banks");

        System.out.println("[BANK] FROM DATABASE");
        System.out.println("[BANK]: UID="+mUser.getUid());


        banks.get().addOnCompleteListener((@NonNull Task<QuerySnapshot> task) -> {
            if(!task.isSuccessful()) {
                System.out.println("[BANK]: NOT SUCCESSFUL");
                return;
            }
            this.bank = new Bank(mUser.getUid());
            for(DocumentSnapshot snapshot: task.getResult()) {

                if(!snapshot.contains("userUid") || !snapshot.contains("coins")) {
                    continue;
                }

                System.out.println("BANK"+snapshot.get("userUid"));

                if(!snapshot.get("userUid").equals(mUser.getUid())) {
                    continue;
                }
                Object coinsObj = snapshot.get("coins");
                if(!(coinsObj instanceof List)) {
                    return;
                }

                List<String> coins = (List<String>) coinsObj;
                this.bank = new Bank(mUser.getUid(), coins);
                break;

            }
            refreshBank();


        });
    }

    private void refreshBank() {
        Preconditions.checkNotNull(this.bankView);
        Preconditions.checkNotNull(this.bank);
        bankView.setText("TOTAL GOLD: " + bank.totalGold());
    }
}
