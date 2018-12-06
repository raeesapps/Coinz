package net.raeesaamir.coinz.leaderboard;

import android.app.ProgressDialog;
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

/**
 * This fragment handles the rendering of the leaderboard.
 *
 * @author raeesaamir
 */
public class LeaderboardFragment extends Fragment {

    /**
     * The context of the fragment.
     */
    private Context context;

    /**
     * The view returned from onViewCreated.
     */
    private View view;

    /**
     * The loading dialog.
     */
    private ProgressDialog dialog;

    /**
     * Used to sort the leaderboard according to the number of coins each user has
     *
     * @param t  - The number of coins this user has
     * @param t1 - The number of coins the other user has.
     * @return A sorting value.
     */
    private static int compare(String t, String t1) {
        String[] tAttributes = t.split(" - ");
        String[] t1Attributes = t1.split(" - ");

        double tCoins = Double.parseDouble(tAttributes[1]);
        double t1Coins = Double.parseDouble(t1Attributes[1]);

        return Double.compare(t1Coins, tCoins);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = view;
        dialog = ProgressDialog.show(context, "",
                "Loading. Please wait...", true);
        dialog.show();
        populateScores();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.leaderboard_fragment, container, false);
    }

    /**
     * Populates each player's gold into the leaderboard view. If the player has not deposited anything into their bank then their total gold will be 0.
     */
    private void populateScores() {
        ListView scoresView = view.findViewById(R.id.scoresFragment);

        Map<String, Double> uidTotalMappings = Maps.newHashMap();
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        CollectionReference banks = firebaseFirestore.collection("Banks");

        banks.get().addOnCompleteListener((@NonNull Task<QuerySnapshot> task) -> {

            for (DocumentSnapshot snapshot : Objects.requireNonNull(task.getResult())) {

                if (!snapshot.contains("userUid") || !snapshot.contains("coins")) {
                    continue;
                }

                Object coinsObj = snapshot.get("coins");
                if (!(coinsObj instanceof List)) {
                    return;
                }
                @SuppressWarnings("unchecked") List<String> coins = (List<String>) coinsObj;
                Bank bank = new Bank(snapshot.getString("userUid"), coins);

                uidTotalMappings.put(snapshot.getString("userUid"), bank.totalGold());
            }

            CollectionReference users = firebaseFirestore.collection("Users");
            List<String> scores = Lists.newArrayList();

            users.get().addOnCompleteListener((@NonNull Task<QuerySnapshot> userTask) -> {

                for (DocumentSnapshot snapshot : Objects.requireNonNull(userTask.getResult())) {

                    if (!snapshot.contains("uid") || !snapshot.contains("displayName")) {
                        continue;
                    }

                    String uid = snapshot.getString("uid");
                    String username = snapshot.getString("displayName");

                    if (uidTotalMappings.containsKey(uid)) {
                        String entry = username + " - " + uidTotalMappings.get(uid);
                        scores.add(entry);
                    } else {
                        scores.add(username + " - " + "0");
                    }
                }

                scores.sort(LeaderboardFragment::compare);

                ArrayAdapter<String> integerArrayAdapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, scores);
                scoresView.setAdapter(integerArrayAdapter);

            });
            dialog.dismiss();
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }
}
