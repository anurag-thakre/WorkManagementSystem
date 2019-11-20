package com.ffma.workmanagement.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.ffma.workmanagement.entiry.Agent;
import com.ffma.workmanagement.model.AgentTask;
import com.ffma.workmanagement.service.AgentService;


@RunWith(SpringRunner.class)
@WebMvcTest(AgentController.class)
public class AgentControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private AgentService service;

	@WithMockUser(value = "admin")
	@Test
	public void testgetAllAgents() throws Exception {
		String jsonContent = "[{\"id\":1,\"skillIds\":[2,3,4]},{\"id\":2,\"skillIds\":[2,4,7,9]}]";
		Integer[] skillIds1 = {2,3,4};
		Integer[] skillIds2 = {2,4,7,9};
		AgentTask agent1 = populateAgent(1, skillIds1);
		AgentTask agent2 = populateAgent(2, skillIds2);
		Set<AgentTask> agents = new HashSet<>();
		agents.add(agent1);
		agents.add(agent2);
		
		when(service.getAllAgents()).thenReturn(agents);
		this.mockMvc.perform(get("/agents")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().json(jsonContent));
	}
	
	@Test
	public void testgetAllAgents_401() throws Exception {
		when(service.getAllAgents()).thenReturn(new HashSet<>());
		this.mockMvc.perform(get("/agents")).andDo(print()).andExpect(status().isUnauthorized() );
	}
	
	private AgentTask populateAgent(int agentId, Integer[] skillIds) {
		AgentTask agent = new AgentTask();
		agent.setId(agentId);
		agent.setSkillIds(skillIds);
		return agent;
	}
}
