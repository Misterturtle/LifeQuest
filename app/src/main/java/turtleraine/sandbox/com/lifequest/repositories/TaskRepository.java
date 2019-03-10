package turtleraine.sandbox.com.lifequest.repositories;

import java.util.List;

import turtleraine.sandbox.com.lifequest.entities.TaskEntity;

public interface TaskRepository {
    List<TaskEntity> getTasks();
}
