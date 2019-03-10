package turtleraine.sandbox.com.lifequest.components.MainMenu;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import javax.inject.Inject;

import turtleraine.sandbox.com.lifequest.Application.Injector;
import turtleraine.sandbox.com.lifequest.Factories.FragmentFactory;
import turtleraine.sandbox.com.lifequest.R;
import turtleraine.sandbox.com.lifequest.components.MainMenu.fragments.NoTasksFragmentQtn;
import turtleraine.sandbox.com.lifequest.services.TaskService;

/**
 * Created by rconaway on 2/18/19.
 */

public class MainMenuImpl {

    @Inject
    FragmentFactory fragmentFactory;

    @Inject
    TaskService taskService;

    public void onCreate(Bundle bundle, MainMenuQtn qtn) {
        Injector.getInjector().inject(this);

        qtn.setContentView(R.layout.main_menu_layout);
    }

    public void onResume(MainMenuQtn qtn) {
        if (taskService.getTasks().isEmpty()) {
            NoTasksFragmentQtn noTasksFragment = fragmentFactory.create(NoTasksFragmentQtn.class);

            FragmentTransaction transaction = qtn.getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.mainMenuFragmentContainer, noTasksFragment);
            transaction.commit();
        }
    }
}
