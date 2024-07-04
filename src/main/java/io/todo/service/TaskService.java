package io.todo.service;

import io.todo.enums.Difficult;
import io.todo.model.Task;
import io.todo.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class TaskService {
  private final TaskRepository taskRepository;
  private final static List<Task> tasks = new ArrayList<>();

  public List<Task> findAll(long page) {
    if (tasks.isEmpty() || tasks.size() != taskRepository.findAll()
        .size()) {
      tasks.clear();
      tasks.addAll(taskRepository.findAll());
      Collections.reverse(tasks);
    }
    return findPage(page);
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

  public List<Task> findPage(Long page, String type) {
    List<Task> taskPage = new ArrayList<>();
    tasks.stream()
        .filter(task -> task.getDifficult()
            .equals(type))
        .skip(page * 5L)
        .forEach(task -> {
          if (taskPage.size() < 5)
            taskPage.add(task);
        });
    return taskPage;
  }

  private List<Task> findPage(Long page) {
    List<Task> taskPage = new ArrayList<>();
    tasks.stream()
        .skip(page * 5L)
        .forEach(task -> {
          if (taskPage.size() < 5)
            taskPage.add(task);
        });
    return taskPage;
  }
}
