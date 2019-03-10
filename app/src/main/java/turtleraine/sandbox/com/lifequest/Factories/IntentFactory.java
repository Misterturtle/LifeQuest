package turtleraine.sandbox.com.lifequest.Factories;

import android.content.Context;
import android.content.Intent;

import turtleraine.sandbox.com.lifequest.Application.MainActivity;
import turtleraine.sandbox.com.lifequest.Application.MainActivityImpl;

/**
 * Created by rconaway on 1/9/19.
 */

public class IntentFactory {

    public <T>Intent makeIntent(Context context, Class<T> classToStart){
        return new Intent(context, classToStart);
    }
}
