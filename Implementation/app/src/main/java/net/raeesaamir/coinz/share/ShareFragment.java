package net.raeesaamir.coinz.share;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareButton;
import com.google.android.gms.tasks.Task;
import com.google.common.collect.Lists;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import net.raeesaamir.coinz.R;
import net.raeesaamir.coinz.wallet.Bank;
import net.raeesaamir.coinz.wallet.Wallet;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class ShareFragment extends Fragment {

    private static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("yyyy/MM/dd");

    private Context context;

    private ListView achievementsView;
    private ShareButton shareButton;
    private FirebaseUser mUser;

    private Bank bank;
    private String dateFormatted;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.achievementsView = view.findViewById(R.id.achievements);
        this.shareButton = view.findViewById(R.id.shareButton);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        long date = new Date().getTime();
        dateFormatted = DATE_FORMATTER.format(date);

        fetchAchievements();
    }

    private void fetchAchievements() {

        Wallet.loadWallet(mUser.getUid(), dateFormatted, (Wallet wallet) -> {
            FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
            CollectionReference banks = firebaseFirestore.collection("Banks");

            banks.get().addOnCompleteListener((@NonNull Task<QuerySnapshot> task) -> {

                List<String> achievements = Lists.newArrayList();
                StringBuilder achievement = new StringBuilder();
                for(DocumentSnapshot snapshot: Objects.requireNonNull(task.getResult())) {

                    System.out.println("[ShareFragment]: querying");

                    if(!snapshot.contains("userUid") || !snapshot.contains("coins")) {
                        continue;
                    }

                    if(!Objects.requireNonNull(snapshot.get("userUid")).equals(mUser.getUid())) {
                        continue;
                    }

                    System.out.println("[ShareFragment] found");

                    Object coinsObj = snapshot.get("coins");
                    if(!(coinsObj instanceof List)) {
                        return;
                    }
                    //noinspection unchecked
                    List<String> coins = (List<String>) coinsObj;

                    this.bank = new Bank(snapshot.getString("userUid"), coins);
                    double goldCollected = bank.totalGold();
                    achievement.append("I collected ").append(goldCollected).append(" gold from playing Coinz.");
                    achievement.append("\n I collected many coins today: ");

                    achievements.add("GOLD - " + bank.totalGold());

                    for(String coin: wallet.getCoins()) {
                        achievement.append("\n").append(coin);
                        achievements.add(coin);
                    }

                    System.out.println("[ShareFragment]: " + achievement);
                }

                ShareLinkContent content = new ShareLinkContent.Builder()
                        .setContentUrl(Uri.parse("http://www.raeesaamir.net"))
                        .setQuote(achievement.toString())
                        .build();

                shareButton.setShareContent(content);

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, achievements);
                achievementsView.setAdapter(arrayAdapter);

            });
        });
    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.share_fragment, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }
}
