package turtleraine.sandbox.com.lifequest.wrappers;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.concurrent.CompletableFuture;

public class FirestoreWrapper {

    FirebaseFirestore firestore;

    public FirestoreWrapper() {
        firestore = FirebaseFirestore.getInstance();
    }

    public FirebaseFirestore getInstance() {
        return FirebaseFirestore.getInstance();
    }

    public CollectionReference collection(String collectionPath) {
        return firestore.collection(collectionPath);
    }

    public void addSnapshotListener(DocumentReference documentReference, EventListener<DocumentSnapshot> listener){
        documentReference.addSnapshotListener(listener);

    }
}
