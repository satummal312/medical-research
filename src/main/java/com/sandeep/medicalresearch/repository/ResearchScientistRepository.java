package com.sandeep.medicalresearch.repository;

import com.sandeep.medicalresearch.entity.ResearchScientistEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ResearchScientistRepository extends JpaRepository<ResearchScientistEntity, Long> {

  List<ResearchScientistEntity> findByResearchProjectEntitySet_name(String name);

  Optional<ResearchScientistEntity> findById(Long id);

}
