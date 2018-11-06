package net.raeesaamir.coinz;

import com.google.common.collect.ImmutableMap;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public abstract class FirestoreDocument {

    public abstract ImmutableMap<String, Object> getDocument();
    public abstract String getDocumentName();
    public abstract String getCollectionName();

    public DocumentReference getFuture() {
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        DocumentReference reference =
                firestore.collection(getCollectionName()).document(getDocumentName());
        reference.set(getDocument());
        return reference;
    }
}
