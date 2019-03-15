package turtleraine.sandbox.com.lifequest.components.MainMenu.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import javax.inject.Inject;

import turtleraine.sandbox.com.lifequest.Application.Injector;
import turtleraine.sandbox.com.lifequest.R;
import turtleraine.sandbox.com.lifequest.components.common.TaskCardViewQtn;
import turtleraine.sandbox.com.lifequest.entities.TaskEntity;
import turtleraine.sandbox.com.lifequest.services.TaskService;

public class TaskViewFragmentImpl {

    @Inject
    TaskService taskService;

    public TaskViewFragmentImpl() {
        Injector.getInjector().inject(this);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState, TaskViewFragmentQtn noTasksFragmentQtn) {
        return inflater.inflate(R.layout.task_view_fragment_layout, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState, TaskViewFragmentQtn taskViewFragmentQtn) {
        LinearLayout taskContainer = view.findViewById(R.id.task_view_container_layout);
        LayoutInflater inflater = taskViewFragmentQtn.getLayoutInflater();

        taskService.getTasks()
                .whenComplete((tasks, error) -> {
                    tasks.stream()
                            .forEach(taskEntity -> {
                                TaskCardViewQtn taskCard = (TaskCardViewQtn) inflater.inflate(R.layout.task_card_view_layout, taskContainer, false);
                                taskCard.populate(taskEntity);

                                taskContainer.addView(taskCard);
                            });
                });
    }
}
