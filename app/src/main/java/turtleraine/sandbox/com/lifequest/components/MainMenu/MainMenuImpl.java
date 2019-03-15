package turtleraine.sandbox.com.lifequest.components.MainMenu;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import javax.inject.Inject;

import turtleraine.sandbox.com.lifequest.Application.Injector;
import turtleraine.sandbox.com.lifequest.Factories.FragmentFactory;
import turtleraine.sandbox.com.lifequest.R;
import turtleraine.sandbox.com.lifequest.components.MainMenu.fragments.CreateTaskFragmentQtn;
import turtleraine.sandbox.com.lifequest.components.MainMenu.fragments.NoTasksFragmentQtn;
import turtleraine.sandbox.com.lifequest.components.MainMenu.fragments.TaskViewFragmentQtn;
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
        BottomNavigationView bottomNavBar = qtn.findViewById(R.id.main_menu_bottom_nav_bar);
        bottomNavBar.setOnNavigationItemSelectedListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.navigation_create_task:
                    CreateTaskFragmentQtn createTaskFragmentQtn = fragmentFactory.create(CreateTaskFragmentQtn.class);

                    FragmentTransaction transaction = qtn.getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.main_menu_fragment_container, createTaskFragmentQtn);
                    transaction.commit();
                    return true;

                default:
                    return false;
            }
        });
    }

    public void onResume(MainMenuQtn qtn) {
        taskService.getTasks()
                .whenComplete((tasks, error) -> {
                    if (tasks != null) {
                        if (tasks.isEmpty()) {
                            NoTasksFragmentQtn noTasksFragment = fragmentFactory.create(NoTasksFragmentQtn.class);

                            FragmentTransaction transaction = qtn.getSupportFragmentManager().beginTransaction();
                            transaction.replace(R.id.main_menu_fragment_container, noTasksFragment);
                            transaction.commit();
                        } else {
                            TaskViewFragmentQtn taskViewFragment = fragmentFactory.create(TaskViewFragmentQtn.class);

                            FragmentTransaction transaction = qtn.getSupportFragmentManager().beginTransaction();
                            transaction.replace(R.id.main_menu_fragment_container, taskViewFragment);
                            transaction.commit();
                        }
                    }
                });
    }
}
