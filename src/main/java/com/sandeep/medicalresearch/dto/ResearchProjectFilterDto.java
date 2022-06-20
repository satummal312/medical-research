package com.sandeep.medicalresearch.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.sandeep.medicalresearch.entity.ResearchDomain;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.MultiValueMap;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResearchProjectFilterDto {
  private Long id;
  private String name;
  private Integer funding_amount;
  private Integer team_count;
  private ResearchDomain domain;
  private String scientist_name;
}
