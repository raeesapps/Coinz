package net.raeesaamir.coinz.messaging;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.Task;
import com.google.common.collect.Lists;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import net.raeesaamir.coinz.R;
import net.raeesaamir.coinz.authentication.FirestoreUser;

import java.util.List;

public class MessagingFragment extends Fragment {

    private Button button;
    private List<FirestoreMessage> firestoreMessageList = Lists.newArrayList();
    private EditText messageContents;
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
        this.messageContents = view.findViewById(R.id.edittext_chatbox);
        setOtherUser(getActivity().getIntent().getStringExtra("username"));
    }

    private void setOnSend() {
        button.setOnClickListener((View view) -> {
            String messageString = messageContents.getText().toString();
            FirestoreMessage message = new FirestoreMessage(messageString, thisUser, otherUser);
            message.getFuture();
            firestoreMessageList.add(message);
            simpleMessageListAdapter.notifyDataSetChanged();
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.messaging_fragment, container, false);
    }

    private void setOtherUser(String username) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference users = db.collection("Users");

        users.get().addOnCompleteListener((@NonNull Task<QuerySnapshot> task) -> {

            if(task.isSuccessful()) {

                if(thisUser == null) {
                    this.thisUser = new FirestoreUser(mUser.getEmail(), mUser.getUid(), mUser.getDisplayName());
                }

                if(otherUser == null) {
                    setOtherUser(getActivity().getIntent().getStringExtra("username"));
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

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference messages = db.collection("Messages");

        messages.get().addOnCompleteListener((@NonNull Task<QuerySnapshot> task) -> {

            if(task.isSuccessful()) {

                for(DocumentSnapshot snapshot: task.getResult()) {

                    if(!snapshot.contains("messageText") || !snapshot.contains("messageTime")
                            || !snapshot.contains("messageToUser") || !snapshot.contains("messageFromUser")) {
                        continue;
                    }

                    if((snapshot.get("messageToUser").equals(thisUser.getDocument()) || snapshot.get("messageToUser").equals(otherUser.getDocument()))
                            && (snapshot.get("messageFromUser").equals(thisUser.getDocument()) || snapshot.get("messageFromUser").equals(otherUser.getDocument()))) {

                        String messageText = snapshot.getString("messageText");
                        long messageTime = snapshot.getLong("messageTime");

                        FirestoreUser to;
                        if(snapshot.get("messageToUser").equals(thisUser.getDocument())) {
                            to = thisUser;
                        } else {
                            to = otherUser;
                        }

                        FirestoreUser from;
                        if(snapshot.get("messageFromUser").equals(thisUser.getDocument())) {
                            from = thisUser;
                        } else {
                            from = otherUser;
                        }

                        FirestoreMessage firestoreMessage = new FirestoreMessage(messageText, from, to, messageTime);
                        firestoreMessageList.add(firestoreMessage);
                    }

                }


                simpleMessageListAdapter =
                        new MessageListAdapter(firestoreMessageList, thisUser);
                recyclerView.setAdapter(simpleMessageListAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            }

        });
    }
}