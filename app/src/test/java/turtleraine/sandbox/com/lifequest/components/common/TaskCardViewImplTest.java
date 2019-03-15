package turtleraine.sandbox.com.lifequest.components.common;

import android.widget.TextView;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import turtleraine.sandbox.com.lifequest.R;
import turtleraine.sandbox.com.lifequest.entities.TaskEntity;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class TaskCardViewImplTest {

    @Mock
    private TaskCardViewQtn mockQtn;

    @Mock
    private TextView mockTaskNameTextView;

    @Mock
    private TextView mockTaskDescriptionTextView;

    TaskCardViewImpl subject;

    @Before
    public void setup(){
        initMocks(this);

        when(mockQtn.findViewById(R.id.task_name_text_view)).thenReturn(mockTaskNameTextView);
        when(mockQtn.findViewById(R.id.task_description_text_view)).thenReturn(mockTaskDescriptionTextView);

        subject = new TaskCardViewImpl();
    }

    @Test
    public void populateSetsTheTaskName(){
        String expectedTaskName = "SomeName";
        TaskEntity task = TaskEntity.builder()
                .title(expectedTaskName)
                .build();

        subject.populate(task, mockQtn);

        verify(mockTaskNameTextView).setText(expectedTaskName);
    }

    @Test
    public void populateSetsTheTaskDescription(){
        String expectedTaskDescription = "SomeDescription";
        TaskEntity task = TaskEntity.builder()
                .description(expectedTaskDescription)
                .build();

        subject.populate(task, mockQtn);

        verify(mockTaskDescriptionTextView).setText(expectedTaskDescription);
    }

}