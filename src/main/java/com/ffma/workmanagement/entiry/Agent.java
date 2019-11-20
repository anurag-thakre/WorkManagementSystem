package com.ffma.workmanagement.entiry;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "agent_rt")
public class Agent implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Integer id;
	
	private String name;

	@Type(type = "com.ffma.workmanagement.entiry.IntArrayUserType")
	@Column(name = "skill_ids", columnDefinition = "integer[]")
	private Integer[] skillIds;

	@CreationTimestamp
	@JsonIgnore
	private LocalDateTime createdDate;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer[] getSkillIds() {
		return skillIds;
	}

	public void setSkillIds(Integer[] skillIds) {
		this.skillIds = skillIds;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}
	
	@Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
