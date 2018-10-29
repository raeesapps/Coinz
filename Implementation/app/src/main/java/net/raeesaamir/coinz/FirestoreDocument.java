package net.raeesaamir.coinz;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

public abstract class FirestoreDocument {

    public abstract Map<String, Object> getDocument();
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
