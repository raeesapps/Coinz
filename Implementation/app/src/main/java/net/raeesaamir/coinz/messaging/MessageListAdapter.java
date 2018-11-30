package net.raeesaamir.coinz.messaging;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.raeesaamir.coinz.R;
import net.raeesaamir.coinz.authentication.FirestoreUser;

import java.util.List;

public class MessageListAdapter extends RecyclerView.Adapter {

    private final List<FirebaseMessage> messageList;
    private final String userUid;
    private final FirestoreUser fromUser;

    MessageListAdapter(List<FirebaseMessage> messageList, FirestoreUser fromUser, String userUid) {
        this.messageList = messageList;
        this.fromUser = fromUser;
        this.userUid = userUid;
    }

    private static MessageType getType(int ordinal) {
        return ordinal == 0 ? MessageType.MESSAGE_SENT : MessageType.MESSAGE_RECEIVED;
    }

    @Override
    public int getItemViewType(int position) {
        FirebaseMessage message = messageList.get(position);
        String fromUser = message.getMessageFromUser();
        MessageType type;

        if (fromUser.equals(this.userUid)) {
            type = MessageType.MESSAGE_SENT;
        } else {
            type = MessageType.MESSAGE_RECEIVED;
        }

        return type.ordinal();
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        FirebaseMessage message = messageList.get(position);
        MessageType type = MessageListAdapter.getType(viewHolder.getItemViewType());

        switch (type) {
            case MESSAGE_SENT:
                SentMessageHolder sentMessageHolder = (SentMessageHolder) viewHolder;
                sentMessageHolder.bind(message);
                break;
            case MESSAGE_RECEIVED:
                ReceivedMessageHolder receivedMessageHolder = (ReceivedMessageHolder) viewHolder;
                receivedMessageHolder.bind(fromUser, message);
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

        if (messageType.equals(MessageType.MESSAGE_RECEIVED)) {
            view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.item_message_received, viewGroup, false);
            return new ReceivedMessageHolder(view);
        } else {
            view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.item_message_sent, viewGroup, false);
            return new SentMessageHolder(view);
        }

    }

    private enum MessageType {
        MESSAGE_SENT,
        MESSAGE_RECEIVED
    }

    static class ReceivedMessageHolder extends RecyclerView.ViewHolder {

        private final TextView messageText;
        private final TextView timeText;
        private final TextView nameText;

        ReceivedMessageHolder(View itemView) {
            super(itemView);
            messageText = itemView.findViewById(R.id.text_message_body);
            timeText = itemView.findViewById(R.id.text_message_time);
            nameText = itemView.findViewById(R.id.text_message_name);
        }

        void bind(FirestoreUser fromUser, FirebaseMessage message) {
            messageText.setText(message.getMessageText());
            timeText.setText(FirebaseMessage.getMessageTimeAsString(message));
            nameText.setText(fromUser.getDisplayName());
        }
    }

    class SentMessageHolder extends RecyclerView.ViewHolder {

        private final TextView messageText;
        private final TextView timeText;

        SentMessageHolder(View itemView) {
            super(itemView);
            messageText = itemView.findViewById(R.id.text_message_body);
            timeText = itemView.findViewById(R.id.text_message_time);
        }

        void bind(FirebaseMessage message) {
            messageText.setText(message.getMessageText());
            timeText.setText(FirebaseMessage.getMessageTimeAsString(message));
        }
    }
}
