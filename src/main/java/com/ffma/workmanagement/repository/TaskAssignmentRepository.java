package com.ffma.workmanagement.repository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ffma.workmanagement.entiry.TaskAssignments;

@Repository
public interface TaskAssignmentRepository extends CrudRepository<TaskAssignments, String> {

	@Query("select ta from TaskAssignments ta where ta.skillIds = ?1 and ta.agentId in ?2 and ta.taskStatus != 'Completed'"
			+ " ORDER BY start_date DESC NULLS LAST")
	LinkedList<TaskAssignments> getTasksBySkillIdAndAgent(Integer[] skillIds, Collection<Integer> agentIds);

	@Modifying
	@Transactional
	@Query("update TaskAssignments set taskStatus = ?2, completeDate = ?3  where taskId = ?1")
	Integer updateTaskStatus(Integer taskId, String taskStatus, LocalDateTime completionDate);

	@Query("select ta from TaskAssignments ta where ta.taskStatus != 'Completed'"
			+ " ORDER BY start_date DESC NULLS LAST")
	LinkedList<TaskAssignments> getAllTasks();
	
	@Query("select ta from TaskAssignments ta where agent_id = ?1")
	Set<TaskAssignments> getTaskIdsByAgentId(Integer agentId);

}
