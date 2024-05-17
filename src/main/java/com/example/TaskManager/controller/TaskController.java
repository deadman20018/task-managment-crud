package com.example.TaskManager.controller;

import com.example.TaskManager.model.Task;
import com.example.TaskManager.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tasks")
public class TaskController {

	private final TaskService taskService;

	@Autowired
	public TaskController(TaskService taskService) {
		this.taskService = taskService;
	}

	@PostMapping("/create")
	public ResponseEntity<Task> createTask(@RequestBody Task task) {
		Task createdTask = taskService.createTask(task);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);
	}

	@GetMapping("/all")
	public ResponseEntity<List<Task>> getAllTasks() {
		List<Task> tasks = taskService.getAllTasks();
		return ResponseEntity.ok().body(tasks);
	}

	@GetMapping("/find/{id}")
	public ResponseEntity<Task> getTaskById(@PathVariable("id") long taskId) {
		Optional<Task> task = taskService.getTaskById(taskId);
		return task.map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<Task> updateTask(@PathVariable("id") long taskId, @RequestBody Task task) {
		task.setId(taskId);
		Task updatedTask = taskService.updateTask(task);
		return ResponseEntity.ok(updatedTask);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Void> deleteTaskById(@PathVariable("id") long taskId) {
		taskService.deleteTaskById(taskId);
		return ResponseEntity.noContent().build();
	}
}