package com.ffma.workmanagement.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ffma.workmanagement.entiry.Agent;

@Repository 
public interface AgentRepository
        extends CrudRepository<Agent, String> {

    @Query("select a from Agent a where a.skillIds = ?1")
    List<Agent> getAgentBySkillIds(Integer[] skillIds);
    
    @Query("select a from Agent a")
    Set<Agent> getAllAgents();
    

}
