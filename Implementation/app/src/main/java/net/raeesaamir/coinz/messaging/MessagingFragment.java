package net.raeesaamir.coinz.messaging;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

import net.raeesaamir.coinz.R;
import net.raeesaamir.coinz.authentication.FirestoreUser;
import net.raeesaamir.coinz.wallet.Wallet;

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
     * The name of the real-time database.
     */
    private static final String DB_NAME = "coinz-12df3";

    /**
     * The date format used in the map's GeoJSON file and the player's wallet.
     */
    @SuppressLint("SimpleDateFormat")
    private static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("yyyy/MM/dd", Locale.UK);

    /**
     * A list of messages sorted by time.
     */
    private final List<FirebaseMessage> firestoreMessageList = new SortedList();

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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = view;
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        this.mUser = mAuth.getCurrentUser();
        this.thisUser = new FirestoreUser(Objects.requireNonNull(mUser).getEmail(), mUser.getUid(), mUser.getDisplayName());
        this.button = view.findViewById(R.id.button_chatbox_send);
        this.tradeButton = view.findViewById(R.id.button_chatbox_trade);
        this.messageContents = view.findViewById(R.id.edittext_chatbox);

        // Instantiate spinner, set the array adapter from thisUser wallet then add
        // onClickListener to remove from thisUser wallet
        // add to otherUser wallet and send a message showing the transaction details.
        listen(Objects.requireNonNull(getActivity()).getIntent().getStringExtra("username"));
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
                Preconditions.checkNotNull(mReference);

                FirebaseMessage message = new FirebaseMessage(messageString, thisUser.getUid(), otherUser.getUid());
                String key = mReference.push().getKey();
                mReference.child(Objects.requireNonNull(key)).setValue(message);

                firestoreMessageList.add(message);
                simpleMessageListAdapter.notifyDataSetChanged();

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
                Wallet.loadWallet(mUser.getUid(), dateFormatted, Wallet.WalletType.SPARE_CHANGE_WALLET, (Wallet wallet) -> tradeButton.setOnClickListener((View view) -> {
                    List<String> coinsList = wallet.getCoins();

                    String[] coins = coinsList.toArray(new String[coinsList.size()]);
                    boolean[] selectedItems = new boolean[coins.length];

                    AlertDialog alertDialog = new AlertDialog.Builder(context)
                            .setMultiChoiceItems(coins, selectedItems, (DialogInterface dialogInterface, int i, boolean b) -> selectedItems[i] = true).setTitle("Select the coins you want to trade").setPositiveButton("Accept", (DialogInterface dialogInterface, int k) -> Wallet.loadWallet(otherUser.getUid(), dateFormatted, Wallet.WalletType.MAIN_WALLET, (Wallet otherWallet) -> {

                                for (int i = 0; i < selectedItems.length; i++) {
                                    if (selectedItems[i]) {
                                        if (otherWallet.numberOfCoins() == 9) {
                                            Toast.makeText(context, "You cannot transfer coins to this player because they already have too many!", Toast.LENGTH_LONG).show();
                                            break;
                                        } else {
                                            String coin = coinsList.get(i);
                                            coins[i] = null;
                                            wallet.removeCoin(coin);

                                            otherWallet.addCoin(coin);
                                            otherWallet.getFuture();

                                            FirebaseMessage message = new FirebaseMessage("You have transferred " + coin + " to " + otherUser.getDisplayName(), thisUser.getUid(), otherUser.getUid());
                                            String key = mReference.push().getKey();
                                            mReference.child(Objects.requireNonNull(key)).setValue(message);

                                            firestoreMessageList.add(message);
                                            simpleMessageListAdapter.notifyDataSetChanged();
                                        }
                                    }
                                }

                                Wallet.WalletSingleton.setOtherWallet(null);

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