package com.ffma.workmanagement.service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ffma.workmanagement.entiry.Agent;
import com.ffma.workmanagement.entiry.TaskAssignments;
import com.ffma.workmanagement.model.AgentTask;
import com.ffma.workmanagement.repository.AgentRepository;
import com.ffma.workmanagement.repository.TaskAssignmentRepository;

@Service
public class AgentService {

	@Autowired
	private AgentRepository agentRepository;

	@Autowired
	TaskAssignmentRepository taskAssignmentRepository;

	public Set<AgentTask> getAllAgents() {
		Set<Agent> agents = agentRepository.getAllAgents();
		Set<AgentTask> agentTasks = new HashSet<>();

		agents.stream().forEach(agent -> {

			Set<TaskAssignments> taskAssignments = taskAssignmentRepository.getTaskIdsByAgentId(agent.getId());
			
			if(taskAssignments != null) {
				Set<Integer> taskIds = taskAssignments.stream().map(TaskAssignments::getTaskId)
						.collect(Collectors.toSet());
				agentTasks.add(populateAgentTask(agent, taskIds));
			} else {
				agentTasks.add(populateAgentTask(agent, null));
			}
		});

		return agentTasks;
	}

	private AgentTask populateAgentTask(Agent agent, Set<Integer> taskIds) {
		AgentTask agentTask = new AgentTask();
		agentTask.setId(agent.getId());
		agentTask.setName(agent.getName());
		agentTask.setCreatedDate(agent.getCreatedDate());
		agentTask.setSkillIds(agent.getSkillIds());
		agentTask.setTaskids(taskIds);
		return agentTask;
	}

}
