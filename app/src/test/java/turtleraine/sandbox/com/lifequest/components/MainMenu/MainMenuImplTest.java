package turtleraine.sandbox.com.lifequest.components.MainMenu;

import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.Arrays;
import java.util.Collections;

import turtleraine.sandbox.com.lifequest.Application.DaggerTest;
import turtleraine.sandbox.com.lifequest.Factories.FragmentFactory;
import turtleraine.sandbox.com.lifequest.R;
import turtleraine.sandbox.com.lifequest.components.MainMenu.fragments.NoTasksFragmentQtn;
import turtleraine.sandbox.com.lifequest.entities.TaskEntity;
import turtleraine.sandbox.com.lifequest.services.TaskService;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class MainMenuImplTest extends DaggerTest {

    @Mock
    private MainMenuQtn mockQtn;

    @Mock
    private BottomNavigationView mockNavBarView;

    @Mock
    private TaskService mockTaskService;

    @Mock
    private FrameLayout mockFragmentLayout;

    @Mock
    private FragmentManager mockFragmentManager;

    @Mock
    private FragmentTransaction mockTransaction;

    @Mock
    private NoTasksFragmentQtn mockNoTasksFragment;

    MainMenuImpl subject;

    FragmentFactory mockFragmentFactory;

    @Before
    public void setup(){
        daggerSetup();
        initMocks(this);

        mockFragmentFactory = testAppModule.makeFragmentFactory();
        mockTaskService = testAppModule.makeTaskService();

        when(mockQtn.findViewById(R.id.navigation)).thenReturn(mockNavBarView);
        when(mockTransaction.replace(any(Integer.class), any())).thenReturn(mockTransaction);

        when(mockQtn.getSupportFragmentManager()).thenReturn(mockFragmentManager);
        when(mockFragmentManager.beginTransaction()).thenReturn(mockTransaction);
        when(mockFragmentFactory.create(NoTasksFragmentQtn.class)).thenReturn(mockNoTasksFragment);

        subject = new MainMenuImpl();
        subject.onCreate(null, mockQtn);
    }

    @Test
    public void onCreate_SetsTheMainMenuLayout(){
        verify(mockQtn).setContentView(R.layout.main_menu_layout);
    }

    @Test
    public void givenNoTasksTheMainViewReflectsNoTasks() {
        when(mockTaskService.getTasks()).thenReturn(Collections.emptyList());

        subject.onResume(mockQtn);

        verify(mockTransaction).replace(R.id.mainMenuFragmentContainer, mockNoTasksFragment);
        verify(mockTransaction).commit();
    }

    @Test
    public void givenSomeTasksTheMainViewDoesNotReflectNoTasks() {
        when(mockTaskService.getTasks()).thenReturn(Arrays.asList(new TaskEntity("turtle")));

        subject.onResume(mockQtn);

        verify(mockTransaction, never()).replace(any(Integer.class), any());
        verify(mockTransaction, never()).commit();
    }

}