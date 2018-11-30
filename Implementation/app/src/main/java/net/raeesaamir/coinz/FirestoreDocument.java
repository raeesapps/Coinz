package net.raeesaamir.coinz;

import com.google.common.collect.ImmutableMap;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public abstract class FirestoreDocument {

    protected abstract ImmutableMap<String, Object> getDocument();

    protected abstract String getDocumentName();

    protected abstract String getCollectionName();

    public void getFuture() {
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        DocumentReference reference =
                firestore.collection(getCollectionName()).document(getDocumentName());
        reference.set(getDocument());
    }
}
