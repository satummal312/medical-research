package com.sandeep.medicalresearch.service;

import com.sandeep.medicalresearch.dao.ProjectDocDao;
import com.sandeep.medicalresearch.dao.ResearchProjectDao;
import com.sandeep.medicalresearch.dao.ResearchScientistDao;
import com.sandeep.medicalresearch.dao.kafka.ResearchProjectKafkaDao;
import com.sandeep.medicalresearch.dto.*;
import com.sandeep.medicalresearch.entity.ProjectDocEntity;
import com.sandeep.medicalresearch.entity.ResearchProjectEntity;
import com.sandeep.medicalresearch.entity.ResearchScientistEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class ResearchProjectService {

  private final ResearchProjectDao researchProjectDao;
  private final ResearchScientistDao researchScientistDao;
  private final ResearchProjectKafkaDao researchProjectKafkaDao;

  private final ProjectDocDao projectDocDao;

  public ResearchProjectService(
      ResearchProjectDao researchProjectDao,
      ResearchScientistDao researchScientistDao,
      ResearchProjectKafkaDao researchProjectKafkaDao,
      ProjectDocDao projectDocDao) {
    this.researchProjectDao = researchProjectDao;
    this.researchScientistDao = researchScientistDao;
    this.researchProjectKafkaDao = researchProjectKafkaDao;
    this.projectDocDao = projectDocDao;
  }

  public void addProject(ResearchProjectDto researchProjectDto, MultipartFile[] project_files)
      throws SQLException, IOException {

    researchProjectDao.save(researchProjectDto, project_files);
  }

  public Set<ResearchScientistDto> getScientistsForThisProject(String name) {

    Set<ResearchScientistDto> researchScientistDtosSet = new HashSet<>();

    List<ResearchScientistEntity> researchScientistEntityList =
        researchScientistDao.findAllByProjectName(name);
    researchScientistEntityList.forEach(
        researchScientistEntity ->
            researchScientistDtosSet.add(
                ResearchScientistDto.builder()
                    .id(researchScientistEntity.getId())
                    .name(researchScientistEntity.getName())
                    .email(researchScientistEntity.getEmail())
                    .dob(researchScientistEntity.getDob())
                    .build()));

    return researchScientistDtosSet;
  }

  public Set<ResearchProjectDto> getAllProjectsBasedOnTheseFilters(
      ResearchProjectFilterDto researchProjectFilterDto) {

    Set<ResearchProjectDto> researchProjectDtoSet = new HashSet<>();

    List<ResearchProjectEntity> researchProjectEntityList =
        researchProjectDao.findAllByFilters(researchProjectFilterDto);

    researchProjectEntityList.forEach(
        researchProjectEntity -> {
          researchProjectDtoSet.add(
              ResearchProjectDto.builder()
                  .name(researchProjectEntity.getName())
                  .domain(researchProjectEntity.getDomain())
                  .funding_amount(researchProjectEntity.getFunding_amount())
                  .team_count(researchProjectEntity.getTeam_count())
                  .build());
        });

    return researchProjectDtoSet;
  }

  public void updateProject(String name, ResearchProjectDto researchProjectDto) {
    researchProjectDao.update(name, researchProjectDto);
  }

  public void deleteProject(String name) {

    researchProjectKafkaDao.sendDeleteRequest(
        ProjectDeleteKafkaMessageDto.builder().name(name).build());
    researchProjectDao.delete(name);
  }

  public List<ProjectDocDto> getDocsForThisProject(String name) throws SQLException {

    List<ProjectDocDto> projectDocDtoSet = new LinkedList<>();
    List<ProjectDocEntity> projectDocEntityList = projectDocDao.findAllByProjectName(name);

    for (ProjectDocEntity projectDocEntity : projectDocEntityList) {
      int length = (int) projectDocEntity.getProject_file().length();
      byte[] project_file_bytes = projectDocEntity.getProject_file().getBytes(1, length);
      projectDocDtoSet.add(
          ProjectDocDto.builder()
              .file_type(projectDocEntity.getFile_type())
              .file(project_file_bytes)
              .name(projectDocEntity.getName())
              .build());
    }

    return projectDocDtoSet;
  }
}
