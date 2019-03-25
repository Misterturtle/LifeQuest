package turtleraine.sandbox.com.lifequest.components.MainMenu.fragments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;

import java.util.function.Consumer;

import turtleraine.sandbox.com.lifequest.Application.DaggerTest;
import turtleraine.sandbox.com.lifequest.R;
import turtleraine.sandbox.com.lifequest.entities.TaskEntity;
import turtleraine.sandbox.com.lifequest.services.TaskService;
import turtleraine.sandbox.com.lifequest.state_store.AppState;
import turtleraine.sandbox.com.lifequest.state_store.CreateTaskViewModel;
import turtleraine.sandbox.com.lifequest.state_store.StateStore;
import turtleraine.sandbox.com.lifequest.utils.FnHelper;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class CreateTaskFragmentImplTest extends DaggerTest {

    @Mock
    private LayoutInflater mockInflater;

    @Mock
    private View mockView;

    @Mock
    private ViewGroup mockViewGroup;

    @Mock
    private Button mockCreateButton;

    @Mock
    private TextView mockTaskNameInput;

    @Mock
    private TextView mockTaskDescriptionInput;

    CreateTaskFragmentImpl subject;

    private TaskService mockTaskService;
    private StateStore mockStateStore;

    @Before
    public void setup() {
        daggerSetup();
        initMocks(this);

        subject = new CreateTaskFragmentImpl();

        mockTaskService = testAppModule.makeTaskService();
        mockStateStore = testAppModule.makeStateStore();

        when(mockView.findViewById(R.id.create_task_button)).thenReturn(mockCreateButton);
        when(mockView.findViewById(R.id.task_name_input)).thenReturn(mockTaskNameInput);
        when(mockView.findViewById(R.id.task_description_input)).thenReturn(mockTaskDescriptionInput);
    }

    @Test
    public void onCreateView_inflatesTheNoTasksFragmentLayout() {
        when(mockInflater.inflate(R.layout.create_task_fragment_layout, mockViewGroup, false)).thenReturn(mockView);

        View result = subject.onCreateView(mockInflater, mockViewGroup, null, null);

        assertSame(mockView, result);
    }

    @Test
    public void clickingTheCreateTaskButtonSavesTheTask() {
        String expectedTaskName = "newTask";
        String expectedTaskDescription = "taskDescription";
        TaskEntity expectedTaskEntity = new TaskEntity(expectedTaskName, expectedTaskDescription);
        ArgumentCaptor<View.OnClickListener> captor = ArgumentCaptor.forClass(View.OnClickListener.class);

        when(mockTaskNameInput.getText()).thenReturn(expectedTaskName);
        when(mockTaskDescriptionInput.getText()).thenReturn(expectedTaskDescription);

        subject.onViewCreated(mockView, null);

        verify(mockCreateButton).setOnClickListener(captor.capture());
        captor.getValue().onClick(null);

        verify(mockTaskService).addNewTask(expectedTaskEntity);
    }

    @Test
    public void onViewCreated_bindsTheCreateTaskPageToTheAppStore() {
        String expectedTitle = "Turtle Task";
        String expectedDescription = "A task for a turtle, duh";
        CreateTaskViewModel expectedTask = CreateTaskViewModel.builder()
                .title(expectedTitle)
                .description(expectedDescription)
                .build();
        AppState state = AppState.builder()
                .createTaskViewModel(expectedTask)
                .build();

        FnHelper.callThroughConsumerWith(state).when(mockStateStore).subscribe(any());

        subject.onViewCreated(mockView, null);

        verify(mockTaskNameInput).setText(expectedTitle);
        verify(mockTaskDescriptionInput).setText(expectedDescription);
    }

    @Test
    public void whenLosingFocusOfTaskNameInputItUpdatesTheAppStateWithTheInput() {
        AppState initialAppState = AppState.builder().build();

        String expectedTaskName = "SomeTaskName";
        when(mockTaskNameInput.getText()).thenReturn(expectedTaskName);

        FnHelper.callThroughFocusChangeListenerWith(null, false).when(mockTaskNameInput).setOnFocusChangeListener(any());

        FnHelper.callThroughConsumerWith(initialAppState).when(mockStateStore).updateState(any());

        subject.onViewCreated(mockView, null);

        assertEquals(expectedTaskName, initialAppState.createTaskViewModel.title);
    }


}