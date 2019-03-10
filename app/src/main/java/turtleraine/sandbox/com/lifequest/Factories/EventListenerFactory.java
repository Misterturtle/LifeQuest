package turtleraine.sandbox.com.lifequest.Factories;

import android.util.EventLog;

import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.concurrent.CompletableFuture;

public class EventListenerFactory {

    public <T> EventListener<T> makeFutureBindedEventListener(CompletableFuture<T> future) {
        return (T success, FirebaseFirestoreException error)  -> {
                if (success != null) {
                    future.complete(success);
                } else {
                    future.completeExceptionally(error);
                }
            };
    }
}
