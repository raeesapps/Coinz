package net.raeesaamir.coinz.messaging;

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
import android.widget.Spinner;
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

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class MessagingFragment extends Fragment {

    private static final String DB_NAME = "coinz-12df3";

    public static class SortedList extends ArrayList<FirebaseMessage> {
        @Override
        public boolean add(FirebaseMessage firebaseMessage) {
            boolean successful = super.add(firebaseMessage);

            this.sort(Comparator.comparingLong(FirebaseMessage::getMessageTime));

            return successful;
        }
    }

    private long msgNanoTime;
    private long previousMsgNanoTime = 0;
    private DatabaseReference mReference;
    private Button button;
    private List<FirebaseMessage> firestoreMessageList = new SortedList();
    private EditText messageContents;
    private Spinner spinner;
    private View view;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private FirestoreUser otherUser;
    private FirestoreUser thisUser;
    private MessageListAdapter simpleMessageListAdapter;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = view;
        this.mAuth = FirebaseAuth.getInstance();
        this.mUser = mAuth.getCurrentUser();
        this.thisUser = new FirestoreUser(mUser.getEmail(), mUser.getUid(), mUser.getDisplayName());
        this.button = view.findViewById(R.id.button_chatbox_send);
        this.spinner = view.findViewById(R.id.spinner);
        this.messageContents = view.findViewById(R.id.edittext_chatbox);

        // Instantiate spinner, set the array adapter from thisUser wallet then add
        // onClickListener to remove from thisUser wallet
        // add to otherUser wallet and send a message showing the transaction details.
        listen(getActivity().getIntent().getStringExtra("username"));
    }

    private void setOnSend() {
        button.setOnClickListener((View view) -> {
            String messageString = messageContents.getText().toString();
            messageContents.setText("");
            msgNanoTime = System.nanoTime();

            if(msgNanoTime - previousMsgNanoTime < 3000000000L) {
                Toast.makeText(getContext(), "Please wait", Toast.LENGTH_SHORT).show();
            } else {
                Preconditions.checkNotNull(mReference);

                FirebaseMessage message = new FirebaseMessage(messageString, thisUser.getUid(), otherUser.getUid());
                String key = mReference.push().getKey();
                mReference.child(key).setValue(message);

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

    private void listen(String username) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference users = db.collection("Users");

        users.get().addOnCompleteListener((@NonNull Task<QuerySnapshot> task) -> {

            if(task.isSuccessful()) {

                if(thisUser == null) {
                    this.thisUser = new FirestoreUser(mUser.getEmail(), mUser.getUid(), mUser.getDisplayName());
                }

                for(DocumentSnapshot snapshot: task.getResult()) {

                    if(!snapshot.contains("uid") || !snapshot.contains("displayName") ||
                            !snapshot.contains("email")) {
                        continue;
                    }

                    if(snapshot.get("displayName").equals(username)) {
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

    private void populateMessages() {
        RecyclerView recyclerView = view.findViewById(R.id.message_list);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        mReference = firebaseDatabase.getReference(DB_NAME);


        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                System.out.println("[MessagingFragment]: onDataChange");
                firestoreMessageList.clear();
                for(DataSnapshot item: dataSnapshot.getChildren()) {
                    FirebaseMessage firestoreMessage = item.getValue(FirebaseMessage.class);

                    if((firestoreMessage.getMessageFromUser().equals(thisUser.getUid()) &&
                            firestoreMessage.getMessageToUser().equals(otherUser.getUid()) ||
                            (firestoreMessage.getMessageFromUser().equals(otherUser.getUid()) &&
                                    firestoreMessage.getMessageToUser().equals(thisUser.getUid())))) {
                        firestoreMessageList.add(firestoreMessage);
                    }
                }

                simpleMessageListAdapter =
                        new MessageListAdapter(firestoreMessageList, otherUser, thisUser.getUid());
                recyclerView.setAdapter(simpleMessageListAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("MessagingFragment","ERROR: " + databaseError.getMessage());
            }

        });


    }
}