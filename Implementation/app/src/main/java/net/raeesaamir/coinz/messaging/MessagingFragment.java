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

import net.raeesaamir.coinz.R;
import net.raeesaamir.coinz.authentication.simple.SimpleUser;
import net.raeesaamir.coinz.authentication.simple.SimpleUserManager;
import net.raeesaamir.coinz.messaging.simple.SimpleMessage;

import java.util.List;

public class MessagingFragment extends Fragment {

    private static final class MessagingControllerLoremIpsum {
        public static final List<SimpleMessage> MESSAGES = Lists.newArrayList(
                new SimpleMessage("u idiot", 23, SimpleUserManager.USERS.get(0)),
                new SimpleMessage("suck it", 24, SimpleUserManager.USERS.get(1)),
                new SimpleMessage("i can't believe you did that!!", 24, SimpleUserManager.USERS.get(1)),
                new SimpleMessage("listen u really think i care?", 26, SimpleUserManager.USERS.get(0))
        );

    }

    private View view;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = view;
        populateMessages();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.messaging_fragment, container, false);
    }

    private void populateMessages() {
        RecyclerView recyclerView = view.findViewById(R.id.message_list);
        MessageListAdapter<SimpleUser, SimpleMessage> simpleMessageListAdapter =
                new MessageListAdapter(MessagingControllerLoremIpsum.MESSAGES, SimpleUserManager.USERS.get(0));
        recyclerView.setAdapter(simpleMessageListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}
