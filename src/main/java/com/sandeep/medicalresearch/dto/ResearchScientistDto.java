package com.sandeep.medicalresearch.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.sandeep.medicalresearch.entity.ResearchScientistEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResearchScientistDto {

  @NotNull(message = "Scientist ID cannot be empty")
  private Long id;

  @NotEmpty(message = "Scientist name cannot be empty")
  private String name;

  private String dob;

  @Email(message = "Email should be valid")
  private String email;

  public static ResearchScientistDto convert(ResearchScientistEntity researchScientistEntity) {

    return ResearchScientistDto.builder()
        .id(researchScientistEntity.getId())
        .dob(researchScientistEntity.getDob())
        .name(researchScientistEntity.getName())
        .email(researchScientistEntity.getEmail())
        .build();
  }
}
