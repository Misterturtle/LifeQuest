package turtleraine.sandbox.com.lifequest.dagger;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import turtleraine.sandbox.com.lifequest.Factories.FragmentFactory;
import turtleraine.sandbox.com.lifequest.Factories.IntentFactory;
import turtleraine.sandbox.com.lifequest.repositories.FirestoreRepository;
import turtleraine.sandbox.com.lifequest.services.TaskService;
import turtleraine.sandbox.com.lifequest.state_store.StateStore;
import turtleraine.sandbox.com.lifequest.wrappers.FirestoreWrapper;

@Module
public class AppModule {

    private Context appContext;

    public AppModule(Context appContext) {
        this.appContext = appContext;
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

    @Singleton
    @Provides
    protected StateStore makeStateStore(){
        return new StateStore();
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
