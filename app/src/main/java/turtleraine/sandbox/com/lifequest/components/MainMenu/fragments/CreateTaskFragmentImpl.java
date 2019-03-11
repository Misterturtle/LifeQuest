package turtleraine.sandbox.com.lifequest.components.MainMenu.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import turtleraine.sandbox.com.lifequest.R;

public class CreateTaskFragmentImpl {
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState, CreateTaskFragmentQtn createTaskFragmentQtn) {
        return inflater.inflate(R.layout.create_task_fragment_layout, container, false);
    }
}
