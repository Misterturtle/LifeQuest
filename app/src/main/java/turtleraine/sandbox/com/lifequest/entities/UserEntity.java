package turtleraine.sandbox.com.lifequest.entities;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class UserEntity {

    public String name;

    public List<TaskEntity> taskList;

    public UserEntity(String name, List<TaskEntity> taskList) {
        this.name = name;
        this.taskList = taskList;
    }
}
