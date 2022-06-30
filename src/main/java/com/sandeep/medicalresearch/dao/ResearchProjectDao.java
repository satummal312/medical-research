package com.sandeep.medicalresearch.dao;

import com.sandeep.medicalresearch.common.specifications.ResearchProjectSpecification;
import com.sandeep.medicalresearch.dto.ResearchProjectDto;
import com.sandeep.medicalresearch.dto.ResearchProjectFilterDto;
import com.sandeep.medicalresearch.dto.ResearchScientistDto;
import com.sandeep.medicalresearch.entity.ProjectDocEntity;
import com.sandeep.medicalresearch.entity.ResearchProjectEntity;
import com.sandeep.medicalresearch.entity.ResearchScientistEntity;
import com.sandeep.medicalresearch.exception.ProjectNotFoundException;
import com.sandeep.medicalresearch.repository.ResearchProjectRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.text.html.Option;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Component
public class ResearchProjectDao {

  private final ResearchProjectRepository researchProjectRepository;
  private final ProjectDocDao projectDocDao;
  private final ResearchScientistDao researchScientistDao;

  public ResearchProjectDao(
      ResearchProjectRepository researchProjectRepository,
      ProjectDocDao projectDocDao,
      ResearchScientistDao researchScientistDao) {
    this.researchProjectRepository = researchProjectRepository;
    this.projectDocDao = projectDocDao;
    this.researchScientistDao = researchScientistDao;
  }

  @Transactional
  @Retryable(
      value = {DataAccessException.class},
      maxAttempts = 3,
      backoff = @Backoff(delay = 5000))
  public ResearchProjectEntity save(
      ResearchProjectDto researchProjectDto, MultipartFile[] project_files)
      throws SQLException, IOException {

    Set<ResearchScientistEntity> researchScientistEntitySet =
        researchScientistDao.saveAllScientists(researchProjectDto.getScientist_list());

    ResearchProjectEntity researchProjectEntity =
        ResearchProjectEntity.builder()
            .name(researchProjectDto.getName())
            .funding_amount(researchProjectDto.getFunding_amount())
            .scientistEntities(researchScientistEntitySet)
            .team_count(researchProjectDto.getTeam_count())
            .domain(researchProjectDto.getDomain())
            .build();
    if (null != project_files) {
      Set<ProjectDocEntity> projectDocEntities =
          ProjectDocEntity.convertTo(project_files, researchProjectEntity);

      researchProjectEntity.setDocsEntityList(projectDocEntities);
    }

    return researchProjectRepository.save(researchProjectEntity);
  }

  @Transactional
  @Retryable(
      value = {DataAccessException.class},
      maxAttempts = 3,
      backoff = @Backoff(delay = 5000))
  public Optional<ResearchProjectEntity> findByName(String name) {
    return researchProjectRepository.findByName(name);
  }

  @Transactional
  @Retryable(
      value = {DataAccessException.class},
      maxAttempts = 3,
      backoff = @Backoff(delay = 5000))
  public List<ResearchProjectEntity> findAllByFilters(
      ResearchProjectFilterDto researchProjectFilterDto) {

    Specification<ResearchProjectEntity> researchProjectEntitySpecification =
        Specification.where(
            ResearchProjectSpecification.hasId(researchProjectFilterDto.getId())
                .and(ResearchProjectSpecification.hasName(researchProjectFilterDto.getName()))
                .and(
                    ResearchProjectSpecification.hasDomain(researchProjectFilterDto.getDomain())
                        .and(
                            ResearchProjectSpecification.hasTeamCount(
                                researchProjectFilterDto.getTeam_count()))
                        .and(
                            ResearchProjectSpecification.hasFundingAmount(
                                researchProjectFilterDto.getFunding_amount()))));

    return researchProjectRepository.findAll(researchProjectEntitySpecification);
  }

  private ResearchProjectEntity save(ResearchProjectEntity researchProjectEntity) {
    return researchProjectRepository.save(researchProjectEntity);
  }

  @Transactional
  @Retryable(
      value = {DataAccessException.class},
      maxAttempts = 3,
      backoff = @Backoff(delay = 5000))
  public ResearchProjectEntity update(String name, ResearchProjectDto researchProjectDto) {

    Optional<ResearchProjectEntity> researchProjectEntityOptional = findByName(name);
    if (researchProjectEntityOptional.isPresent()) {
      ResearchProjectEntity researchProjectEntity = researchProjectEntityOptional.get();
      researchProjectEntity.setName(researchProjectDto.getName());
      researchProjectEntity.setDomain(researchProjectDto.getDomain());
      researchProjectEntity.setFunding_amount(researchProjectDto.getFunding_amount());
      researchProjectEntity.setTeam_count(researchProjectDto.getTeam_count());

      Set<ResearchScientistDto> researchScientistDtos = researchProjectDto.getScientist_list();

      researchScientistDtos.forEach(
          researchScientistDto -> {
            researchScientistDao.update(researchScientistDto);
          });
      return save(researchProjectEntity);
    } else {
      throw new ProjectNotFoundException("Research Project is not found in our DB:" + name);
    }
  }

  @Transactional
  @Retryable(
      value = {DataAccessException.class},
      maxAttempts = 3,
      backoff = @Backoff(delay = 5000))
  public void delete(String name) {
    Optional<ResearchProjectEntity> researchProjectEntityOptional = findByName(name);
    researchProjectEntityOptional.ifPresent(researchProjectRepository::delete);
  }
}
