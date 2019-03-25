package turtleraine.sandbox.com.lifequest.state_store;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import turtleraine.sandbox.com.lifequest.common.exceptions.CyclicalUpdate;
import turtleraine.sandbox.com.lifequest.entities.TaskEntity;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.fail;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.verify;

public class StateStoreTest {

    StateStore subject;


    @Before
    public void setup() {
        subject = new StateStore();
    }

    @Test
    public void subscribe_registersTheSubscription() {
        boolean[] wasCalled = new boolean[1];
        wasCalled[0] = false;

        subject.subscribe(appState -> wasCalled[0] = true);
        subject.getSubscriptions().get(0).updateFunction.accept(null);

        assertTrue(wasCalled[0]);
    }

    @Test
    public void subscribe_immediatelyTriggersTheUpdateFunctionWithTheCurrentState() {
        boolean[] wasCalled = new boolean[1];
        wasCalled[0] = false;

        subject.subscribe(appState -> wasCalled[0] = true);

        assertTrue(wasCalled[0]);
    }

    @Test
    public void updateState_updatesTheCurrentState() {
        String expectedTitle = "SomeTask";

        subject.updateState(appState -> {
            appState.createTaskViewModel.title = expectedTitle;
        });

        AppState result = subject.getAppState();
        assertEquals(expectedTitle, result.createTaskViewModel.title);
    }

    @Test
    public void updateState_triggersAllSubscriptions() {
        String expectedTitle = "Original Task";
        AppState[] updatedAppState = new AppState[1];
        subject.subscribe(appState -> updatedAppState[0] = appState);

        subject.updateState(appState -> appState.createTaskViewModel.title = expectedTitle);

        assertEquals(expectedTitle, updatedAppState[0].createTaskViewModel.title);
    }

    @Test
    public void atTheEndOfAnUpdateCycleItTriggersTheNextPendingState() {
        AppState originalState = subject.getAppState();
        CreateTaskViewModel task1 = CreateTaskViewModel.builder()
                .title("Original Task")
                .build();
        AppState state1 = AppState.builder()
                .createTaskViewModel(task1)
                .build();


        CreateTaskViewModel task2 = CreateTaskViewModel.builder()
                .title("Another Task")
                .build();
        AppState state2 = AppState.builder()
                .createTaskViewModel(task2)
                .build();


        String expectedTitle = "Some Other Task";
        CreateTaskViewModel task3 = CreateTaskViewModel.builder()
                .title(expectedTitle)
                .build();
        AppState state3 = AppState.builder()
                .createTaskViewModel(task3)
                .build();

        subject.pendingAppStates.add(state1);
        subject.pendingAppStates.add(state2);
        ArrayList<AppState> stateSnapshots = new ArrayList<>();

        subject.subscribe(stateSnapshots::add);
        subject.updateState(appState -> appState.createTaskViewModel.title = expectedTitle);

        assertEquals(originalState, stateSnapshots.get(0));
        assertEquals(state1, stateSnapshots.get(1));
        assertEquals(state2, stateSnapshots.get(2));
        assertEquals(state3, stateSnapshots.get(3));
    }

    @Test
    public void aSubscriptionThatUpdatesStateDoesNotUpdateStateMidUpdateCycle() {
        ArrayList<AppState> stateSnapshots = new ArrayList<>();

        String originalTitle = "Original Task";
        String anotherTitle = "Another Task";

        subject.subscribe(appState -> {
            subject.updateState(appStateCopy -> appStateCopy.createTaskViewModel.title = originalTitle);
        });

        subject.subscribe(stateSnapshots::add);

        subject.updateState(appStateCopy -> appStateCopy.createTaskViewModel.title = anotherTitle);

        assertEquals(originalTitle, stateSnapshots.get(0).createTaskViewModel.title);
        assertEquals(anotherTitle, stateSnapshots.get(1).createTaskViewModel.title);
        assertEquals(originalTitle, stateSnapshots.get(2).createTaskViewModel.title);
        assertEquals(3, stateSnapshots.size());
    }

    @Test
    public void aSubscriptionWillNotTriggerIfTheAppStateIsExactlyTheSame() {
        AppState originalState = subject.getAppState();
        String expectedTitle = "Some Task";
        CreateTaskViewModel newTask = CreateTaskViewModel.builder()
                .title(expectedTitle)
                .build();
        AppState state1 = AppState.builder()
                .createTaskViewModel(newTask)
                .build();

        subject.pendingAppStates.add(state1);
        subject.pendingAppStates.add(state1);
        ArrayList<AppState> stateSnapshots = new ArrayList<>();

        subject.subscribe(stateSnapshots::add);
        subject.updateState(appState -> appState.createTaskViewModel.title = expectedTitle);

        assertEquals(originalState, stateSnapshots.get(0));
        assertEquals(state1, stateSnapshots.get(1));
        assertEquals(2, stateSnapshots.size());
    }

    @Test
    public void aSubscriptionWillNotTriggerIfTheAppStateIsEqualButNotTheSame() {
        AppState originalState = subject.getAppState();

        String expectedTitle = "Original Task";
        CreateTaskViewModel newTask = CreateTaskViewModel.builder()
                .title(expectedTitle)
                .build();
        AppState newAppState = AppState.builder()
                .createTaskViewModel(newTask)
                .build();


        CreateTaskViewModel anotherTask = CreateTaskViewModel.builder()
                .title(expectedTitle)
                .build();
        AppState anotherAppState = AppState.builder()
                .createTaskViewModel(anotherTask)
                .build();

        subject.pendingAppStates.add(newAppState);
        subject.pendingAppStates.add(anotherAppState);

        ArrayList<AppState> stateSnapshots = new ArrayList<>();
        subject.subscribe(stateSnapshots::add);

        subject.updateState(appState -> appState.createTaskViewModel.title = expectedTitle);

        assertEquals(newAppState, anotherAppState);
        assertEquals(originalState, stateSnapshots.get(0));
        assertEquals(newAppState, stateSnapshots.get(1));
        assertEquals(2, stateSnapshots.size());
    }

    @Test
    public void cyclicalSubscriptionsWillThrowAnError() {

        String task1 = "Task1";
        String task2 = "Task2";

        try {
            subject.subscribe(appState -> {
                subject.updateState(mutatableAppState -> mutatableAppState.createTaskViewModel.title = task1);
            });

            subject.subscribe(appState -> {
                subject.updateState(mutatableAppState -> mutatableAppState.createTaskViewModel.title = task2);
            });

            fail();
        } catch (Exception e) {
            assertEquals(new CyclicalUpdate(), e);
        }
    }

}