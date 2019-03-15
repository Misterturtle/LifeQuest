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

    private final static TaskEntity firstTaskEntity = TaskEntity.builder()
            .title("First Task")
            .build();

    private final static TaskEntity secondTaskEntity = TaskEntity.builder()
            .title("second Task")
            .build();

    @Before
    public void setup() {
        daggerSetup();

        mockTaskRepository = testAppModule.makeFirestoreRepository();

        subject = new TaskService();
    }

    @SneakyThrows
    @Test
    public void getTasks_fetchesTasksFromRespository() {

        List<TaskEntity> expectedTaskList = Arrays.asList(firstTaskEntity, secondTaskEntity);

        when(mockTaskRepository.getTasks()).thenReturn(CompletableFuture.completedFuture(expectedTaskList));

        CompletableFuture<List<TaskEntity>> tasks = subject.getTasks();

        assertSame(expectedTaskList, tasks.get());
    }

    @Test
    public void addNewTask_updatesTheTaskListWithTheNewTaskAppended() {
        List<TaskEntity> existingTaskList = Arrays.asList(firstTaskEntity, secondTaskEntity);
        when(mockTaskRepository.getTasks()).thenReturn(CompletableFuture.completedFuture(existingTaskList));

        TaskEntity newTask = TaskEntity.builder()
                .title("newTask")
                .build();

        subject.addNewTask(newTask);

        List<TaskEntity> expectedTaskList = Arrays.asList(firstTaskEntity, secondTaskEntity, newTask);
        verify(mockTaskRepository).setTasks(expectedTaskList);
    }


}