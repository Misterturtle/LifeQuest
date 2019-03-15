package turtleraine.sandbox.com.lifequest.components.common;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;

import turtleraine.sandbox.com.lifequest.entities.TaskEntity;

public class TaskCardViewQtn extends CardView {

    TaskCardViewImpl impl = new TaskCardViewImpl();

    public TaskCardViewQtn(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void populate(TaskEntity task){
        impl.populate(task, this);
    }

}
