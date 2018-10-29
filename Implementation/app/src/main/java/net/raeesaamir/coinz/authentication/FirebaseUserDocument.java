package net.raeesaamir.coinz.authentication;

import com.google.common.collect.Maps;

import net.raeesaamir.coinz.FirestoreDocument;

import java.util.Map;

public class FirebaseUserDocument extends FirestoreDocument {

    private String email;
    private String uid;
    private String displayName;

    public FirebaseUserDocument(String email, String uid, String displayName) {
        this.email = email;
        this.uid = uid;
        this.displayName = displayName;
    }

    public FirebaseUserDocument() {

    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public Map<String, Object> getDocument() {
        Map<String, Object> doc = Maps.newHashMap();

        doc.put("email", email);
        doc.put("uid", uid);
        doc.put("displayName", displayName);

        return doc;
    }

    @Override
    public String getDocumentName() {
        return uid;
    }

    @Override
    public String getCollectionName() {
        return "Users";
    }
}
