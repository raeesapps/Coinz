package net.raeesaamir.coinz.messaging;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.Task;
import com.google.common.base.Objects;
import com.google.common.collect.ImmutableMap;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import net.raeesaamir.coinz.FirestoreDocument;
import net.raeesaamir.coinz.authentication.FirestoreUser;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FirestoreMessage extends FirestoreDocument {

    public static void createExampleConversation() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference users = db.collection("Users");
        users.get().addOnCompleteListener((@NonNull Task<QuerySnapshot> task) -> {
            if(task.isSuccessful()) {

                FirestoreUser to = null;
                FirestoreUser from = null;
                for(DocumentSnapshot snapshot: task.getResult()) {

                    if(!snapshot.contains("uid") || !snapshot.contains("displayName") || !snapshot.contains("email")) {
                        continue;
                    }

                    String uid = (String) snapshot.get("uid");
                    String displayName = (String) snapshot.get("displayName");
                    String email = (String) snapshot.get("email");

                    if(displayName.equalsIgnoreCase("Raees")) {
                        to = new FirestoreUser(email, uid, displayName);
                    }

                    if(displayName.equalsIgnoreCase("John")) {
                        from = new FirestoreUser(email, uid, displayName);
                    }

                }

                FirestoreMessage firestoreMessage = new FirestoreMessage("Hello", from, to);
                System.out.println("FUTURE SUCCESSFUL");
                firestoreMessage.getFuture();
            }

        });

    }

    private static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("dd/MM/yy");

    private String messageText;
    private FirestoreUser messageFromUser;
    private FirestoreUser messageToUser;
    private long messageTime;

    public FirestoreMessage(String messageText, FirestoreUser messageFromUser, FirestoreUser messageToUser, long messageTime) {
        this.messageText = messageText;
        this.messageFromUser = messageFromUser;
        this.messageToUser = messageToUser;
        this.messageTime = messageTime;
    }

    public FirestoreMessage(String messageText, FirestoreUser messageFromUser, FirestoreUser messageToUser) {
        this.messageText = messageText;
        this.messageFromUser = messageFromUser;
        this.messageToUser = messageToUser;
        this.messageTime = new Date().getTime();
    }

    public FirestoreMessage(){

    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public FirestoreUser getMessageFromUser() {
        return messageFromUser;
    }

    public void setMessageFromUser(FirestoreUser messageFromUser) {
        this.messageFromUser = messageFromUser;
    }

    public FirestoreUser getMessageToUser() {
        return messageToUser;
    }

    public void setMessageToUser(FirestoreUser messageToUser) {
        this.messageToUser = messageToUser;
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

    @Override
    public ImmutableMap<String, Object> getDocument() {
        return new ImmutableMap.Builder<String, Object>()
                .put("messageText", messageText).put("messageFromUser", messageFromUser.getDocument())
                .put("messageToUser", messageToUser.getDocument()).put("messageTime", messageTime).build();
    }

    @Override
    public String getDocumentName() {
        return Integer.toString(hashCode());
    }

    @Override
    public String getCollectionName() {
        return "Messages";
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) {
            return false;
        }

        if(!(obj instanceof FirestoreMessage)) {
            return false;
        }

        FirestoreMessage otherMsg = (FirestoreMessage) obj;
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
