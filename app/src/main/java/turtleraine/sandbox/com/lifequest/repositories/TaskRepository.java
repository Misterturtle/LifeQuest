package turtleraine.sandbox.com.lifequest.repositories;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import turtleraine.sandbox.com.lifequest.entities.TaskEntity;

public interface TaskRepository {
    CompletableFuture<List<TaskEntity>> getTasks();

    void setTasks(List<TaskEntity> expectedTaskList);
}
