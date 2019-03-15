package turtleraine.sandbox.com.lifequest.entities;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Builder
public class TaskEntity {

    public TaskEntity(String title, String description) {
        this.title = title;
        this.description = description;
    }

    String title;
    String description;
}
