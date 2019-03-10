package turtleraine.sandbox.com.lifequest.Application;

import android.content.Intent;
import android.os.Bundle;

import javax.inject.Inject;

import turtleraine.sandbox.com.lifequest.Factories.IntentFactory;
import turtleraine.sandbox.com.lifequest.components.MainMenu.MainMenuQtn;


public class MainActivityImpl {

    @Inject
    public IntentFactory intentFactory;

    void onCreate(Bundle bundle, MainActivity qtn){
        Injector.getInjector().inject(this);
        Intent mainMenuIntent = intentFactory.makeIntent(qtn, MainMenuQtn.class);

        qtn.startActivity(mainMenuIntent);
    }
}
