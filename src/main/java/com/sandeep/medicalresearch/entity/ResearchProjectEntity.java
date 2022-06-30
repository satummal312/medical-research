package com.sandeep.medicalresearch.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

@Entity(name = "RESEARCH_PROJECT")
@AllArgsConstructor
@NoArgsConstructor
@Table(
    uniqueConstraints = @UniqueConstraint(name = "RESEARCH_PROJECT_UQ", columnNames = "NAME"),
    indexes = @Index(name = "RESEARCH_PROJECT_INDEX", columnList = "ID, NAME"))
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
@Data
public class ResearchProjectEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RESEARCH_PROJECT_SEQ")
  @SequenceGenerator(name = "RESEARCH_PROJECT_SEQ", allocationSize = 1)
  private Long id;

  @Column(name = "NAME")
  private String name;

  private Integer funding_amount;
  private Integer team_count;

  @Enumerated(EnumType.STRING)
  private ResearchDomain domain;

  @CreationTimestamp private LocalDateTime creation_time_stamp;

  @UpdateTimestamp private LocalDateTime update_time_stamp;

  @OneToMany(mappedBy = "researchProjectEntity", orphanRemoval = true, cascade = CascadeType.ALL)
  private Set<ProjectDocEntity> docsEntityList;

  @ManyToMany
  @JoinTable(
      name = "project_scientist_mapper",
      joinColumns = @JoinColumn(name = "research_project_id"),
      inverseJoinColumns = @JoinColumn(name = "scientist_id"))
  private Set<ResearchScientistEntity> scientistEntities;

  @Override
  public String toString() {
    return "ResearchProjectEntity{"
        + "id="
        + id
        + ", name='"
        + name
        + '\''
        + ", funding_amount="
        + funding_amount
        + ", team_count="
        + team_count
        + ", domain="
        + domain
        + ", creation_time_stamp="
        + creation_time_stamp
        + ", update_time_stamp="
        + update_time_stamp
        + '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ResearchProjectEntity that = (ResearchProjectEntity) o;
    return Objects.equals(name, that.name) && Objects.equals(funding_amount, that.funding_amount) && Objects.equals(team_count, that.team_count) && domain == that.domain;
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, funding_amount, team_count, domain);
  }
}
