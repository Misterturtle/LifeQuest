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

import turtleraine.sandbox.com.lifequest.Application.DaggerTest;
import turtleraine.sandbox.com.lifequest.R;
import turtleraine.sandbox.com.lifequest.entities.TaskEntity;
import turtleraine.sandbox.com.lifequest.services.TaskService;

import static org.junit.Assert.*;
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

    CreateTaskFragmentImpl subject;

    private TaskService mockTaskService;

    @Before
    public void setup() {
        daggerSetup();
        initMocks(this);

        subject = new CreateTaskFragmentImpl();

        mockTaskService = testAppModule.makeTaskService();
    }

    @Test
    public void onCreateView_inflatesTheNoTasksFragmentLayout(){
        when(mockInflater.inflate(R.layout.create_task_fragment_layout, mockViewGroup, false)).thenReturn(mockView);

        View result = subject.onCreateView(mockInflater, mockViewGroup, null, null);

        assertSame(mockView, result);
    }

    @Test
    public void clickingTheCreateTaskButtonSavesTheTask() {
        String expectedTaskName = "newTask";
        TaskEntity expectedTaskEntity = new TaskEntity(expectedTaskName);
        ArgumentCaptor<View.OnClickListener> captor = ArgumentCaptor.forClass(View.OnClickListener.class);
        when(mockView.findViewById(R.id.create_task_button)).thenReturn(mockCreateButton);
        when(mockView.findViewById(R.id.taskNameInput)).thenReturn(mockTaskNameInput);
        when(mockTaskNameInput.getText()).thenReturn(expectedTaskName);

        subject.onViewCreated(mockView, null);

        verify(mockCreateButton).setOnClickListener(captor.capture());
        captor.getValue().onClick(null);

        verify(mockTaskService).addNewTask(expectedTaskEntity);
    }

}