package com.ffma.workmanagement.entiry;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;
import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name =  "task_assignments_rt")
public class TaskAssignments implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name="task_sequence",sequenceName="task_id_seq", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE,generator="task_sequence")
    @Generated(GenerationTime.INSERT)
    private Integer taskId;

    private Integer agentId;

    @Type( type = "com.ffma.workmanagement.entiry.IntArrayUserType" )
    @Column(
        name = "skill_ids",
        columnDefinition = "integer[]"
    )
    private Integer[] skillIds;
    
    @Transient
    private String[] skillNames;
    
    private String priority;
    
    private String taskStatus;
    
    @CreationTimestamp
    @JsonIgnore
    private LocalDateTime startDate;
    
    @CreationTimestamp
    @JsonIgnore
    private LocalDateTime completeDate;

	public Integer getTaskId() {
		return taskId;
	}

	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}

	public Integer getAgentId() {
		return agentId;
	}

	public void setAgentId(Integer agentId) {
		this.agentId = agentId;
	}

	public Integer[] getSkillIds() {
		return skillIds;
	}

	public void setSkillIds(Integer[] skillIds) {
		this.skillIds = skillIds;
	}

	public String[] getSkillNames() {
		return skillNames;
	}

	public void setSkillNames(String[] skillNames) {
		this.skillNames = skillNames;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getTaskStatus() {
		return taskStatus;
	}

	public void setTaskStatus(String taskStatus) {
		this.taskStatus = taskStatus;
	}

	public LocalDateTime getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDateTime startDate) {
		this.startDate = startDate;
	}

	public LocalDateTime getCompleteDate() {
		return completeDate;
	}

	public void setCompleteDate(LocalDateTime completeDate) {
		this.completeDate = completeDate;
	}
	
	@Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
