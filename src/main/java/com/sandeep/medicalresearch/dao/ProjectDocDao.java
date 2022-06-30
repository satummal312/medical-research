package com.sandeep.medicalresearch.dao;

import com.sandeep.medicalresearch.dto.ProjectDocDto;
import com.sandeep.medicalresearch.dto.ResearchProjectDto;
import com.sandeep.medicalresearch.entity.ProjectDocEntity;
import com.sandeep.medicalresearch.repository.ProjectDocRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@Slf4j
public class ProjectDocDao {

  private final ProjectDocRepository projectDocRepository;

  public ProjectDocDao(ProjectDocRepository projectDocRepository) {
    this.projectDocRepository = projectDocRepository;
  }

  public ProjectDocEntity save(ProjectDocDto projectDocDto) throws IOException, SQLException {
    SerialBlob blob = new SerialBlob(projectDocDto.getFile());

        return ProjectDocEntity.builder()
            .name(projectDocDto.getName())
            .project_file(blob)
            .file_type(projectDocDto.getFile_type())
            .build();
  }

  public Set<ProjectDocEntity> saveAllDocs(MultipartFile[] files) throws SQLException, IOException {

    Set<ProjectDocEntity> projectDocEntities = new HashSet<>();
    for (MultipartFile file : files) {
      if (file.getSize() == 0) continue;
      ProjectDocEntity projectDocEntity =
          save(
              ProjectDocDto.builder()
                  .name(file.getOriginalFilename())
                  .file(file.getBytes())
                  .file_type(file.getContentType())
                  .build());
      projectDocEntities.add(projectDocEntity);
    }
    return projectDocEntities;
  }

  public List<ProjectDocEntity> findAllByProjectName(String name) {
    return projectDocRepository.findAllByResearchProjectEntity_Name(name);
  }
}
