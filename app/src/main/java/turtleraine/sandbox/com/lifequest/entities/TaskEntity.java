package turtleraine.sandbox.com.lifequest.entities;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode
@NoArgsConstructor
@Builder
public class TaskEntity {

    public TaskEntity(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public String title;
    public String description;
}
