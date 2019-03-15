package turtleraine.sandbox.com.lifequest.repositories;

import android.util.Log;

import com.google.common.util.concurrent.Futures;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import javax.inject.Inject;

import turtleraine.sandbox.com.lifequest.Application.Injector;
import turtleraine.sandbox.com.lifequest.common.exceptions.FirestoreGetFailure;
import turtleraine.sandbox.com.lifequest.entities.TaskEntity;
import turtleraine.sandbox.com.lifequest.entities.UserEntity;
import turtleraine.sandbox.com.lifequest.wrappers.FirestoreWrapper;

public class FirestoreRepository implements TaskRepository {

    private final String USER_COLLECTION_PATH = "users/";
    private final String HARD_CODED_USER = "Turtle";

    @Inject
    FirestoreWrapper firestoreWrapper;

    public FirestoreRepository() {
        Injector.getInjector().inject(this);
    }

    public void updateUser(UserEntity user) {
        firestoreWrapper.document(USER_COLLECTION_PATH + user.name).set(user);
    }

    public CompletableFuture<UserEntity> getUser(String userName) {
        CompletableFuture<UserEntity> future = new CompletableFuture<>();

        firestoreWrapper.document(USER_COLLECTION_PATH + userName).get().addOnCompleteListener(docTask -> {
            if (docTask.isSuccessful()) {
                DocumentSnapshot result = docTask.getResult();
                if (result.getString("name") == null) {
                    future.complete(new UserEntity(HARD_CODED_USER, Collections.emptyList()));
                } else {
                    future.complete(result.toObject(UserEntity.class));
                }

            } else {
                future.completeExceptionally(new FirestoreGetFailure(USER_COLLECTION_PATH + userName));
            }
        });

        return future;
    }

    @Override
    public CompletableFuture<List<TaskEntity>> getTasks() {
        return getUser(HARD_CODED_USER).thenApply(user -> user.taskList);
    }

    @Override
    public void setTasks(List<TaskEntity> expectedTaskList) {
        UserEntity hardCodedUser = new UserEntity(HARD_CODED_USER, expectedTaskList);
        updateUser(hardCodedUser);
    }
}
