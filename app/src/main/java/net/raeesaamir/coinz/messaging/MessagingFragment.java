package net.raeesaamir.coinz.messaging;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.common.base.Preconditions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;

import net.raeesaamir.coinz.CoinzApplication;
import net.raeesaamir.coinz.R;
import net.raeesaamir.coinz.authentication.FirestoreUser;
import net.raeesaamir.coinz.game.FeatureCollection;
import net.raeesaamir.coinz.menu.MenuFragment;
import net.raeesaamir.coinz.wallet.Bank;
import net.raeesaamir.coinz.wallet.Banks;
import net.raeesaamir.coinz.wallet.Wallet;
import net.raeesaamir.coinz.wallet.WalletType;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

/**
 * The fragment that handles the rendering of messages between the logged in user and some other user.
 *
 * @author raeesaamir
 */
public class MessagingFragment extends Fragment {

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
     * The name of the real-time database.
     */
    private static final String DB_NAME = "coinz-12df3";

    /**
     * A list of messages sorted by time.
     */
    private final List<FirebaseMessage> firestoreMessageList = new SortedList();

    /**
     * The features of today's map such as the exchange rate and the location of the markers.
     */
    private FeatureCollection featureCollection;

    /**
     * The parent activity.
     */
    private FragmentActivity activity;
    /**
     * The time a message was attempted to be sent, in nanoseconds.
     */
    private long msgNanoTime;

    /**
     * The time the previous message was sent, in nanoeseconds.
     */
    private long previousMsgNanoTime = 0;

    /**
     * A reference to the real-time database.
     */
    private DatabaseReference mReference;

    /**
     * The send message button.
     */
    private Button button;

    /**
     * The button to open the trading screen.
     */
    private Button tradeButton;

    /**
     * The text container where you enter messages.
     */
    private EditText messageContents;

    /**
     * The view returned from onViewCreated.
     */
    private View view;

    /**
     * The context of the fragment.
     */
    private Context context;

    /**
     * The authenticated user.
     */
    private FirebaseUser mUser;

    /**
     * The firestore user document of the other user the user is talking to.
     */
    private FirestoreUser otherUser;

    /**
     * The firestore user document of the authenticated user.
     */
    private FirestoreUser thisUser;

    /**
     * The message list adapter which puts messages into the message list.
     */
    private MessageListAdapter simpleMessageListAdapter;

    /**
     * The loading dialog.
     */
    private ProgressDialog dialog;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = view;
        dialog = ProgressDialog.show(context, "",
                "Loading. Please wait...", true);
        dialog.show();

        if (!CoinzApplication.isInternetConnectionAvailable(context)) {
            dialog.dismiss();
            activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MenuFragment()).commit();


