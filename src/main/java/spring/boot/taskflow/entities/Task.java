package spring.boot.taskflow.entities;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import spring.boot.taskflow.enums.PriorityEnum;
import spring.boot.taskflow.enums.StatusEnum;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 255, nullable = false)
    private String title;

    @Column(length = 255)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusEnum status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PriorityEnum priority;

    @Column(nullable = false)
    private LocalDate deadline;

    @Column(nullable = false)
    private LocalDate createdAt;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}
