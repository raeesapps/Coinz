package net.raeesaamir.coinz.messaging;

import com.google.common.base.Objects;
import com.google.common.collect.ImmutableMap;

import net.raeesaamir.coinz.FirestoreDocument;
import net.raeesaamir.coinz.authentication.FirestoreUser;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FirebaseMessage {

    private static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("dd/MM/yy HH:mm");

    private String messageText;
    private String messageFromUser;
    private String messageToUser;
    private long messageTime;

    public FirebaseMessage(String messageText, String messageFromUser, String messageToUser, long messageTime) {
        this.messageText = messageText;
        this.messageFromUser = messageFromUser;
        this.messageToUser = messageToUser;
        this.messageTime = messageTime;
    }

    public FirebaseMessage(String messageText, String messageFromUser, String messageToUser) {
        this.messageText = messageText;
        this.messageFromUser = messageFromUser;
        this.messageToUser = messageToUser;
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

    public String getMessageFromUser() {
        return messageFromUser;
    }

    public void setMessageFromUser(String messageFromUser) {
        this.messageFromUser = messageFromUser;
    }

    public String getMessageToUser() {
        return messageToUser;
    }

    public void setMessageToUser(String messageToUser) {
        this.messageToUser = messageToUser;
    }

    public long getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(long messageTime) {
        this.messageTime = messageTime;
    }

    public static String getMessageTimeAsString(FirebaseMessage msg) {
        Date date = new Date(msg.messageTime);
        return DATE_FORMATTER.format(date);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) {
            return false;
        }

        if(!(obj instanceof FirebaseMessage)) {
            return false;
        }

        FirebaseMessage otherMsg = (FirebaseMessage) obj;
        return Objects.equal(messageText, otherMsg.messageText) &&
                Objects.equal(messageFromUser, otherMsg.messageFromUser) &&
                Objects.equal(messageToUser, otherMsg.messageToUser) &&
                Objects.equal(messageTime, otherMsg.messageTime);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(messageText, messageFromUser, messageToUser, messageTime);
    }
}
