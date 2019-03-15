package turtleraine.sandbox.com.lifequest.components.common;

import android.widget.TextView;

import turtleraine.sandbox.com.lifequest.R;
import turtleraine.sandbox.com.lifequest.entities.TaskEntity;

public class TaskCardViewImpl {
    public void populate(TaskEntity task, TaskCardViewQtn qtn) {
        TextView taskNameTextView = qtn.findViewById(R.id.task_name_text_view);
        taskNameTextView.setText(task.getTitle());
    }
}
