package turtleraine.sandbox.com.lifequest.services;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.Arrays;
import java.util.List;

import turtleraine.sandbox.com.lifequest.Application.DaggerTest;
import turtleraine.sandbox.com.lifequest.entities.TaskEntity;
import turtleraine.sandbox.com.lifequest.repositories.TaskRepository;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class TaskServiceTest extends DaggerTest {

    private TaskService subject;

    private TaskRepository mockTaskRepository;

    @Before
    public void setup(){
        daggerSetup();

        mockTaskRepository = testAppModule.makeLocalTaskRepository();

        subject = new TaskService();
    }

    @Test
    public void getTasks_fetchesTasksFromRespository() {
        List<TaskEntity> expectedTaskList = Arrays.asList(new TaskEntity("Turtle"), new TaskEntity("Tuttle"));

        when(mockTaskRepository.getTasks()).thenReturn(expectedTaskList);

        List<TaskEntity> result = subject.getTasks();

        assertSame(expectedTaskList, result);
    }


}