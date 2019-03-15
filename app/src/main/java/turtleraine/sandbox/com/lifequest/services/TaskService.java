package turtleraine.sandbox.com.lifequest.services;

import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import javax.inject.Inject;

import lombok.NonNull;
import lombok.SneakyThrows;
import turtleraine.sandbox.com.lifequest.Application.Injector;
import turtleraine.sandbox.com.lifequest.entities.TaskEntity;
import turtleraine.sandbox.com.lifequest.entities.UserEntity;
import turtleraine.sandbox.com.lifequest.repositories.FirestoreRepository;
import turtleraine.sandbox.com.lifequest.wrappers.FirestoreWrapper;

public class TaskService {

    @Inject
    FirestoreRepository taskRespository;

    @Inject
    FirestoreWrapper wrapper;

    public TaskService() {
        Injector.getInjector().inject(this);
    }

    @SneakyThrows
    public CompletableFuture<List<TaskEntity>> getTasks() {
        return taskRespository.getTasks();
    }

    public void addNewTask(TaskEntity newTask) {
        getTasks()
                .whenComplete((tasks, error) -> {
                    if(tasks != null){
                        ArrayList<TaskEntity> newTaskList = new ArrayList<>(tasks);
                        newTaskList.add(newTask);

                        taskRespository.setTasks(newTaskList);
                    }
                });
    }
}
