package com.sandeep.medicalresearch.dao;

import com.sandeep.medicalresearch.dto.ResearchScientistDto;
import com.sandeep.medicalresearch.entity.ResearchProjectEntity;
import com.sandeep.medicalresearch.entity.ResearchScientistEntity;
import com.sandeep.medicalresearch.repository.ResearchScientistRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Component
@Slf4j
public class ResearchScientistDao {

  private final ResearchScientistRepository researchScientistRepository;

  public ResearchScientistDao(ResearchScientistRepository researchScientistRepository) {
    this.researchScientistRepository = researchScientistRepository;
  }

  @Transactional
  @Retryable(value = DataAccessException.class, maxAttempts = 3, backoff = @Backoff(delay = 5000))
  public ResearchScientistEntity save(ResearchScientistDto researchScientistDto) {

    ResearchScientistEntity researchScientistEntity =
        ResearchScientistEntity.builder()
            .id(researchScientistDto.getId())
            .name(researchScientistDto.getName())
            .dob(researchScientistDto.getDob())
            .build();

    return researchScientistRepository.save(researchScientistEntity);
  }

  @Transactional
  @Retryable(value = DataAccessException.class, maxAttempts = 3, backoff = @Backoff(delay = 5000))
  public Set<ResearchScientistEntity> saveAllScientists(Set<ResearchScientistDto> scientist_list) {
    Set<ResearchScientistEntity> researchScientistEntitySet = new HashSet<>();
    scientist_list.forEach(
        researchScientistDto -> {
          researchScientistEntitySet.add(save(researchScientistDto));
        });
    return researchScientistEntitySet;
  }

  @Transactional
  @Retryable(value = DataAccessException.class, maxAttempts = 3, backoff = @Backoff(delay = 5000))
  public List<ResearchScientistEntity> findAllByProjectName(String name) {

    return researchScientistRepository.findByResearchProjectEntitySet_name(name);
  }

  @Transactional
  @Retryable(value = DataAccessException.class, maxAttempts = 3, backoff = @Backoff(delay = 5000))
  public Optional<ResearchScientistEntity> findById(Long id) {
    return researchScientistRepository.findById(id);
  }

  @Transactional
  @Retryable(value = DataAccessException.class, maxAttempts = 3, backoff = @Backoff(delay = 5000))
  public ResearchScientistEntity save(ResearchScientistEntity researchScientistEntity) {
    return researchScientistRepository.save(researchScientistEntity);
  }

  @Transactional
  @Retryable(
          value = {DataAccessException.class},
          maxAttempts = 3,
          backoff = @Backoff(delay = 5000))
  public void update(ResearchScientistDto researchScientistDto) {
    Optional<ResearchScientistEntity> researchScientistEntityOptional =
        findById(researchScientistDto.getId());
    if (researchScientistEntityOptional.isPresent()) {
      ResearchScientistEntity researchScientistEntity = researchScientistEntityOptional.get();
      researchScientistEntity.setName(researchScientistDto.getName());
      researchScientistEntity.setDob(researchScientistDto.getDob());
      researchScientistEntity.setEmail(researchScientistDto.getEmail());
      save(researchScientistEntity);
    } else {
      ResearchScientistEntity researchScientistEntity =
          ResearchScientistEntity.builder()
              .id(researchScientistDto.getId())
              .name(researchScientistDto.getName())
              .dob(researchScientistDto.getDob())
              .email(researchScientistDto.getEmail())
              .build();
      save(researchScientistEntity);
    }
  }
}
