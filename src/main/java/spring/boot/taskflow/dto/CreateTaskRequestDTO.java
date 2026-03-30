package spring.boot.taskflow.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import spring.boot.taskflow.entities.Task;
import spring.boot.taskflow.enums.PriorityEnum;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateTaskRequestDTO {

    private String title;

    private String description;

    private PriorityEnum priority;

    private LocalDate deadline;

    public Task toEntity() {
        Task task = new Task();

        task.setTitle(this.title);
        task.setDescription(this.description);
        task.setPriority(this.priority);
        task.setDeadline(this.deadline);

        return task;
    }

}
