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

import com.google.android.gms.tasks.OnCompleteListener;
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

    private View view;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private FirestoreUser otherUser;
    private FirestoreUser thisUser;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = view;
        this.mAuth = FirebaseAuth.getInstance();
        this.mUser = mAuth.getCurrentUser();
        this.thisUser = new FirestoreUser(mUser.getEmail(), mUser.getUid(), mUser.getDisplayName());
        populateMessages();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.messaging_fragment, container, false);
    }

    private void populateMessages() {
        RecyclerView recyclerView = view.findViewById(R.id.message_list);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference messages = db.collection("Messages");

        messages.get().addOnCompleteListener((@NonNull Task<QuerySnapshot> task) -> {

            if(task.isSuccessful()) {

                List<FirestoreMessage> firestoreMessageList = Lists.newArrayList();
                for(DocumentSnapshot snapshot: task.getResult()) {

                    if(!snapshot.contains("messageText") || !snapshot.contains("messageTime")
                            || !snapshot.contains("messageToUser") || snapshot.contains("messageFromUser")) {
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

            }

        });

        MessageListAdapter simpleMessageListAdapter =
                new MessageListAdapter(Lists.newArrayList(), thisUser);
        recyclerView.setAdapter(simpleMessageListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}
