package net.raeesaamir.coinz.leaderboard;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.tasks.Task;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import net.raeesaamir.coinz.R;
import net.raeesaamir.coinz.wallet.Bank;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class LeaderboardFragment extends Fragment {

    private Context context;

    private View view;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = view;
        populateScores();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.leaderboard_fragment, container, false);
    }

    private void populateScores() {
        ListView scoresView = view.findViewById(R.id.scoresFragment);

        Map<String, Double> uidTotalMappings = Maps.newHashMap();
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        CollectionReference banks = firebaseFirestore.collection("Banks");

        banks.get().addOnCompleteListener((@NonNull Task<QuerySnapshot> task) -> {

            for(DocumentSnapshot snapshot: Objects.requireNonNull(task.getResult())) {

                if(!snapshot.contains("userUid") || !snapshot.contains("coins")) {
                    continue;
                }

                Object coinsObj = snapshot.get("coins");
                if(!(coinsObj instanceof List)) {
                    return;
                }
                @SuppressWarnings("unchecked") List<String> coins = (List<String>) coinsObj;
                Bank bank = new Bank(snapshot.getString("userUid"), coins);

                uidTotalMappings.put(snapshot.getString("userUid"), bank.totalGold());
            }

            CollectionReference users = firebaseFirestore.collection("Users");
            List<String> scores = Lists.newArrayList();

            users.get().addOnCompleteListener((@NonNull Task<QuerySnapshot> userTask) -> {

                for(DocumentSnapshot snapshot: Objects.requireNonNull(userTask.getResult())) {

                    String uid = snapshot.getString("uid");
                    String username = snapshot.getString("displayName");

                    if(uidTotalMappings.containsKey(uid)) {
                        String entry = username + " - " + uidTotalMappings.get(uid);
                        scores.add(entry);
                    }
                }
                ArrayAdapter<String> integerArrayAdapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, scores);
                scoresView.setAdapter(integerArrayAdapter);

            });
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }
}
