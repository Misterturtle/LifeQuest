package turtleraine.sandbox.com.lifequest.repositories;

import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

import turtleraine.sandbox.com.lifequest.entities.TaskEntity;

import static org.junit.Assert.*;

public class LocalTaskRepositoryTest {

    LocalTaskRepository subject;

    @Before
    public void setup(){
        subject = new LocalTaskRepository();
    }

    @Test
    public void getTasks_returnsAnEmptyList(){
        List<TaskEntity> result = subject.getTasks();

        assertEquals(Collections.emptyList(), result);
    }

}