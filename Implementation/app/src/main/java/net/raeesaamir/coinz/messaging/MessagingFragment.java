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

import com.google.common.collect.Lists;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import net.raeesaamir.coinz.R;

public class MessagingFragment extends Fragment {

    private View view;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = view;
        this.mAuth = FirebaseAuth.getInstance();
        this.mUser = mAuth.getCurrentUser();
        populateMessages();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.messaging_fragment, container, false);
    }

    private void populateMessages() {
        RecyclerView recyclerView = view.findViewById(R.id.message_list);
        MessageListAdapter simpleMessageListAdapter =
                new MessageListAdapter(Lists.newArrayList(), mUser.getDisplayName());
        recyclerView.setAdapter(simpleMessageListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}
