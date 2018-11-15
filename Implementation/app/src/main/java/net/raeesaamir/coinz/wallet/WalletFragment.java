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
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;

import net.raeesaamir.coinz.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class WalletFragment extends Fragment {

    private static final String SHARED_PREFERENCES_KEY = "FeatureCollection_Shared_Preferences";
    private static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("yyyy/MM/dd");

    private View view;
    private Wallet wallet;
    private Gson gson;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private SharedPreferences sharedPreferences;

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
        populateWallet();
    }

    private void populateWallet() {
        long date = new Date().getTime();
        String dateFormatted = DATE_FORMATTER.format(date);
        this.wallet = Wallet.fromSharedPreferences(sharedPreferences, gson, mUser.getUid(), dateFormatted);

        ListView walletView = view.findViewById(R.id.wallet_items);

        ArrayAdapter<String> integerArrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, wallet.getCoins());
        walletView.setAdapter(integerArrayAdapter);
    }
}
