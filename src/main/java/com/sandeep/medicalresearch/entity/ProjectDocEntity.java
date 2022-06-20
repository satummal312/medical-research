package com.sandeep.medicalresearch.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Blob;
import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
@Data
@Entity(name = "PROJECT_DOCS")
@AllArgsConstructor
@NoArgsConstructor
@Table(uniqueConstraints = @UniqueConstraint(name = "PROJECT_DOC_UQ", columnNames = "NAME"), indexes = @Index(name = "PROJECT_DOC_INDEX", columnList = "NAME"))
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
    @CreationTimestamp
    private LocalDateTime create_time_stamp;
    @UpdateTimestamp
    private LocalDateTime update_time_stamp;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "research_project_id")
    private ResearchProjectEntity researchProjectEntity;

    @Override
    public String toString() {
        return "ProjectDocEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", project_file=" + project_file +
                ", create_time_stamp=" + create_time_stamp +
                ", update_time_stamp=" + update_time_stamp +
                '}';
    }
}
