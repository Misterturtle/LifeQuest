package turtleraine.sandbox.com.lifequest.state_store;

import lombok.Builder;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
@Builder
public class AppState implements AppStateViewModel<AppState> {

    @Builder.Default
    public CreateTaskViewModel createTaskViewModel = CreateTaskViewModel.builder().build();

    @Override
    public AppState copy() {
        return AppState.builder()
                .createTaskViewModel(createTaskViewModel.copy())
                .build();
    }
}
