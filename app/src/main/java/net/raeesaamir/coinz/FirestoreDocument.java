package net.raeesaamir.coinz;

import com.google.common.collect.ImmutableMap;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * Represents a document stored in the Cloud Firestore database.
 *
 * @author raeesaamir
 */
public abstract class FirestoreDocument {

    /**
     * The doucment represented as an immutable map.
     *
     * @return An immutable map object storing the key-value pairs to be put into the database.
     */
    protected abstract ImmutableMap<String, Object> getDocument();

    /**
     * The name of the document.
     *
     * @return The name of the document.
     */
    protected abstract String getDocumentName();

    /**
     * The name of the collection where the docuemnt will be stored.
     *
     * @return The name o the collection.
     */
    protected abstract String getCollectionName();

    /**
     * This must be called to save the document to the database.
     */
    public void getFuture() {
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        DocumentReference reference =
                firestore.collection(getCollectionName()).document(getDocumentName());
        reference.set(getDocument());
    }
}
