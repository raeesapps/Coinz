package net.raeesaamir.coinz.messaging;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import net.raeesaamir.coinz.R;
import net.raeesaamir.coinz.authentication.User;

import java.util.Date;

public class ReceivedMessageHolder<U extends User, M extends Message<U>> extends RecyclerView.ViewHolder {

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
