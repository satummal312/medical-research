package com.sandeep.medicalresearch.repository;

import com.sandeep.medicalresearch.entity.ResearchProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface ResearchProjectRepository
    extends JpaRepository<ResearchProjectEntity, Long>, JpaSpecificationExecutor {
  Optional<ResearchProjectEntity> findByName(String name);
}
