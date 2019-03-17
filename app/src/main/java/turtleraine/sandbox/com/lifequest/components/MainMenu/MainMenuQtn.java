package turtleraine.sandbox.com.lifequest.components.MainMenu;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import javax.inject.Inject;

import turtleraine.sandbox.com.lifequest.Application.Injector;

/**
 * Created by rconaway on 2/18/19.
 */

public class MainMenuQtn extends AppCompatActivity {

    MainMenuImpl impl = new MainMenuImpl();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Injector.getInjector().inject(this);
        super.onCreate(savedInstanceState);

        impl.onCreate(savedInstanceState, this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        impl.onResume(this);
    }
}
