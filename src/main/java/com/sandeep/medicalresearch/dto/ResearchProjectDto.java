package com.sandeep.medicalresearch.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.sandeep.medicalresearch.entity.ResearchDomain;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResearchProjectDto {
    @NotEmpty(message = "Research Project Name cannot be null")
    private String name;
    private Integer funding_amount;
    private Integer team_count;
    @NotNull(message ="Research domain cannot be null")
    private ResearchDomain domain;
    @NotEmpty(message = "Scientist list cannot be empty for a research project")
    @Valid
    private Set<ResearchScientistDto> scientist_list;
}
