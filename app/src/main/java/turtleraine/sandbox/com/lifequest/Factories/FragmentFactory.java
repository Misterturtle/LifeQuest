package turtleraine.sandbox.com.lifequest.Factories;

import java.lang.reflect.Constructor;
import java.util.Collections;

public class FragmentFactory {

    public <T> T create(Class<T> fragmentClass) {
        try {
            return fragmentClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }
}
