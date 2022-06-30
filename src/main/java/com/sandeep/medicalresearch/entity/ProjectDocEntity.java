package com.sandeep.medicalresearch.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
@Data
@Entity(name = "PROJECT_DOCS")
@AllArgsConstructor
@NoArgsConstructor
@Table(
    uniqueConstraints = @UniqueConstraint(name = "PROJECT_DOC_UQ", columnNames = "NAME"),
    indexes = @Index(name = "PROJECT_DOC_INDEX", columnList = "NAME"))
public class ProjectDocEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PROJECT_DOCS_SEQ")
  @SequenceGenerator(name = "PROJECT_DOCS_SEQ", allocationSize = 1)
  private Long id;

  @Column(name = "NAME")
  private String name;

  @Lob
  @Column(name = "PROJECT_FILE")
  private Blob project_file;

  @Column(name = "FILE_TYPE")
  private String file_type;

  @CreationTimestamp private LocalDateTime create_time_stamp;
  @UpdateTimestamp private LocalDateTime update_time_stamp;

  @ManyToOne(fetch = FetchType.LAZY)
  @JsonIgnore
  private ResearchProjectEntity researchProjectEntity;

  @Override
  public String toString() {
    return "ProjectDocEntity{"
        + "id="
        + id
        + ", name='"
        + name
        + '\''
        + ", project_file="
        + project_file
        + ", create_time_stamp="
        + create_time_stamp
        + ", update_time_stamp="
        + update_time_stamp
        + '}';
  }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProjectDocEntity that = (ProjectDocEntity) o;
        return Objects.equals(name, that.name) && Objects.equals(project_file, that.project_file) && Objects.equals(file_type, that.file_type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, project_file, file_type);
    }

    public static Set<ProjectDocEntity> convertTo(
      MultipartFile[] files, ResearchProjectEntity researchProjectEntity)
      throws IOException, SQLException {
    Set<ProjectDocEntity> projectDocEntities = new HashSet<>();

    for (MultipartFile file : files) {
      if (file.getSize() == 0) continue;

      SerialBlob blob = new SerialBlob(file.getBytes());
      projectDocEntities.add(
          ProjectDocEntity.builder()
              .name(file.getOriginalFilename())
              .project_file(blob)
              .file_type(file.getContentType())
              .researchProjectEntity(researchProjectEntity)
              .build());
    }
    return projectDocEntities;
  }
}
