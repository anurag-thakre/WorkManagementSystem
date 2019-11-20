package com.ffma.workmanagement.controller;

import static org.hamcrest.CoreMatchers.any;
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.security.InvalidParameterException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.ffma.workmanagement.entiry.TaskAssignments;
import com.ffma.workmanagement.service.TaskAssignmentService;

@RunWith(SpringRunner.class)
@WebMvcTest(TaskController.class)
public class TaskControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private TaskAssignmentService service;

	@WithMockUser(value = "admin")
	@Test
	public void testCreateTask() throws Exception {
		TaskAssignments taskAssignments = new TaskAssignments();
		taskAssignments.setPriority("High");
		taskAssignments.setTaskStatus("Not Started");
		String[] skillNames = { "skill1", "skill2" };
		taskAssignments.setSkillNames(skillNames);

		TaskAssignments response = new TaskAssignments();
		response.setAgentId(1111);
		response.setPriority(taskAssignments.getPriority());
		response.setSkillIds(taskAssignments.getSkillIds());
		response.setTaskStatus(taskAssignments.getTaskStatus());

		RequestBuilder request = MockMvcRequestBuilders.post("/tasks").accept(MediaType.APPLICATION_JSON)
				.content(
						"{\"skillNames\":[\"skill1\",\"skill2\"],\"priority\":\"High\",\"taskStatus\":\"Not Started\"}")
				.contentType(MediaType.APPLICATION_JSON);

		when(service.saveTaskAssignment(taskAssignments)).thenReturn(response);

		MvcResult result = mockMvc.perform(request).andExpect(status().isCreated()).andReturn();
		result.getResponse().getContentAsString();
	}

	@Test
	public void testCreateTaskUnauthorized() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders.post("/tasks").accept(MediaType.APPLICATION_JSON)
				.content(
						"{\"skillNames\":[\"skill1\",\"skill2\"],\"priority\":\"High\",\"taskStatus\":\"Not Started\"}")
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(request).andExpect(status().isUnauthorized()).andReturn();
		result.getResponse().getContentAsString();
	}

	@WithMockUser(value = "admin")
	@Test
	public void testUpdateTask() throws Exception {
		Integer taskId = 44;
		String status = "Completed";
		when(service.updateTaskStatus(taskId, status)).thenReturn(1);

		mockMvc.perform(put("/tasks/{taskId}/status/{status}", taskId, status)).andExpect(status().isOk())
				.andExpect(content().string(containsString("1")));
	}

}
