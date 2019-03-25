package turtleraine.sandbox.com.lifequest.state_store;

import java.util.function.Consumer;
import java.util.function.Function;

public interface AppStateViewModel<T> {
    T copy();
}
