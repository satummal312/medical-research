package com.sandeep.medicalresearch.repository;

import com.sandeep.medicalresearch.entity.ProjectDocEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectDocRepository extends JpaRepository<ProjectDocEntity, Long> {
    List<ProjectDocEntity> findAllByResearchProjectEntity_Name(String name);
}
