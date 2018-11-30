package net.raeesaamir.coinz.authentication;

import com.google.common.base.Objects;
import com.google.common.collect.ImmutableMap;

import net.raeesaamir.coinz.FirestoreDocument;

public class FirestoreUser extends FirestoreDocument {

    private final String email;
    private final String uid;
    private final String displayName;

    public FirestoreUser(String email, String uid, String displayName) {
        this.email = email;
        this.uid = uid;
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getUid() {
        return uid;
    }

    @Override
    public ImmutableMap<String, Object> getDocument() {
        return ImmutableMap.<String, Object>builder().put("uid", uid).put("email", email).put("displayName", displayName).build();
    }

    @Override
    public String getDocumentName() {
        return uid;
    }

    @Override
    public String getCollectionName() {
        return "Users";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (!(obj instanceof FirestoreUser)) {
            return false;
        }

        FirestoreUser otherUsr = (FirestoreUser) obj;
        return Objects.equal(email, otherUsr.email) && Objects.equal(uid, otherUsr.uid)
                && Objects.equal(displayName, otherUsr.displayName);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(email, uid, displayName);
    }
}
