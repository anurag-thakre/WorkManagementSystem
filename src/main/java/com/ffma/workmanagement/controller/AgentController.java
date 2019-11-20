package com.ffma.workmanagement.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ffma.workmanagement.model.AgentTask;
import com.ffma.workmanagement.service.AgentService;

@RestController
@RequestMapping("/agents")
public class AgentController {

	@Autowired
	private AgentService agentService;

	@GetMapping
	public Set<AgentTask> getAllAgents() {
		return agentService.getAllAgents();
	}

}
