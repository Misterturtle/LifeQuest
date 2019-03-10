package turtleraine.sandbox.com.lifequest.Application;

import android.content.Context;

import static org.mockito.Mockito.mock;

/**
 * Created by rconaway on 2/18/19.
 */

public class DaggerTest {

    protected Context appContext;
    protected TestAppModule testAppModule;

    public void daggerSetup(){
        appContext = mock(Context.class);
        testAppModule = new TestAppModule(appContext);
        Injector.createInjector(testAppModule);
    }
}
