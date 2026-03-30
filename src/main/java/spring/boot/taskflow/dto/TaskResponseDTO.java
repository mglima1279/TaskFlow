package spring.boot.taskflow.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import spring.boot.taskflow.entities.Task;
import spring.boot.taskflow.enums.PriorityEnum;
import spring.boot.taskflow.enums.StatusEnum;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TaskResponseDTO {

    private long id;

    private String title;

    private String description;

    private StatusEnum status;

    private PriorityEnum priority;

    private LocalDate deadline;

    private LocalDate createdAt;

    public static TaskResponseDTO fromEntity(Task task) {
        TaskResponseDTO response = new TaskResponseDTO();

        response.setId(task.getId());
        response.setTitle(task.getTitle());
        response.setDescription(task.getDescription());
        response.setStatus(task.getStatus());
        response.setPriority(task.getPriority());
        response.setDeadline(task.getDeadline());
        response.setCreatedAt(task.getCreatedAt());

        return response;
    }
}
