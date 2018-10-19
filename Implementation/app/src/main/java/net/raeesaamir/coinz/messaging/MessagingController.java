package net.raeesaamir.coinz.messaging;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.google.common.collect.Lists;

import net.raeesaamir.coinz.R;
import net.raeesaamir.coinz.authentication.simple.SimpleUser;
import net.raeesaamir.coinz.messaging.simple.SimpleMessage;

import java.util.List;

public class MessagingController extends AppCompatActivity {

    public static final class MessagingControllerLoremIpsum {
        public static final SimpleUser USER = new SimpleUser("Raees");
        public static final SimpleUser USER2 = new SimpleUser("Faheed");
        public static final List<SimpleMessage> MESSAGES = Lists.newArrayList(
                new SimpleMessage("u idiot", 23, USER),
                new SimpleMessage("suck it", 24, USER2),
                new SimpleMessage("i can't believe you did that!!", 24, USER2),
                new SimpleMessage("listen u really think i care?", 26, USER2)
        );

    }

    private RecyclerView recyclerView;
    private MessageListAdapter<SimpleUser, SimpleMessage> simpleMessageListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.messaging_view);

        recyclerView = findViewById(R.id.message_list);
        simpleMessageListAdapter = new MessageListAdapter(MessagingControllerLoremIpsum.MESSAGES, MessagingControllerLoremIpsum.USER);
        recyclerView.setAdapter(simpleMessageListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }
}
