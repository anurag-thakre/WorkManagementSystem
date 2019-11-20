package com.ffma.workmanagement.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.security.InvalidParameterException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.ffma.workmanagement.entiry.Agent;
import com.ffma.workmanagement.entiry.TaskAssignments;
import com.ffma.workmanagement.repository.AgentRepository;
import com.ffma.workmanagement.repository.SkillRepository;
import com.ffma.workmanagement.repository.TaskAssignmentRepository;

import javassist.NotFoundException;

public class TaskAssignmentServiceTest {

	@Mock
	TaskAssignmentRepository taskAssignmentRepository;

	@Mock
	AgentRepository agentRepository;

	@Mock
	SkillRepository skillRepository;

	@InjectMocks
	TaskAssignmentService service;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testSaveTaskAssignmentWithAvailableAgentsWithTasks() throws NotFoundException {
		Integer[] skillIds = { 1, 2, 3 };
		TaskAssignments taskAssignments = mockTaskAssignments();
		Collection<String> names = Arrays.asList(taskAssignments.getSkillNames());
		when(skillRepository.getSkillIdsBySkillNames(names)).thenReturn(skillIds);
		List<Agent> agents = mockAgents();
		when(agentRepository.getAgentBySkillIds(skillIds)).thenReturn(agents);
		Collection<Integer> agentIds = new ArrayList<>(1);
		when(taskAssignmentRepository.getTasksBySkillIdAndAgent(skillIds, agentIds))
				.thenReturn(getMockTaskAssignmentWithAgents());
		when(taskAssignmentRepository.save(Mockito.any())).thenReturn(taskAssignments);
		TaskAssignments returnedAssignments = service.saveTaskAssignment(taskAssignments);
		assertTrue(1 == returnedAssignments.getAgentId());
	}

	@Test
	public void testUpdateTaskStatus() {
		String taskStatus = "Completed";
		Integer taskId = 1;
		when(taskAssignmentRepository.updateTaskStatus(taskId, taskStatus, LocalDateTime.now())).thenReturn(0);
		Integer returedValue = service.updateTaskStatus(taskId, taskStatus);
		assertTrue(returedValue == 0);
	}

	@Test
	public void testGetAllTasks() {
		when(taskAssignmentRepository.getAllTasks()).thenReturn(getMockTaskAssignmentWithAgents());
		List<TaskAssignments> returedValue = service.getAllTasks();
		TaskAssignments tasks = returedValue.get(0);
		assertNotNull(returedValue);
		assertEquals(tasks.getPriority(), "High");
	}

	@Test
	public void testSaveTaskAssignment() throws NotFoundException {
		Integer[] skillIds = { 1, 2, 3 };
		TaskAssignments taskAssignments = mockTaskAssignments();
		Collection<String> names = Arrays.asList(taskAssignments.getSkillNames());
		when(skillRepository.getSkillIdsBySkillNames(names)).thenReturn(skillIds);
		List<Agent> agents = mockAgents();
		when(agentRepository.getAgentBySkillIds(skillIds)).thenReturn(agents);
		when(taskAssignmentRepository.save(Mockito.any())).thenReturn(taskAssignments);
		TaskAssignments returnedAssignments = service.saveTaskAssignment(taskAssignments);
		assertNotNull(returnedAssignments);
		assertTrue(1 == returnedAssignments.getAgentId());
	}

	@Test
	public void testSaveTaskAssignmentWithSkillNamesEmpty() {
		Integer[] skillIds = { 1, 2, 3 };
		String[] skillNames = {};
		TaskAssignments taskAssignments = mockTaskAssignments();
		taskAssignments.setSkillNames(skillNames);
		Collection<String> names = Arrays.asList(taskAssignments.getSkillNames());
		when(skillRepository.getSkillIdsBySkillNames(names)).thenReturn(skillIds);
		List<Agent> agents = mockAgents();
		when(agentRepository.getAgentBySkillIds(skillIds)).thenReturn(agents);
		when(taskAssignmentRepository.save(Mockito.any())).thenReturn(taskAssignments);
		try {
			service.saveTaskAssignment(taskAssignments);
		} catch (Exception e) {
			assertTrue(e instanceof InvalidParameterException);
		}
	}

	@Test
	public void testSaveTaskAssignmentAgentsNotAvailable() {
		Integer[] skillIds = { 1, 2, 3 };
		TaskAssignments taskAssignments = mockTaskAssignments();
		Collection<String> names = Arrays.asList(taskAssignments.getSkillNames());
		when(skillRepository.getSkillIdsBySkillNames(names)).thenReturn(skillIds);
		List<Agent> agents = new ArrayList<>();
		when(agentRepository.getAgentBySkillIds(skillIds)).thenReturn(agents);
		when(taskAssignmentRepository.save(Mockito.any())).thenReturn(taskAssignments);
		try {
			service.saveTaskAssignment(taskAssignments);
		} catch (Exception e) {
			assertNotNull(e);
		}
	}

	private TaskAssignments mockTaskAssignments() {
		TaskAssignments taskAssignments = new TaskAssignments();
		String[] skillNames = { "skill1", "skill2", "skill3" };
		taskAssignments.setSkillNames(skillNames);
		taskAssignments.setPriority("Low");
		taskAssignments.setTaskStatus("Not Started");
		return taskAssignments;

	}

	private List<Agent> mockAgents() {
		List<Agent> agents = new ArrayList<>();
		Integer[] skillIds = { 1, 2, 3 };
		Agent agent = new Agent();
		agent.setId(1);
		agent.setSkillIds(skillIds);
		agents.add(agent);
		return agents;
	}

	private LinkedList<TaskAssignments> getMockTaskAssignmentWithAgents() {
		LinkedList<TaskAssignments> taskAssignmentsList = new LinkedList<>();
		TaskAssignments taskAssignments = new TaskAssignments();
		String[] skillNames = { "skill1", "skill2", "skill3" };
		Integer[] skillIds = { 1, 2, 3 };
		taskAssignments.setSkillIds(skillIds);
		taskAssignments.setSkillNames(skillNames);
		taskAssignments.setPriority("High");
		taskAssignments.setTaskStatus("Not Started");
		taskAssignments.setAgentId(1);
		taskAssignmentsList.add(taskAssignments);
		return taskAssignmentsList;
	}

}
