package turtleraine.sandbox.com.lifequest.state_store;

import java.util.function.Consumer;

import lombok.Builder;


@Builder
public class Subscription {
    public final Consumer<AppState> updateFunction;
    public AppState lastAppState;
}
