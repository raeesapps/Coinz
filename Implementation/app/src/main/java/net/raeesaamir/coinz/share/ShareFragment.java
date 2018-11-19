package net.raeesaamir.coinz.share;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;

import net.raeesaamir.coinz.R;
import net.raeesaamir.coinz.wallet.Bank;
import net.raeesaamir.coinz.wallet.Wallet;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ShareFragment extends Fragment {

    private static final String SHARED_PREFERENCES_KEY = "FeatureCollection_Shared_Preferences";
    private static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("yyyy/MM/dd");


    private View view;
    private TextView shareText;
    private Button shareButton;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

    private Bank bank;
    private Wallet wallet;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = view;
        this.shareText = view.findViewById(R.id.shareText);
        this.shareButton = view.findViewById(R.id.shareButton);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        Gson gson = new Gson();
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE);

        long date = new Date().getTime();
        String dateFormatted = DATE_FORMATTER.format(date);
        wallet = Wallet.fromSharedPreferences(sharedPreferences, gson, mUser.getUid(), dateFormatted);

        fetchAchievements();

        shareButton.setOnClickListener((View viewParam) -> {

        });
    }

    private void fetchAchievements() {

        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        CollectionReference banks = firebaseFirestore.collection("Banks");

        banks.get().addOnCompleteListener((@NonNull Task<QuerySnapshot> task) -> {

            for(DocumentSnapshot snapshot: task.getResult()) {

                System.out.println("[ShareFragment]: querying");

                if(!snapshot.contains("userUid") || !snapshot.contains("coins")) {
                    continue;
                }

                if(!snapshot.get("userUid").equals(mUser.getUid())) {
                    continue;
                }

                System.out.println("[ShareFragment] found");

                Object coinsObj = snapshot.get("coins");
                if(!(coinsObj instanceof List)) {
                    return;
                }
                List<String> coins = (List<String>) coinsObj;

                String achievement = "";

                this.bank = new Bank(snapshot.getString("userUid"), coins);
                double goldCollected = bank.totalGold();
                achievement = achievement + "I collected " + goldCollected + " gold from playing Coinz today.";
                achievement = achievement + "\n I also managed to collect several other coins: ";

                for(String coin: wallet.getCoins()) {
                    achievement = achievement + "\n" + coin;
                }

                System.out.println("[ShareFragment]: " + achievement);

                shareText.setText(achievement);
            }

        });
    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.share_fragment, container, false);
    }
}
