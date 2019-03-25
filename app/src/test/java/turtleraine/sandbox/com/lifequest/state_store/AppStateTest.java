package turtleraine.sandbox.com.lifequest.state_store;

import org.junit.Test;

import static org.junit.Assert.*;

public class AppStateTest {

    @Test
    public void aNewAppStateDoesNotHaveNullViewModels(){
        AppState subject = AppState.builder().build();

        assertNotNull(subject.createTaskViewModel);
    }

    @Test
    public void copyReturnsADeepCopyOfTheAppState(){
        CreateTaskViewModel expectedViewModel = CreateTaskViewModel.builder()
                .title("Deep")
                .build();

        AppState subject = AppState.builder()
                .createTaskViewModel(expectedViewModel)
                .build();

        AppState copiedAppState = subject.copy();

        assertEquals(expectedViewModel, copiedAppState.createTaskViewModel);
    }
}