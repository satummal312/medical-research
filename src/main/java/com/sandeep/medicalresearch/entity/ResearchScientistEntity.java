package com.sandeep.medicalresearch.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity(name = "RESEARCH_SCIENTIST")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(
    uniqueConstraints = @UniqueConstraint(name = "RESEARCH_SCIENTIST_UQ", columnNames = "id"),
    indexes = @Index(name = "RESEARCH_SCIENTIST_INDEX", columnList = "id, name"))
public class ResearchScientistEntity {
  @Id private Long id;
  private String name;
  private String dob;
  private String email;

  @CreationTimestamp private LocalDateTime creation_time_stamp;

  @UpdateTimestamp private LocalDateTime update_time_stamp;

  @ManyToMany(mappedBy = "scientistEntities")
  private Set<ResearchProjectEntity> researchProjectEntitySet;

  @Override
  public String toString() {
    return "ResearchScientistEntity{"
        + "id="
        + id
        + ", name='"
        + name
        + '\''
        + ", dob='"
        + dob
        + '\''
        + ", email='"
        + email
        + '\''
        + ", creation_time_stamp="
        + creation_time_stamp
        + ", update_time_stamp="
        + update_time_stamp
        + '}';
  }
}
