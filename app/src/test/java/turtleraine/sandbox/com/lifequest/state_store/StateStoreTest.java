package turtleraine.sandbox.com.lifequest.state_store;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.UUID;

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
        TaskEntity taskEntity = TaskEntity.builder().title("SomeTask").build();

        subject.updateState(appState -> {
            appState.taskBeingCreated.title = "SomeTask";
            return appState;
        });

        AppState result = subject.getAppState();
        assertSame(expectedAppState, result);
    }

    @Test
    public void updateState_triggersAllSubscriptions() {
        TaskEntity originalTask = TaskEntity.builder()
                .title("Original Task")
                .build();
        AppState newAppState = AppState.builder()
                .taskBeingCreated(originalTask)
                .build();

        AppState[] updatedAppState = new AppState[1];
        subject.subscribe(appState -> updatedAppState[0] = appState);

        subject.updateState(newAppState);

        assertEquals(newAppState, updatedAppState[0]);
    }

    @Test
    public void atTheEndOfAnUpdateCycleItTriggersTheNextPendingState() {
        AppState originalState = subject.getAppState();
        TaskEntity task1 = TaskEntity.builder()
                .title("Original Task")
                .build();
        AppState state1 = AppState.builder()
                .taskBeingCreated(task1)
                .build();


        TaskEntity task2 = TaskEntity.builder()
                .title("Another Task")
                .build();
        AppState state2 = AppState.builder()
                .taskBeingCreated(task2)
                .build();


        TaskEntity task3 = TaskEntity.builder()
                .title("Some Other Task")
                .build();
        AppState state3 = AppState.builder()
                .taskBeingCreated(task3)
                .build();

        subject.pendingAppStates.add(state1);
        subject.pendingAppStates.add(state2);
        ArrayList<AppState> stateSnapshots = new ArrayList<>();

        subject.subscribe(stateSnapshots::add);
        subject.updateState(state3);

        assertEquals(originalState, stateSnapshots.get(0));
        assertEquals(state1, stateSnapshots.get(1));
        assertEquals(state2, stateSnapshots.get(2));
        assertEquals(state3, stateSnapshots.get(3));
    }

    @Test
    public void aSubscriptionThatUpdatesStateDoesNotUpdateStateMidUpdateCycle() {
        ArrayList<AppState> stateSnapshots = new ArrayList<>();

        TaskEntity originalTask = TaskEntity.builder()
                .title("Original Task")
                .build();
        AppState originalAppState = AppState.builder()
                .taskBeingCreated(originalTask)
                .build();


        TaskEntity anotherTask = TaskEntity.builder()
                .title("Another Task")
                .build();
        AppState anotherAppState = AppState.builder()
                .taskBeingCreated(anotherTask)
                .build();

        subject.subscribe(appState -> {
            subject.updateState(originalAppState);
        });

        subject.subscribe(stateSnapshots::add);

        subject.updateState(anotherAppState);

        assertEquals(originalAppState, stateSnapshots.get(0));
        assertEquals(anotherAppState, stateSnapshots.get(1));
        assertEquals(originalAppState, stateSnapshots.get(2));
        assertEquals(3, stateSnapshots.size());
    }

    @Test
    public void aSubscriptionWillNotTriggerIfTheAppStateIsExactlyTheSame() {
        AppState originalState = subject.getAppState();
        TaskEntity newTask = TaskEntity.builder()
                .title("Some Task")
                .build();
        AppState state1 = AppState.builder()
                .taskBeingCreated(newTask)
                .build();

        subject.pendingAppStates.add(state1);
        subject.pendingAppStates.add(state1);
        ArrayList<AppState> stateSnapshots = new ArrayList<>();

        subject.subscribe(stateSnapshots::add);
        subject.updateState(state1);

        assertEquals(originalState, stateSnapshots.get(0));
        assertEquals(state1, stateSnapshots.get(1));
        assertEquals(2, stateSnapshots.size());
    }

    @Test
    public void aSubscriptionWillNotTriggerIfTheAppStateIsEqualButNotTheSame() {
        AppState originalState = subject.getAppState();
        TaskEntity newTask = TaskEntity.builder()
                .title("Original Task")
                .build();
        AppState newAppState = AppState.builder()
                .taskBeingCreated(newTask)
                .build();


        TaskEntity anotherTask = TaskEntity.builder()
                .title("Original Task")
                .build();
        AppState anotherAppState = AppState.builder()
                .taskBeingCreated(anotherTask)
                .build();

        subject.pendingAppStates.add(newAppState);
        subject.pendingAppStates.add(anotherAppState);
        ArrayList<AppState> stateSnapshots = new ArrayList<>();

        subject.subscribe(stateSnapshots::add);
        subject.updateState(anotherAppState);

        assertEquals(newAppState, anotherAppState);
        assertEquals(originalState, stateSnapshots.get(0));
        assertEquals(newAppState, stateSnapshots.get(1));
        assertEquals(2, stateSnapshots.size());
    }

    @Test
    public void cyclicalSubscriptionsWillThrowAnError() {

        try {
            subject.subscribe(appState -> {
                TaskEntity task = TaskEntity.builder()
                        .title("Task1")
                        .build();
                AppState state = AppState.builder()
                        .taskBeingCreated(task)
                        .build();
                subject.updateState(state);
            });

            subject.subscribe(appState -> {
                TaskEntity task = TaskEntity.builder()
                        .title("Task2")
                        .build();
                AppState state = AppState.builder()
                        .taskBeingCreated(task)
                        .build();
                subject.updateState(state);
            });

            fail();
        } catch (Exception e) {
            assertEquals(new CyclicalUpdate(), e);
        }
    }

}