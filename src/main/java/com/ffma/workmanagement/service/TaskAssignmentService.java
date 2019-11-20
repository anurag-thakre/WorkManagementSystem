package com.ffma.workmanagement.service;

import java.security.InvalidParameterException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.ffma.workmanagement.entiry.Agent;
import com.ffma.workmanagement.entiry.TaskAssignments;
import com.ffma.workmanagement.entiry.TaskStatus;
import com.ffma.workmanagement.repository.AgentRepository;
import com.ffma.workmanagement.repository.SkillRepository;
import com.ffma.workmanagement.repository.TaskAssignmentRepository;

import javassist.NotFoundException;

@Service
public class TaskAssignmentService {

	private static final Logger LOGGER = LoggerFactory.getLogger(TaskAssignmentService.class);

	@Autowired
	TaskAssignmentRepository taskAssignmentRepository;

	@Autowired
	AgentRepository agentRepository;

	@Autowired
	SkillRepository skillRepository;

	public TaskAssignments saveTaskAssignment(TaskAssignments taskAssignments) throws NotFoundException {
		LOGGER.info("saveTaskAssignment for skills {}", taskAssignments);
		Collection<String> names = Arrays.asList(taskAssignments.getSkillNames());

		if (CollectionUtils.isEmpty(names)) {
			throw new InvalidParameterException("Provided skills are invalid");
		}
		Integer[] skillIds = skillRepository.getSkillIdsBySkillNames(names);
		List<Agent> agentsHavingSkills = agentRepository.getAgentBySkillIds(skillIds);

		if (CollectionUtils.isEmpty(agentsHavingSkills)) {
			throw new NotFoundException("Agent not found with required skills!");
		}
		Integer agentId = null;
		List<Integer> agentIds = agentsHavingSkills.stream().map(Agent::getId).collect(Collectors.toList());
		LinkedList<TaskAssignments> tasksByAgents = taskAssignmentRepository.getTasksBySkillIdAndAgent(skillIds,
				agentIds);
		List<Integer> assignedAgentIds = tasksByAgents.stream().map(TaskAssignments::getAgentId)
				.collect(Collectors.toList());
		agentIds.removeAll(assignedAgentIds);

		if (!CollectionUtils.isEmpty(agentIds)) {
			agentId = agentIds.stream().findAny().get();
		}

		if (agentId == null) {

			Optional<Agent> foundAgent = agentsHavingSkills.stream()
					.filter(obj -> checkIfAgentIsAvaillable(tasksByAgents, taskAssignments.getPriority(), obj.getId()))
					.findAny();

			if (!foundAgent.isPresent()) {
				throw new NotFoundException("Agent not found with required skills!");
			}
			agentId = foundAgent.get().getId();
		}
		taskAssignments.setAgentId(agentId);
		taskAssignments.setStartDate(LocalDateTime.now());
		taskAssignments.setSkillIds(skillIds);
		return taskAssignmentRepository.save(taskAssignments);

	}

	private boolean checkIfAgentIsAvaillable(List<TaskAssignments> agentTasks, String taskPriority, Integer agentId) {
		boolean isAgentAvailableToPick = false;
		for (TaskAssignments taskAssignment : agentTasks) {
			if (taskAssignment.getAgentId().equals(agentId)) {
				if (StringUtils.equalsIgnoreCase("low", taskAssignment.getPriority())
						&& StringUtils.equalsIgnoreCase("high", taskPriority)) {
					LOGGER.info("Fould agent working on low priprity task {}", agentId);
					return true;
				} else if (taskPriority.equalsIgnoreCase(taskAssignment.getPriority())) {
					LOGGER.info("Agent working on high priority task {}", agentId);
					return false;
				}
			}
		}
		return isAgentAvailableToPick;
	}

	public Integer updateTaskStatus(Integer taskId, String taskStatus) {
		LOGGER.info("updating the task status for taskId{}", taskId);
		TaskStatus status = TaskStatus.fromValue(taskStatus);
		LocalDateTime completionDate = LocalDateTime.now();
		return taskAssignmentRepository.updateTaskStatus(taskId, status.value(), completionDate);
	}

	public List<TaskAssignments> getAllTasks() {
		return taskAssignmentRepository.getAllTasks();
	}

}
