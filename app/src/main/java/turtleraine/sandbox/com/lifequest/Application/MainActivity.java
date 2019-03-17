package turtleraine.sandbox.com.lifequest.Application;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity {

    MainActivityImpl impl = new MainActivityImpl();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Injector.createInjector(this);
        Injector.getInjector().inject(this);
        super.onCreate(savedInstanceState);
        impl.onCreate(savedInstanceState, this);
    }
}
