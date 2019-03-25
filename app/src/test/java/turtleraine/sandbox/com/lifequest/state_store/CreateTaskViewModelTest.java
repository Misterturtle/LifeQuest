package turtleraine.sandbox.com.lifequest.state_store;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CreateTaskViewModelTest {

    CreateTaskViewModel subject;

    @Test
    public void copyReturnsANewInstanceWithTheSameData(){
        CreateTaskViewModel subject = CreateTaskViewModel.builder().title("SomeTitle").build();

        CreateTaskViewModel result = subject.copy();

        assertEquals(subject, result);
        assertNotSame(subject, result);
    }

}