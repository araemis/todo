package io.todo.controller;

import io.todo.enums.Difficult;
import io.todo.model.Task;
import io.todo.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @RequestMapping("/")
    public String home(Model model) {
        List<Task> tasks = taskService.findAll("", "");
        model.addAttribute("tasks", tasks);
        return "index";
    }

    @GetMapping("/filter")
    public String filterDifficult(@RequestParam("type-difficult") String difficult, Model model, @RequestParam("type-checked") String checked) {
        List<Task> tasks = taskService.filter(difficult, checked);
        model.addAttribute("tasks", tasks);
        return "fragments/tasks :: tasks";
    }

    @PostMapping(path = "/addTask")
    public String addTask(@RequestParam String title, @RequestParam("difficult") Difficult difficult) {
        Task newTask = new Task();
        newTask.setTitle(title);
        newTask.setDifficult(difficult);
        taskService.save(newTask);
        return "redirect:/";
    }

    @PutMapping("/updateTask/{id}")
    public String checkTask(Model model, @PathVariable Long id, @RequestParam("type-difficult") String difficult, @RequestParam("type-checked") String checked) {
        taskService.updateById(id);
        List<Task> tasks = taskService.findAll(difficult, checked);
        model.addAttribute("tasks", tasks);
        return "fragments/tasks :: tasks";
    }

    @DeleteMapping("/deleteAll")
    public String deleteAll() {
        taskService.deleteAll();
        return "redirect:/";
    }

    @DeleteMapping("/deleteTask/{id}")
    public String deleteTask(Model model, @PathVariable Long id, @RequestParam("type-difficult") String difficult, @RequestParam("type-checked") String checked) {
        taskService.deleteById(id);
        List<Task> tasks = taskService.findAll(difficult, checked);
        model.addAttribute("tasks", tasks);
        return "fragments/tasks :: tasks";
    }
}
