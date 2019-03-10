package turtleraine.sandbox.com.lifequest.Application;

import android.content.Context;

import turtleraine.sandbox.com.lifequest.dagger.AppComponent;
import turtleraine.sandbox.com.lifequest.dagger.AppModule;
import turtleraine.sandbox.com.lifequest.dagger.DaggerAppComponent;

public class Injector {

    private static AppModule module;
    private static AppComponent injector;

    protected static void createInjector(Context context) {
        module = new AppModule(context);
        injector = DaggerAppComponent.builder().appModule(module).build();
    }

    protected static void createInjector(AppModule module) {
        injector = DaggerAppComponent.builder().appModule(module).build();
    }

    public static AppComponent getInjector() {
        return injector;
    }
}
