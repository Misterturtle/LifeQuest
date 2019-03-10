package turtleraine.sandbox.com.lifequest.services;

import java.util.List;

import javax.inject.Inject;

import turtleraine.sandbox.com.lifequest.Application.Injector;
import turtleraine.sandbox.com.lifequest.entities.TaskEntity;
import turtleraine.sandbox.com.lifequest.repositories.LocalTaskRepository;

public class TaskService {

    @Inject
    LocalTaskRepository localTaskRespository;


    public TaskService() {
        Injector.getInjector().inject(this);
    }

    public List<TaskEntity> getTasks() {
        return localTaskRespository.getTasks();
    }
}
