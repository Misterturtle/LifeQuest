package turtleraine.sandbox.com.lifequest.dagger;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import turtleraine.sandbox.com.lifequest.Application.MainActivityImpl;
import turtleraine.sandbox.com.lifequest.Factories.FragmentFactory;
import turtleraine.sandbox.com.lifequest.Factories.IntentFactory;
import turtleraine.sandbox.com.lifequest.components.MainMenu.MainMenuImpl;
import turtleraine.sandbox.com.lifequest.components.MainMenu.fragments.CreateTaskFragmentImpl;
import turtleraine.sandbox.com.lifequest.components.MainMenu.fragments.NoTasksFragmentImpl;
import turtleraine.sandbox.com.lifequest.repositories.FirestoreRepository;
import turtleraine.sandbox.com.lifequest.services.TaskService;
import turtleraine.sandbox.com.lifequest.wrappers.FirestoreWrapper;

@Module
public class AppModule {

    private Context appContext;

    public AppModule(Context appContext) {
        this.appContext = appContext;
    }


    // ------------------------- Impl --------------------- //
    @Singleton
    @Provides
    protected MainActivityImpl makeMainActivityImpl() {
        return new MainActivityImpl();
    }

    @Singleton
    @Provides
    protected NoTasksFragmentImpl makeNoTasksFragmentImpl() {
        return new NoTasksFragmentImpl();
    }

    @Singleton
    @Provides
    protected MainMenuImpl makeMainMenuImpl() {
        return new MainMenuImpl();
    }

    @Singleton
    @Provides
    protected CreateTaskFragmentImpl createTaskFragment() {
        return new CreateTaskFragmentImpl();
    }

    // ----------------------- Services ------------------ //

    @Singleton
    @Provides
    protected FirestoreWrapper makeFirestoreWrapper() {
        return new FirestoreWrapper();
    }

    @Singleton
    @Provides
    protected TaskService makeTaskService() {
        return new TaskService();
    }

    @Singleton
    @Provides
    protected FirestoreRepository makeFirestoreRepository() {
        return new FirestoreRepository();
    }

    // ----------------------- Factories -------------------- //

    @Provides
    @Singleton
    protected IntentFactory makeIntentFactory() {
        return new IntentFactory();
    }

    @Provides
    @Singleton
    protected FragmentFactory makeFragmentFactory() {
        return new FragmentFactory();
    }
}
