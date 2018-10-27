package net.raeesaamir.coinz.messaging;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FirebaseMessage {

    private static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("dd/MM/yy");

    private String messageText;
    private String messageUser;
    private long messageTime;

    public FirebaseMessage(String messageText, String messageUser) {
        this.messageText = messageText;
        this.messageUser = messageUser;
        this.messageTime = new Date().getTime();
    }

    public FirebaseMessage(){

    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getMessageUser() {
        return messageUser;
    }

    public void setMessageUser(String messageUser) {
        this.messageUser = messageUser;
    }

    public long getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(long messageTime) {
        this.messageTime = messageTime;
    }

    public String getMessageTimeAsString() {
        Date date = new Date(messageTime);
        return DATE_FORMATTER.format(date);
    }
}
