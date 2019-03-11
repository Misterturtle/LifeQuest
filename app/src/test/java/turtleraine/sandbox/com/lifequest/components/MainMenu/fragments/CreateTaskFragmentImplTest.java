package turtleraine.sandbox.com.lifequest.components.MainMenu.fragments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import turtleraine.sandbox.com.lifequest.R;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class CreateTaskFragmentImplTest {

    @Mock
    private LayoutInflater mockInflater;

    @Mock
    private View mockView;

    @Mock
    private ViewGroup mockViewGroup;

    CreateTaskFragmentImpl subject;

    @Before
    public void setup() {
        initMocks(this);

        subject = new CreateTaskFragmentImpl();
    }

    @Test
    public void onCreateView_inflatesTheNoTasksFragmentLayout(){
        when(mockInflater.inflate(R.layout.create_task_fragment_layout, mockViewGroup, false)).thenReturn(mockView);

        View result = subject.onCreateView(mockInflater, mockViewGroup, null, null);

        assertSame(mockView, result);
    }

}