            AlertDialog internetConnectionHangedDialog = new AlertDialog.Builder(context).setTitle("Game").setMessage("Your internet connection has hanged. Try again later when it's backup and running!").setPositiveButton("Close", (x, y) -> {
            }).create();
            internetConnectionHangedDialog.show();

        } else {
            Gson gson = new Gson();
            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            long date = new Date().getTime();
            String dateFormatted = DATE_FORMATTER.format(date);

            SharedPreferences preferences = activity.getSharedPreferences(SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE);

            try {
                featureCollection = FeatureCollection.fromWebsite(preferences, gson, mAuth.getUid(), dateFormatted);

                if (featureCollection == null) {

                    if (dialog.isShowing()) {
                        dialog.dismiss();
                    }
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MenuFragment()).commit();

                    AlertDialog internetConnectionHangedDialog = new AlertDialog.Builder(activity).setTitle("Game").setMessage("Your internet connection has hanged. Try again later when it's backup and running!").setPositiveButton("Close", (x, y) -> {
                    }).create();
                    internetConnectionHangedDialog.show();
                } else {
                    this.mUser = mAuth.getCurrentUser();
                    this.thisUser = new FirestoreUser(Objects.requireNonNull(mUser).getEmail(), mUser.getUid(), mUser.getDisplayName());
                    this.button = view.findViewById(R.id.button_chatbox_send);
                    this.tradeButton = view.findViewById(R.id.button_chatbox_trade);
                    this.messageContents = view.findViewById(R.id.edittext_chatbox);
                    listen(activity.getIntent().getStringExtra("username"));
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * The logic for sending a message to another user.
     */
    private void setOnSend() {
        button.setOnClickListener((View view) -> {
            String messageString = messageContents.getText().toString();
            messageContents.setText("");
            msgNanoTime = System.nanoTime();

            if (msgNanoTime - previousMsgNanoTime < 3000000000L) {
                Toast.makeText(context, "Please wait", Toast.LENGTH_SHORT).show();
            } else {

                if (!CoinzApplication.isInternetConnectionAvailable(context)) {
                    AlertDialog internetConnectionHangedDialog = new AlertDialog.Builder(context).setTitle("Game").setMessage("Your internet connection has hanged. Try again later when it's backup and running!").setPositiveButton("Close", (x, y) -> {
                    }).create();
                    internetConnectionHangedDialog.show();
                } else {
                    Preconditions.checkNotNull(mReference);

                    FirebaseMessage message = new FirebaseMessage(messageString, thisUser.getUid(), otherUser.getUid());
                    String key = mReference.push().getKey();
                    mReference.child(Objects.requireNonNull(key)).setValue(message);

                    firestoreMessageList.add(message);
                    simpleMessageListAdapter.notifyDataSetChanged();

                }
            }

            previousMsgNanoTime = msgNanoTime;

        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.messaging_fragment, container, false);
    }

    /**
     * Listens for incoming messages from the other user.
     *
     * @param username - The display name of the other user.
     */
    private void listen(String username) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference users = db.collection("Users");

        users.get().addOnCompleteListener((@NonNull Task<QuerySnapshot> task) -> {

            if (task.isSuccessful()) {

                if (thisUser == null) {
                    this.thisUser = new FirestoreUser(mUser.getEmail(), mUser.getUid(), mUser.getDisplayName());
                }

                for (DocumentSnapshot snapshot : Objects.requireNonNull(task.getResult())) {

                    if (!snapshot.contains("uid") || !snapshot.contains("displayName") ||
                            !snapshot.contains("email")) {
                        continue;
                    }

                    if (Objects.requireNonNull(snapshot.get("displayName")).equals(username)) {
                        String uid = snapshot.getString("uid");
                        String displayName = snapshot.getString("displayName");
                        String email = snapshot.getString("email");
                        otherUser = new FirestoreUser(email, uid, displayName);
                    }
                }


                setOnSend();
                populateMessages();

                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
            }

        });
    }

    /**
     * Adds messages received from the other user to our fragment. This method also handles the trading logic. Trading is transfering coins from this user to the other user.
     */
    private void populateMessages() {
        RecyclerView recyclerView = view.findViewById(R.id.message_list);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        mReference = firebaseDatabase.getReference(DB_NAME);


        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                System.out.println("[MessagingFragment]: onDataChange");
                firestoreMessageList.clear();
                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    FirebaseMessage firestoreMessage = item.getValue(FirebaseMessage.class);

                    if ((Objects.requireNonNull(firestoreMessage).getMessageFromUser().equals(thisUser.getUid()) &&
                            firestoreMessage.getMessageToUser().equals(otherUser.getUid()) ||
                            (firestoreMessage.getMessageFromUser().equals(otherUser.getUid()) &&
                                    firestoreMessage.getMessageToUser().equals(thisUser.getUid())))) {
                        firestoreMessageList.add(firestoreMessage);
                    }
                }

                simpleMessageListAdapter =
                        new MessageListAdapter(firestoreMessageList, otherUser, thisUser.getUid());
                recyclerView.setAdapter(simpleMessageListAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(context));

                long date = new Date().getTime();
                String dateFormatted = DATE_FORMATTER.format(date);
                Wallet.loadWallet(mUser.getUid(), dateFormatted, WalletType.SPARE_CHANGE_WALLET, (Wallet wallet) -> tradeButton.setOnClickListener((View view) -> {
                    List<String> coinsList = wallet.getCoins();

                    String[] coins = coinsList.toArray(new String[coinsList.size()]);
                    boolean[] selectedItems = new boolean[coins.length];

                    AlertDialog alertDialog = new AlertDialog.Builder(context)
                            .setMultiChoiceItems(coins, selectedItems, (DialogInterface dialogInterface, int i, boolean b) -> selectedItems[i] = true).setTitle("Select the coins you want to trade").setPositiveButton("Accept", (DialogInterface dialogInterface, int k) -> Bank.loadBank(otherUser.getUid(), (Bank otherBank) -> {

                                if (!CoinzApplication.isInternetConnectionAvailable(context)) {
                                    AlertDialog internetConnectionHangedDialog = new AlertDialog.Builder(context).setTitle("Game").setMessage("Your internet connection has hanged. Try again later when it's backup and running!").setPositiveButton("Close", (x, y) -> {
                                    }).create();
                                    internetConnectionHangedDialog.show();
                                } else {
                                    for (int i = 0; i < selectedItems.length; i++) {
                                        if (selectedItems[i]) {
                                            String coin = coinsList.get(i);
                                            coins[i] = null;
                                            wallet.removeCoin(coin);

                                            otherBank.deposit(featureCollection.getRates(), coin);

                                            FirebaseMessage message = new FirebaseMessage("You have transferred " + coin + " to " + otherUser.getDisplayName(), thisUser.getUid(), otherUser.getUid());
                                            String key = mReference.push().getKey();
                                            mReference.child(Objects.requireNonNull(key)).setValue(message);

                                            firestoreMessageList.add(message);
                                            simpleMessageListAdapter.notifyDataSetChanged();
                                        }
                                    }
                                    Banks.setOtherBank(null);
                                }

                            }, true)).setNegativeButton("Decline", (DialogInterface dialogInterface, int i) -> {

                            }).create();

                    alertDialog.show();

                }));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("MessagingFragment", "ERROR: " + databaseError.getMessage());
            }

        });


    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;

        if (context instanceof FragmentActivity) {
            this.activity = (FragmentActivity) context;
        }
    }

    /**
     * A sorted array list by date.
     *
     * @author raeesaamir
     */
    static class SortedList extends ArrayList<FirebaseMessage> {
        @Override
        public boolean add(FirebaseMessage firebaseMessage) {
            boolean successful = super.add(firebaseMessage);
            this.sort(Comparator.comparingLong(FirebaseMessage::getMessageTime));
            return successful;
        }
    }
}