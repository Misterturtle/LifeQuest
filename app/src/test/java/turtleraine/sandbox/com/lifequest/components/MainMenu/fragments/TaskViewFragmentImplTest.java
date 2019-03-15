package turtleraine.sandbox.com.lifequest.components.MainMenu.fragments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import turtleraine.sandbox.com.lifequest.Application.DaggerTest;
import turtleraine.sandbox.com.lifequest.R;
import turtleraine.sandbox.com.lifequest.components.common.TaskCardViewQtn;
import turtleraine.sandbox.com.lifequest.entities.TaskEntity;
import turtleraine.sandbox.com.lifequest.services.TaskService;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class TaskViewFragmentImplTest extends DaggerTest {

    @Mock
    private LayoutInflater mockInflater;

    @Mock
    private View mockView;

    @Mock
    private ViewGroup mockViewGroup;

    @Mock
    private LinearLayout mockLinearLayout;

    @Mock
    private TaskViewFragmentQtn mockQtn;

    TaskViewFragmentImpl subject;

    TaskService mockTaskService;

    @Before
    public void setup() {
        daggerSetup();
        initMocks(this);

        when(mockQtn.getLayoutInflater()).thenReturn(mockInflater);
        when(mockView.findViewById(R.id.task_view_container_layout)).thenReturn(mockLinearLayout);

        subject = new TaskViewFragmentImpl();

        mockTaskService = testAppModule.makeTaskService();
    }

    @Test
    public void onCreateView_inflatesTheNoTasksFragmentLayout(){
        when(mockInflater.inflate(R.layout.task_view_fragment_layout, mockViewGroup, false)).thenReturn(mockView);

        View result = subject.onCreateView(mockInflater, mockViewGroup, null, null);

        assertSame(mockView, result);
    }

    @Test
    public void onViewCreated_createsATaskViewComponentForEachTaskRetrieved() {
        TaskCardViewQtn mockTaskView1 = mock(TaskCardViewQtn.class);
        TaskCardViewQtn mockTaskView2 = mock(TaskCardViewQtn.class);

        TaskEntity firstTaskEntity = new TaskEntity("First Task");
        TaskEntity secondTaskEntity = new TaskEntity("Second Task");

        List<TaskEntity> retrievedTasks = Arrays.asList(firstTaskEntity, secondTaskEntity);
        when(mockTaskService.getTasks()).thenReturn(CompletableFuture.completedFuture(retrievedTasks));
        when(mockInflater.inflate(R.layout.task_card_view_layout, mockLinearLayout, false)).thenReturn(mockTaskView1, mockTaskView2);

        subject.onViewCreated(mockView, null, mockQtn);

        verify(mockTaskView1).populate(firstTaskEntity);
        verify(mockTaskView2).populate(secondTaskEntity);
        verify(mockLinearLayout).addView(mockTaskView1);
        verify(mockLinearLayout).addView(mockTaskView2);
    }

}