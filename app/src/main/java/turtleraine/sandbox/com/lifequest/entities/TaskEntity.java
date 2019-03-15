package turtleraine.sandbox.com.lifequest.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class TaskEntity {

    public TaskEntity(String title) {
        this.title = title;
    }

    String title;
}
