package turtleraine.sandbox.com.lifequest.components.MainMenu.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import turtleraine.sandbox.com.lifequest.Application.Injector;

public class TaskViewFragmentQtn extends Fragment {


    TaskViewFragmentImpl impl = new TaskViewFragmentImpl();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Injector.getInjector().inject(this);
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return impl.onCreateView(inflater, container, savedInstanceState, this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        impl.onViewCreated(view, savedInstanceState, this);
    }
}
