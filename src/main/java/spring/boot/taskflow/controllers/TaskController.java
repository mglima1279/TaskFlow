package spring.boot.taskflow.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import spring.boot.taskflow.dto.CreateTaskRequestDTO;
import spring.boot.taskflow.dto.TaskResponseDTO;
import spring.boot.taskflow.entities.Task;
import spring.boot.taskflow.entities.User;
import spring.boot.taskflow.enums.StatusEnum;
import spring.boot.taskflow.services.TaskService;
import spring.boot.taskflow.services.UserService;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final UserService userService;
    private final TaskService taskService;

    @PostMapping
    public ResponseEntity<TaskResponseDTO> createTask(@RequestBody CreateTaskRequestDTO request,
            @AuthenticationPrincipal UserDetails userDetails) {

        User user = userService.getUserByEmail(userDetails.getUsername());

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        Task task = taskService.createTask(request, user);

        if (task == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(TaskResponseDTO.fromEntity(task));
    }

    @GetMapping
    public ResponseEntity<List<TaskResponseDTO>> getAllTasks(
            @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.getUserByEmail(userDetails.getUsername());

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(taskService.readAllTasks(user).stream().map(TaskResponseDTO::fromEntity).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponseDTO> getTask(@PathVariable long id,
            @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.getUserByEmail(userDetails.getUsername());

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        Task task = taskService.readTask(id, user);

        if (task == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(TaskResponseDTO.fromEntity(task));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable long id,
            @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.getUserByEmail(userDetails.getUsername());

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        taskService.deleteTask(id, user);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskResponseDTO> updateTask(@PathVariable long id, @RequestBody CreateTaskRequestDTO request,
            @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.getUserByEmail(userDetails.getUsername());

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        Task task = taskService.updateTask(id, request, user);

        if (task == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(TaskResponseDTO.fromEntity(task));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<TaskResponseDTO> updateTaskStatus(@PathVariable long id, @RequestBody StatusEnum request,
            @AuthenticationPrincipal UserDetails userDetails) {

        User user = userService.getUserByEmail(userDetails.getUsername());

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        Task task = taskService.updateTaskStatus(id, request, user);

        if (task == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(TaskResponseDTO.fromEntity(task));
    }
}
