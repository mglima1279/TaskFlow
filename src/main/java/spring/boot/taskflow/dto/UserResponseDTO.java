package spring.boot.taskflow.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import spring.boot.taskflow.entities.User;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDTO {

    private long id;

    private String name;

    private String email;

    private List<TaskResponseDTO> tasks;

    public static UserResponseDTO fromEntity(User user) {
        UserResponseDTO response = new UserResponseDTO();
        response.setId(user.getId());
        response.setName(user.getName());
        response.setEmail(user.getEmail());
        response.setTasks(user.getTasks().stream()
                .map(TaskResponseDTO::fromEntity)
                .toList());

        return response;
    }

}
