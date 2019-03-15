package turtleraine.sandbox.com.lifequest.components.MainMenu;

import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;
import android.widget.FrameLayout;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;

import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.CompletableFuture;

import turtleraine.sandbox.com.lifequest.Application.DaggerTest;
import turtleraine.sandbox.com.lifequest.Factories.FragmentFactory;
import turtleraine.sandbox.com.lifequest.R;
import turtleraine.sandbox.com.lifequest.components.MainMenu.fragments.CreateTaskFragmentQtn;
import turtleraine.sandbox.com.lifequest.components.MainMenu.fragments.NoTasksFragmentQtn;
import turtleraine.sandbox.com.lifequest.components.MainMenu.fragments.TaskViewFragmentQtn;
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

    @Mock
    private MenuItem mockMenuItem;

    @Mock
    private CreateTaskFragmentQtn mockCreateTaskFragmentQtn;

    @Mock
    private TaskViewFragmentQtn mockTaskViewFragment;

    MainMenuImpl subject;

    FragmentFactory mockFragmentFactory;

    @Before
    public void setup(){
        daggerSetup();
        initMocks(this);

        mockFragmentFactory = testAppModule.makeFragmentFactory();
        mockTaskService = testAppModule.makeTaskService();

        when(mockQtn.findViewById(R.id.main_menu_bottom_nav_bar)).thenReturn(mockNavBarView);
        when(mockTransaction.replace(any(Integer.class), any())).thenReturn(mockTransaction);

        when(mockQtn.getSupportFragmentManager()).thenReturn(mockFragmentManager);
        when(mockFragmentManager.beginTransaction()).thenReturn(mockTransaction);
        when(mockFragmentFactory.create(NoTasksFragmentQtn.class)).thenReturn(mockNoTasksFragment);
        when(mockFragmentFactory.create(CreateTaskFragmentQtn.class)).thenReturn(mockCreateTaskFragmentQtn);
        when(mockFragmentFactory.create(TaskViewFragmentQtn.class)).thenReturn(mockTaskViewFragment);

        subject = new MainMenuImpl();
        subject.onCreate(null, mockQtn);
    }

    @Test
    public void onCreate_SetsTheMainMenuLayout(){
        verify(mockQtn).setContentView(R.layout.main_menu_layout);
    }

    @Test
    public void whenCreateTaskNavButtonIsTappedThenTheCreateTaskFragmentIsLoaded() {
        ArgumentCaptor<BottomNavigationView.OnNavigationItemSelectedListener> captor = ArgumentCaptor.forClass(BottomNavigationView.OnNavigationItemSelectedListener.class);
        verify(mockNavBarView).setOnNavigationItemSelectedListener(captor.capture());

        when(mockMenuItem.getItemId()).thenReturn(R.id.navigation_create_task);

        captor.getValue().onNavigationItemSelected(mockMenuItem);

        verify(mockTransaction).replace(R.id.main_menu_fragment_container, mockCreateTaskFragmentQtn);
        verify(mockTransaction).commit();
    }

    @Test
    public void givenNoTasksTheMainViewReflectsNoTasks() {
        when(mockTaskService.getTasks()).thenReturn(CompletableFuture.completedFuture(Collections.emptyList()));

        subject.onResume(mockQtn);

        verify(mockTransaction).replace(R.id.main_menu_fragment_container, mockNoTasksFragment);
        verify(mockTransaction).commit();
    }

    @Test
    public void givenSomeTasksTheMainViewDoesNotReflectsTaskView() {
        when(mockTaskService.getTasks()).thenReturn(CompletableFuture.completedFuture(Arrays.asList(new TaskEntity("turtle"))));

        subject.onResume(mockQtn);

        verify(mockTransaction).replace(R.id.main_menu_fragment_container, mockTaskViewFragment);
        verify(mockTransaction).commit();
    }
}