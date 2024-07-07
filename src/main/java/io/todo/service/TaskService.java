package io.todo.service;

import io.todo.enums.Difficult;
import io.todo.model.Task;
import io.todo.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final static List<Task> tasks = new ArrayList<>();

    public List<Task> findAll(String difficult, String checked) {
        if (tasks.isEmpty() || tasks.size() != taskRepository.findAll()
                                                             .size()) {
            tasks.clear();
            tasks.addAll(taskRepository.findAll());
        }
        return filter(difficult, checked);
    }

    public void updateById(Long id) {
        Task task = new Task();
        for (int i = 0; i < tasks.size(); i++) {
            if (Objects.equals(id, tasks.get(i)
                                        .getId())) {
                task.cloneTask(tasks.get(i));
                task.setIsComplete(!task.getIsComplete());
                tasks.set(i, task);
            }
        }
        taskRepository.save(task);
    }

    public void save(Task task) {
        tasks.add(task);
        taskRepository.save(task);
    }

    public void deleteById(Long id) {
        tasks.stream()
             .filter(t -> Objects.equals(t.getId(), id))
             .findFirst()
             .ifPresent(tasks::remove);
        taskRepository.deleteById(id);
    }

    public List<Task> filter(String difficult, String checked) {
        Predicate<Task> getChecked = task -> {
            if (checked.equals("CHECKED") && task.getIsComplete()) {
                return true;
            } else if (checked.equals("UNCHECKED") && !task.getIsComplete()) {
                return true;
            } else return (!checked.equals("CHECKED") && !checked.equals("UNCHECKED"));
        };


        if (!Difficult.allDifficult(difficult)) {
            return tasks.stream()
                        .sorted((t1, t2) -> t2.getId()
                                              .compareTo((t1.getId())))
                        .filter(getChecked)
                        .toList();
        }

        return tasks.stream()
                    .filter(task -> task.getDifficult()
                                        .toString()
                                        .equals(difficult))
                    .sorted((t1, t2) -> t2.getId()
                                          .compareTo(t1.getId()))
                    .filter(getChecked)
                    .toList();
    }

    public void deleteAll() {
        taskRepository.deleteAll();
        tasks.clear();
    }
}
