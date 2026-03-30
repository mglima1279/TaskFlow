package spring.boot.taskflow.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import spring.boot.taskflow.dto.CreateTaskRequestDTO;
import spring.boot.taskflow.entities.Task;
import spring.boot.taskflow.entities.User;
import spring.boot.taskflow.enums.StatusEnum;
import spring.boot.taskflow.repositories.TaskRepository;
import spring.boot.taskflow.security.CustomUserDetails;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final JwtService jwtService;
    private final TaskRepository taskRepository;

    public Task createTask(CreateTaskRequestDTO request, User user, String token) {
        if (!jwtService.isTokenValid(token, new CustomUserDetails(user))) {
            return null;
        }

        Task task = request.toEntity();

        task.setStatus(StatusEnum.PENDING);
        task.setCreatedAt(LocalDate.now());
        task.setUser(user);

        return taskRepository.save(task);
    }

    public Task readTask(long id, User user, String token) {
        if (!jwtService.isTokenValid(token, new CustomUserDetails(user))) {
            return null;
        }

        Optional<Task> existingTask = taskRepository.findById(id);

        if (existingTask.isPresent()) {
            return existingTask.get();
        }

        return null;
    }

    public List<Task> readAllTasks(User user, String token) {
        if (!jwtService.isTokenValid(token, new CustomUserDetails(user))) {
            return null;
        }

        return taskRepository.findAll();
    }

    public Task updateTask(long id, CreateTaskRequestDTO request, User user, String token) {
        if (!jwtService.isTokenValid(token, new CustomUserDetails(user))) {
            return null;
        }

        Optional<Task> existingTask = taskRepository.findById(id);

        if (existingTask.isEmpty()) {
            return null;
        }

        Task task = existingTask.get();

        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setPriority(request.getPriority());
        task.setDeadline(request.getDeadline());

        return taskRepository.save(task);
    }

    public Task updateTaskStatus(long id, StatusEnum status, User user, String token) {
        if (!jwtService.isTokenValid(token, new CustomUserDetails(user))) {
            return null;
        }

        Optional<Task> existingTask = taskRepository.findById(id);

        if (existingTask.isEmpty()) {
            return null;
        }

        Task task = existingTask.get();

        task.setStatus(status);

        return taskRepository.save(task);
    }

    public void deleteTask(long id, User user, String token) {
        if (jwtService.isTokenValid(token, new CustomUserDetails(user))) {
            Optional<Task> existingTask = taskRepository.findById(id);

            if (existingTask.isPresent()) {
                taskRepository.deleteById(id);
            }
        }
    }
}
