package com.ffma.workmanagement.model;

import java.util.HashSet;
import java.util.Set;

import com.ffma.workmanagement.entiry.Agent;

public class AgentTask extends Agent {

	Set<Integer> taskids = new HashSet<>();

	public Set<Integer> getTaskids() {
		return taskids;
	}

	public void setTaskids(Set<Integer> taskids) {
		this.taskids = taskids;
	}
	
}
