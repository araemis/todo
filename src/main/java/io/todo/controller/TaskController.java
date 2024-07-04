package io.todo.controller;

import io.todo.enums.Difficult;
import io.todo.model.Task;
import io.todo.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;
    private static int page;

    @RequestMapping("/")
    public String home(Model model) {
        page = 0;
        List<Task> tasks = taskService.findAll(page);
        model.addAttribute("tasks", tasks);
        model.addAttribute("page", (page + 1));
        return "index";
    }

    @GetMapping("/nextPage")
    public String nextPage(Model model) {
        page++;
        List<Task> tasks = taskService.findAll(page);
        if (!tasks.isEmpty()) {
            model.addAttribute("tasks", tasks);
            model.addAttribute("page", (page + 1));
            return "index";
        }
        page--;
        return "redirect:/actualPage";
    }

    @GetMapping("/filter/{type-difficult}")
    public String filterDifficult(@PathVariable String type, Model model) {
        page = 0;
        List<Task> tasks = taskService.findPage((long) page, type);
        model.addAttribute("tasks", tasks);
        model.addAttribute("page", (page + 1));
        return "index";
    }

    @RequestMapping("/actualPage")
    public String actualPage(Model model) {
        List<Task> tasks = taskService.findAll(page);
        model.addAttribute("tasks", tasks);
        model.addAttribute("page", (page + 1));
        return "index";
    }

    @GetMapping("/previousPage")
    public String previousPage(Model model) {
        page = page > 0 ? page - 1 : 0;
        List<Task> tasks = taskService.findAll(page);
        if (!tasks.isEmpty()) {
            model.addAttribute("tasks", tasks);
            model.addAttribute("page", (page + 1));
            return "index";
        }
        page++;
        return "redirect:/actualPage";
    }

    @PostMapping("/addTask")
    public String addTask(@RequestParam String title, @RequestParam Difficult difficult) {
        Task newTask = new Task();
        newTask.setTitle(title);
        newTask.setDifficult(difficult);
        taskService.save(newTask);
        return "redirect:/actualPage";
    }

    @PutMapping("/updateTask/{id}")
    public String checkTask(@PathVariable Long id) {
        taskService.updateById(id);
        return "redirect:/actualPage";
    }

    @DeleteMapping("/deleteTask/{id}")
    public String deleteTask(@PathVariable Long id) {
        taskService.deleteById(id);
        return "redirect:/actualPage";
    }
}
