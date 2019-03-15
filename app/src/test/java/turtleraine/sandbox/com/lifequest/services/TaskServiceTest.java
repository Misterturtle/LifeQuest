package turtleraine.sandbox.com.lifequest.services;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import lombok.SneakyThrows;
import turtleraine.sandbox.com.lifequest.Application.DaggerTest;
import turtleraine.sandbox.com.lifequest.entities.TaskEntity;
import turtleraine.sandbox.com.lifequest.repositories.TaskRepository;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TaskServiceTest extends DaggerTest {

    private TaskService subject;

    private TaskRepository mockTaskRepository;

    @Before
    public void setup(){
        daggerSetup();

        mockTaskRepository = testAppModule.makeFirestoreRepository();

        subject = new TaskService();
    }

    @SneakyThrows
    @Test
    public void getTasks_fetchesTasksFromRespository() {
        List<TaskEntity> expectedTaskList = Arrays.asList(new TaskEntity("Turtle"), new TaskEntity("Tuttle"));

        when(mockTaskRepository.getTasks()).thenReturn(CompletableFuture.completedFuture(expectedTaskList));

        CompletableFuture<List<TaskEntity>> tasks = subject.getTasks();

        assertSame(expectedTaskList, tasks.get());
    }

    @Test
    public void addNewTask_updatesTheTaskListWithTheNewTaskAppended() {
        List<TaskEntity> existingTaskList = Arrays.asList(new TaskEntity("Turtle"), new TaskEntity("Tuttle"));
        when(mockTaskRepository.getTasks()).thenReturn(CompletableFuture.completedFuture(existingTaskList));

        TaskEntity newTask = new TaskEntity("newTask");

        subject.addNewTask(newTask);

        List<TaskEntity> expectedTaskList = Arrays.asList(new TaskEntity("Turtle"), new TaskEntity("Tuttle"), newTask);
        verify(mockTaskRepository).setTasks(expectedTaskList);
    }


}