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
import spring.boot.taskflow.exceptions.TaskNotFoundException;
import spring.boot.taskflow.repositories.TaskRepository;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    public Task createTask(CreateTaskRequestDTO request, User user) {
        Task task = request.toEntity();

        task.setStatus(StatusEnum.PENDING);
        task.setCreatedAt(LocalDate.now());
        task.setUser(user);

        return taskRepository.save(task);
    }

    public Task readTask(long id, User user) {
        Optional<Task> existingTask = taskRepository.findById(id);

        if (existingTask.isEmpty()) {
            throw new TaskNotFoundException();
        }

        Task task = existingTask.get();

        if (task.getUser().getId() == user.getId()) {
            return task;
        }

        return null;
    }

    public List<Task> readAllTasks(User user) {
        return taskRepository.findByUser(user);
    }

    public Task updateTask(long id, CreateTaskRequestDTO request, User user) {
        Optional<Task> existingTask = taskRepository.findById(id);

        if (existingTask.isEmpty()) {
            throw new TaskNotFoundException();
        }

        Task task = existingTask.get();

        if (task.getUser().getId() != user.getId()) {
            return null;
        }

        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setPriority(request.getPriority());
        task.setDeadline(request.getDeadline());

        return taskRepository.save(task);
    }

    public Task updateTaskStatus(long id, StatusEnum status, User user) {
        Optional<Task> existingTask = taskRepository.findById(id);

        if (existingTask.isEmpty()) {
            throw new TaskNotFoundException();
        }

        Task task = existingTask.get();

        if (task.getUser().getId() != user.getId()) {
            return null;
        }

        task.setStatus(status);

        return taskRepository.save(task);
    }

    public void deleteTask(long id, User user) {
        Optional<Task> existingTask = taskRepository.findById(id);

        if (existingTask.isEmpty()) {
            throw new TaskNotFoundException();
        }

        Task task = existingTask.get();

        if (task.getUser().getId() == user.getId()) {
            taskRepository.deleteById(id);
        }
    }
}
