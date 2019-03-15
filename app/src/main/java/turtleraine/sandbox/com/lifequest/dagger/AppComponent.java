package turtleraine.sandbox.com.lifequest.dagger;

import javax.inject.Singleton;

import dagger.Component;
import turtleraine.sandbox.com.lifequest.Application.MainActivity;
import turtleraine.sandbox.com.lifequest.Application.MainActivityImpl;
import turtleraine.sandbox.com.lifequest.components.MainMenu.MainMenuImpl;
import turtleraine.sandbox.com.lifequest.components.MainMenu.MainMenuQtn;
import turtleraine.sandbox.com.lifequest.components.MainMenu.fragments.CreateTaskFragmentImpl;
import turtleraine.sandbox.com.lifequest.components.MainMenu.fragments.CreateTaskFragmentQtn;
import turtleraine.sandbox.com.lifequest.components.MainMenu.fragments.NoTasksFragmentQtn;
import turtleraine.sandbox.com.lifequest.repositories.FirestoreRepository;
import turtleraine.sandbox.com.lifequest.services.TaskService;


@Singleton
@Component(modules = { AppModule.class})
public interface AppComponent {
    void inject(MainActivity mainActivity);
    void inject(MainActivityImpl mainActivity);
    void inject(MainMenuQtn mainMenuQtn);
    void inject(MainMenuImpl mainMenu);
    void inject(TaskService taskService);
    void inject(NoTasksFragmentQtn noTasksFragmentQtn);
    void inject(CreateTaskFragmentQtn createTaskFragmentQtn);
    void inject(FirestoreRepository firestoreRepository);
    void inject(CreateTaskFragmentImpl createTaskFragment);
}
