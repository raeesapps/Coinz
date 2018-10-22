package net.raeesaamir.coinz.messaging;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.raeesaamir.coinz.R;
import net.raeesaamir.coinz.authentication.User;

import java.util.Date;
import java.util.List;

public class MessageListAdapter<U extends User, M extends Message<U>> extends RecyclerView.Adapter {

    private enum MessageType {
        MESSAGE_SENT,
        MESSAGE_RECEIVED;
    }

    public static class ReceivedMessageHolder<U extends User, M extends Message<U>> extends RecyclerView.ViewHolder {

        private final TextView messageText;
        private final TextView timeText;
        private final TextView nameText;

        ReceivedMessageHolder(View itemView) {
            super(itemView);
            messageText = itemView.findViewById(R.id.text_message_body);
            timeText = itemView.findViewById(R.id.text_message_time);
            nameText = itemView.findViewById(R.id.text_message_name);
        }

        void bind(M message) {
            messageText.setText(message.message());

            Date date = new Date(message.createdAt());
            timeText.setText(date.toGMTString());

            U user = message.sender();
            nameText.setText(user.name());
        }
    }

    public class SentMessageHolder<U extends User, M extends Message<U>> extends RecyclerView.ViewHolder {

        private final TextView messageText;

        SentMessageHolder(View itemView) {
            super(itemView);
            messageText = itemView.findViewById(R.id.text_message_body);
        }

        void bind(M message) {
            messageText.setText(message.message());
        }
    }

    private static final MessageType getType(int ordinal) {
        return ordinal == 0 ? MessageType.MESSAGE_SENT : MessageType.MESSAGE_RECEIVED;
    }

    private List<M> messageList;
    private U user;

    public MessageListAdapter(List<M> messageList, U user) {
        this.messageList = messageList;
        this.user = user;

        Log.i("MessageListAdapter", "constructor");

    }

    @Override
    public int getItemViewType(int position) {
        M message = messageList.get(position);
        MessageType type;
        U user = message.sender();

        Log.i("MessageListAdapter", "getItemViewType");


        if (user.equals(this.user)) {
            type = MessageType.MESSAGE_SENT;
        } else {
            type = MessageType.MESSAGE_RECEIVED;
        }

        return type.ordinal();
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        M message = messageList.get(position);
        MessageType type = MessageListAdapter.getType(viewHolder.getItemViewType());

        Log.i("MessageListAdapter", "onBindViewholder");

        switch(type) {
            case MESSAGE_SENT:
                SentMessageHolder<U, M> sentMessageHolder = (SentMessageHolder<U, M>) viewHolder;
                Log.i("BINDING", message.message());
                sentMessageHolder.bind(message);
                break;
            case MESSAGE_RECEIVED:
                ReceivedMessageHolder<U, M> receivedMessageHolder = (ReceivedMessageHolder<U, M>) viewHolder;
                Log.i("BINDING", message.message());
                receivedMessageHolder.bind(message);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view;
        MessageType messageType = MessageListAdapter.getType(viewType);

        Log.i("MessageListAdapter", "onCreateViewHolder");

        if(messageType.equals(MessageType.MESSAGE_RECEIVED)) {
            view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.item_message_received, viewGroup, false);
            return new ReceivedMessageHolder<U, M>(view);
        } else {
            view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.item_message_sent, viewGroup, false);
            return new SentMessageHolder<U, M>(view);
        }

    }
}
