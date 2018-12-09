package net.raeesaamir.coinz.messaging;

import android.annotation.SuppressLint;

import com.google.common.base.Objects;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Represent's a message from the chat system that is stored in the real-time database.
 *
 * @author raeesaamir
 */
class FirebaseMessage {

    /**
     * The date format used in the map's GeoJSON file and the player's wallet.
     */
    @SuppressLint("SimpleDateFormat")
    private static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("yyyy/MM/dd", Locale.UK);

    /**
     * The contents of the message.
     */
    private String messageText;

    /**
     * The UUID of the user who sent the message.
     */
    private String messageFromUser;

    /**
     * The UUID of the user who the message is intended for.
     */
    private String messageToUser;

    /**
     * The time the message was sent.
     */
    private long messageTime;

    @SuppressWarnings("unused")
    FirebaseMessage(String messageText, String messageFromUser, String messageToUser, long messageTime) {
        this.messageText = messageText;
        this.messageFromUser = messageFromUser;
        this.messageToUser = messageToUser;
        this.messageTime = messageTime;
    }

    /**
     * Constructs a new FirebaseMessage instance.
     *
     * @param messageText     - The contents of the message.
     * @param messageFromUser - The UUID of the user who sent the message.
     * @param messageToUser   - The UUID of the user who the message is intended for.
     */
    FirebaseMessage(String messageText, String messageFromUser, String messageToUser) {
        this.messageText = messageText;
        this.messageFromUser = messageFromUser;
        this.messageToUser = messageToUser;
        this.messageTime = new Date().getTime();
    }

    @SuppressWarnings("unused")
    FirebaseMessage() {

    }

    /**
     * Returns the time of the message as a string.
     *
     * @param msg - The message
     * @return A string representing the time.
     */
    public static String getMessageTimeAsString(FirebaseMessage msg) {
        Date date = new Date(msg.messageTime);
        return DATE_FORMATTER.format(date);
    }

    /**
     * Gets the contents of the message.
     *
     * @return - The contents of the message.
     */
    public String getMessageText() {
        return messageText;
    }

    @SuppressWarnings("unused")
    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    /**
     * Gets the UUID of the user who sent the message.
     *
     * @return The UUID.
     */
    public String getMessageFromUser() {
        return messageFromUser;
    }

    @SuppressWarnings("unused")
    public void setMessageFromUser(String messageFromUser) {
        this.messageFromUser = messageFromUser;
    }

    /**
     * Gets the UUID of the user who the message is intended for.
     *
     * @return The UUID.
     */
    public String getMessageToUser() {
        return messageToUser;
    }

    @SuppressWarnings("unused")
    public void setMessageToUser(String messageToUser) {
        this.messageToUser = messageToUser;
    }

    /**
     * Gets the time the message was sent.
     *
     * @return The time the message was sent.
     */
    public long getMessageTime() {
        return messageTime;
    }

    @SuppressWarnings("unused")
    public void setMessageTime(long messageTime) {
        this.messageTime = messageTime;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (obj instanceof FirebaseMessage) {

            FirebaseMessage otherMsg = (FirebaseMessage) obj;
            return Objects.equal(messageText, otherMsg.messageText) &&
                    Objects.equal(messageFromUser, otherMsg.messageFromUser) &&
                    Objects.equal(messageToUser, otherMsg.messageToUser) &&
                    Objects.equal(messageTime, otherMsg.messageTime);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(messageText, messageFromUser, messageToUser, messageTime);
    }
}
