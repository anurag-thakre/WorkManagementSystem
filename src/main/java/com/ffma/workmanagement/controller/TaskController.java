package com.ffma.workmanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ffma.workmanagement.entiry.TaskAssignments;
import com.ffma.workmanagement.service.TaskAssignmentService;

import javassist.NotFoundException;

@RestController
@RequestMapping("/tasks")
public class TaskController {

	@Autowired
	TaskAssignmentService taskAssignmentService;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public TaskAssignments createTask(@RequestBody TaskAssignments taskAssignments) throws NotFoundException {
		return taskAssignmentService.saveTaskAssignment(taskAssignments);
	}

	@PutMapping("/{taskId}/status/{status}")
	public Integer updateTask(@PathVariable Integer taskId, @PathVariable String status) {
		return taskAssignmentService.updateTaskStatus(taskId, status);

	}

}
