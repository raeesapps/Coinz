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

/**
 * The adapter that tells the message recycler view how to add a message when a new message is received.
 *
 * @author raeesaamir
 */
public class MessageListAdapter extends RecyclerView.Adapter {

    /**
     * The list of messages in the recycler view.
     */
    private final List<FirebaseMessage> messageList;

    /**
     * The UUID of the logged in user.
     */
    private final String userUid;

    /**
     * The user who has sent messages to the logged in user..
     */
    private final FirestoreUser fromUser;

    /**
     * Constructs a new message list adapter.
     *
     * @param messageList - The list of messages in the view.
     * @param fromUser    - The user sending the messages to the logged in user.
     * @param userUid     - The UUID of the user sending the messages.
     */
    MessageListAdapter(List<FirebaseMessage> messageList, FirestoreUser fromUser, String userUid) {
        this.messageList = messageList;
        this.fromUser = fromUser;
        this.userUid = userUid;
    }

    /**
     * Gets the type of message received.
     *
     * @param ordinal - The order the message type is defined in the enum.
     * @return 0 if the message is a sent message otherwise 1 if the message is a received message.
     */
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

    /**
     * Represents the type of message in the view.
     */
    private enum MessageType {

        /**
         * A sent message.
         */
        MESSAGE_SENT,

        /**
         * A received message.
         */
        MESSAGE_RECEIVED
    }

    /**
     * Represents the template for a received message.
     *
     * @author raeesaamir
     */
    static class ReceivedMessageHolder extends RecyclerView.ViewHolder {

        /**
         * The content of the message.
         */
        private final TextView messageText;

        /**
         * The time the message was sent.
         */
        private final TextView timeText;

        /**
         * The display name of the user.
         */
        private final TextView nameText;

        /**
         * Constructs a new received message view holder.
         *
         * @param itemView - The view object.
         */
        ReceivedMessageHolder(View itemView) {
            super(itemView);
            messageText = itemView.findViewById(R.id.text_message_body);
            timeText = itemView.findViewById(R.id.text_message_time);
            nameText = itemView.findViewById(R.id.text_message_name);
        }

        /**
         * Binds a message object into the received message view holder.
         *
         * @param fromUser - The person the message is from.
         * @param message  - The message itself.
         */
        void bind(FirestoreUser fromUser, FirebaseMessage message) {
            messageText.setText(message.getMessageText());
            timeText.setText(FirebaseMessage.getMessageTimeAsString(message));
            nameText.setText(fromUser.getDisplayName());
        }
    }

    /**
     * Represents the template for a sent message.
     */
    class SentMessageHolder extends RecyclerView.ViewHolder {

        /**
         * The content of the message.
         */
        private final TextView messageText;

        /**
         * The time the message was sent.
         */
        private final TextView timeText;

        /**
         * Constructs a new sent message view holder.
         *
         * @param itemView - The view object.
         */
        SentMessageHolder(View itemView) {
            super(itemView);
            messageText = itemView.findViewById(R.id.text_message_body);
            timeText = itemView.findViewById(R.id.text_message_time);
        }

        /**
         * Binds the message object into the sent message view holder.
         *
         * @param message - The message object.
         */
        void bind(FirebaseMessage message) {
            messageText.setText(message.getMessageText());
            timeText.setText(FirebaseMessage.getMessageTimeAsString(message));
        }
    }
}
