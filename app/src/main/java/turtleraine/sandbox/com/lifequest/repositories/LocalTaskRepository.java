package turtleraine.sandbox.com.lifequest.repositories;

import java.util.Collections;
import java.util.List;

import turtleraine.sandbox.com.lifequest.entities.TaskEntity;

public class LocalTaskRepository implements TaskRepository {

    @Override
    public List<TaskEntity> getTasks() {
        return Collections.emptyList();
    }
}
