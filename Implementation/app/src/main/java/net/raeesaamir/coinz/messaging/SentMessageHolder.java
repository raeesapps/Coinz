package net.raeesaamir.coinz.messaging;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import net.raeesaamir.coinz.R;
import net.raeesaamir.coinz.authentication.User;

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
