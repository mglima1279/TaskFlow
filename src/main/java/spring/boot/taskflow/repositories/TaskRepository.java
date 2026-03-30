package spring.boot.taskflow.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import spring.boot.taskflow.entities.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
}
