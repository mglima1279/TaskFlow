package spring.boot.taskflow.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import spring.boot.taskflow.entities.User;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserRequestDTO {

    private String name;

    private String email;

    private String password;

    public User toEntity() {
        User user = new User();
        user.setName(this.name);
        user.setEmail(this.email);

        return user;
    }

}
