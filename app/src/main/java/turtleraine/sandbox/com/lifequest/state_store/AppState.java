package turtleraine.sandbox.com.lifequest.state_store;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import turtleraine.sandbox.com.lifequest.entities.TaskEntity;

@EqualsAndHashCode
@Builder
public class AppState implements AppStateViewModel<AppState> {

    public TaskEntity taskBeingCreated;

    @Override
    public AppState copy() {
        return AppState.builder().taskBeingCreated;
    }
}
