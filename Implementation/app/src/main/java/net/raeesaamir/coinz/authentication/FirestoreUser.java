package net.raeesaamir.coinz.authentication;

import com.google.common.base.Objects;
import com.google.common.collect.ImmutableMap;

import net.raeesaamir.coinz.FirestoreDocument;

/**
 * This class represents a copy of the user's information stored in the database. It contains information used to identify the user. We need this class because there is no way of querying users without putting their information into the database.
 *
 * @author raeesaamir
 */
public class FirestoreUser extends FirestoreDocument {

    /**
     * The user's email address
     */
    private final String email;

    /**
     * The user's unique identification number
     */
    private final String uid;

    /**
     * The display name of the user's account.
     */
    private final String displayName;


    /**
     * Constructs a new FirebaseUser object.
     *
     * @param email       - The user's email address.
     * @param uid         - The user's id.
     * @param displayName - The user's display name.
     */
    public FirestoreUser(String email, String uid, String displayName) {
        this.email = email;
        this.uid = uid;
        this.displayName = displayName;
    }

    /**
     * Gets the display name
     *
     * @return - The display name
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Gets the user's id.
     *
     * @return The user's id.
     */
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

        if (obj instanceof FirestoreUser) {

            FirestoreUser otherUsr = (FirestoreUser) obj;
            return Objects.equal(email, otherUsr.email) && Objects.equal(uid, otherUsr.uid)
                    && Objects.equal(displayName, otherUsr.displayName);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(email, uid, displayName);
    }
}
