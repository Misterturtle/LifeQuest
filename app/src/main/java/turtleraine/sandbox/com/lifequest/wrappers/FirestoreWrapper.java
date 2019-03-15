package turtleraine.sandbox.com.lifequest.wrappers;

import android.util.Log;

import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.concurrent.CompletableFuture;

public class FirestoreWrapper {

    private FirebaseFirestore firestore;

    public FirestoreWrapper() {
        firestore = FirebaseFirestore.getInstance();
    }

    public FirebaseFirestore getInstance() {
        return firestore;
    }

    public CollectionReference collection(String collectionPath) {
        return firestore.collection(collectionPath);
    }

    public DocumentReference document(String documentPath) {
        return firestore.document(documentPath);
    }

    public void addSnapshotListener(DocumentReference documentReference, EventListener<DocumentSnapshot> listener){
        documentReference.addSnapshotListener(listener);

    }
}
