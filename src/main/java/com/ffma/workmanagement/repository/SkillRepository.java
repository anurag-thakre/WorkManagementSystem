package com.ffma.workmanagement.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ffma.workmanagement.entiry.Skill;

@Repository 
public interface SkillRepository
        extends CrudRepository<Skill, String> {

    @Query("select s.id from Skill s where s.name in ?1")
    Integer[] getSkillIdsBySkillNames( Collection<String> names);

}
