package io.todo.model;

import io.todo.enums.Difficult;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "tasks")
@AllArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String title;
    @Enumerated(EnumType.STRING)
    private Difficult difficult;
    private Boolean isComplete = false;

    public void cloneTask(Task task) {
        this.id =  task.getId();
        this.title = task.getTitle();
        this.difficult = task.getDifficult();
        this.isComplete = task.getIsComplete();
    }
}
