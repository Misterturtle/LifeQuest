package turtleraine.sandbox.com.lifequest.components.MainMenu.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import javax.inject.Inject;

import turtleraine.sandbox.com.lifequest.Application.Injector;
import turtleraine.sandbox.com.lifequest.R;
import turtleraine.sandbox.com.lifequest.entities.TaskEntity;
import turtleraine.sandbox.com.lifequest.entities.UserEntity;
import turtleraine.sandbox.com.lifequest.services.TaskService;

public class CreateTaskFragmentImpl {

    @Inject
    public TaskService taskService;

    private Button submitButton;
    private TextView taskNameField;

    public CreateTaskFragmentImpl() {
        Injector.getInjector().inject(this);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState, CreateTaskFragmentQtn createTaskFragmentQtn) {
        return inflater.inflate(R.layout.create_task_fragment_layout, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        submitButton = view.findViewById(R.id.create_task_button);
        taskNameField = view.findViewById(R.id.taskNameInput);
        submitButton.setOnClickListener(buttonView -> {
            String taskName = taskNameField.getText().toString();
            TaskEntity task = new TaskEntity(taskName);
            taskService.addNewTask(task);
        });
    }
}
