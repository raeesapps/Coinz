package net.raeesaamir.coinz.share;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareButton;
import com.google.common.collect.Lists;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import net.raeesaamir.coinz.CoinzApplication;
import net.raeesaamir.coinz.R;
import net.raeesaamir.coinz.menu.MenuFragment;
import net.raeesaamir.coinz.wallet.Bank;
import net.raeesaamir.coinz.wallet.Wallet;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * This fragment shows you the achievements you've obtained. It gives you the option to share them to Facebook.
 *
 * @author raeesaamir
 */
public class ShareFragment extends Fragment {

    /**
     * The date format used in the map's GeoJSON file and the player's wallet.
     */
    @SuppressLint("SimpleDateFormat")
    private static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("yyyy/MM/dd", Locale.UK);

    /**
     * The context of the fragment.
     */
    private Context context;

    /**
     * The parent activity.
     */
    private FragmentActivity activity;

    /**
     * The list view that will hold your achievements.
     */
    private ListView achievementsView;

    /**
     * The Share to Facebook button.
     */
    private ShareButton shareButton;

    /**
     * The authenticated user.
     */
    private FirebaseUser mUser;

    /**
     * Today's date formatted.
     */
    private String dateFormatted;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.achievementsView = view.findViewById(R.id.achievements);
        this.shareButton = view.findViewById(R.id.shareButton);

        if (!CoinzApplication.isInternetConnectionAvailable(context)) {
            activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MenuFragment()).commit();

            AlertDialog internetConnectionHangedDialog = new AlertDialog.Builder(activity).setTitle("Game").setMessage("Your internet connection has hanged. Try again later when it's backup and running!").setPositiveButton("Close", (x, y) -> {
            }).create();
            internetConnectionHangedDialog.show();
        } else {
            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            mUser = mAuth.getCurrentUser();

            long date = new Date().getTime();
            dateFormatted = DATE_FORMATTER.format(date);

            fetchAchievements();

        }
    }

    /**
     * Fetches all the user's achievements from the bank and the user's wallet. Sets up the sharing functionality.
     */
    private void fetchAchievements() {

        Wallet.loadWallet(mUser.getUid(), dateFormatted, (Wallet wallet) -> Bank.loadBank(mUser.getUid(), (Bank bank) -> {
            List<String> achievements = Lists.newArrayList();
            StringBuilder achievement = new StringBuilder();

            double goldCollected = bank.totalGold();
            achievement.append("I collected ").append(goldCollected).append(" gold from playing Coinz.");
            achievement.append("\n I collected many coins today: ");

            achievements.add("GOLD - " + bank.totalGold());

            for (String coin : wallet.getCoins()) {
                achievement.append("\n").append(coin);
                achievements.add(coin);
            }

            ShareLinkContent content = new ShareLinkContent.Builder()
                    .setContentUrl(Uri.parse("http://www.raeesaamir.net"))
                    .setQuote(achievement.toString())
                    .build();

            shareButton.setShareContent(content);

            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, achievements);
            achievementsView.setAdapter(arrayAdapter);

        }, false));
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

        if (context instanceof FragmentActivity) {
            this.activity = (FragmentActivity) context;
        }
    }
}
