package turtleraine.sandbox.com.lifequest.state_store;

import java.util.function.Consumer;
import java.util.function.Function;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import turtleraine.sandbox.com.lifequest.entities.TaskEntity;


@EqualsAndHashCode
@Builder
public class CreateTaskViewModel implements AppStateViewModel<CreateTaskViewModel> {

    public String title;
    public String description;

    private CreateTaskViewModel(String title, String description) {
        this.title = title;
        this.description = description;
    }

    @Override
    public CreateTaskViewModel copy() {
        return CreateTaskViewModel.builder()
                .description(description)
                .title(title)
                .build();
    }
}
