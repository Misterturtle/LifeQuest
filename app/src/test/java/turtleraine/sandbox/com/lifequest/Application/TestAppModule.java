package turtleraine.sandbox.com.lifequest.Application;

import android.content.Context;

import org.mockito.Mock;

import turtleraine.sandbox.com.lifequest.Factories.FragmentFactory;
import turtleraine.sandbox.com.lifequest.Factories.IntentFactory;
import turtleraine.sandbox.com.lifequest.dagger.AppModule;
import turtleraine.sandbox.com.lifequest.repositories.FirestoreRepository;
import turtleraine.sandbox.com.lifequest.services.TaskService;
import turtleraine.sandbox.com.lifequest.wrappers.FirestoreWrapper;

import static org.mockito.Mockito.mock;
import static org.mockito.MockitoAnnotations.initMocks;

public class TestAppModule extends AppModule {

    public TestAppModule(Context appContext) {
        super(appContext);
        initMocks(this);
    }


    @Mock
    private FirestoreWrapper mockFirestoreWrapper;
    @Override
    public FirestoreWrapper makeFirestoreWrapper() {
        return mockFirestoreWrapper;
    }

    @Mock
    private FragmentFactory mockFragmentFactory;
    @Override
    public FragmentFactory makeFragmentFactory() {
        return mockFragmentFactory;
    }

    @Mock
    private MainActivityImpl mockMainActivityImpl;
    @Override
    public MainActivityImpl makeMainActivityImpl() {
        return mockMainActivityImpl;
    }

    @Mock
    private IntentFactory mockIntentFactory;
    @Override
    public IntentFactory makeIntentFactory() {
        return mockIntentFactory;
    }

    @Mock
    private TaskService mockTaskService;
    @Override
    public TaskService makeTaskService() {
        return mockTaskService;
    }

    @Mock
    private FirestoreRepository mockFirestoreRepository;
    @Override
    public FirestoreRepository makeFirestoreRepository() {
        return mockFirestoreRepository;
    }
}
