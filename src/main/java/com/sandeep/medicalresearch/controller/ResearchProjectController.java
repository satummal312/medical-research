package com.sandeep.medicalresearch.controller;

import com.sandeep.medicalresearch.dto.ResearchProjectDto;
import com.sandeep.medicalresearch.dto.ResearchProjectFilterDto;
import com.sandeep.medicalresearch.dto.ResearchScientistDto;
import com.sandeep.medicalresearch.entity.ResearchDomain;
import com.sandeep.medicalresearch.service.ResearchProjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Set;

@RestController
@Slf4j
@RequestMapping("/v1/project")
@Api("Research Project APIs")
public class ResearchProjectController {

  private final ResearchProjectService researchProjectService;

  public ResearchProjectController(ResearchProjectService researchProjectService) {
    this.researchProjectService = researchProjectService;
  }

  @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
  @ApiOperation("Create Research Project with or without documentation")
  public ResponseEntity create(
      @RequestPart("payload") @Valid ResearchProjectDto researchProjectDto,
      @RequestParam(value = "files", required = false) @Valid MultipartFile[] project_files)
      throws SQLException, IOException {

    log.info("publishing medical research project");

    researchProjectService.addProject(researchProjectDto, project_files);

    return ResponseEntity.ok().build();
  }

  @PutMapping("/{name}")
  @ApiOperation("Update Research Project info")
  public ResponseEntity update(
      @RequestBody @Valid ResearchProjectDto researchProjectDto, @PathVariable String name)
      throws SQLException, IOException {
    log.info("Updating medical Research Project:" + name);
    researchProjectService.updateProject(name, researchProjectDto);
    return ResponseEntity.noContent().build();
  }

  @DeleteMapping("/{name}")
  @ApiOperation("Delete Research Project")
  public ResponseEntity delete(@PathVariable String name){
    researchProjectService.deleteProject(name);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/{name}/scientist/info")
  @ApiOperation("Get all scientists for this project")
  public Set<ResearchScientistDto> getAllScientistsForThisProject(@PathVariable String name) {
    log.info("Get All scientists working on this project");
    return researchProjectService.getScientistsForThisProject(name);
  }

  @GetMapping
  @ApiOperation("Get All Projects usin filters")
  public Set<ResearchProjectDto> getAllProjectsUsingFilters(
      @RequestParam(name = "id") @Nullable Long id,
      @RequestParam(name = "name") @Nullable String name,
      @RequestParam(name = "domain") @Nullable ResearchDomain domain,
      @RequestParam(name = "funding_amount") @Nullable Integer funding_amount,
      @RequestParam(name = "team_count") @Nullable Integer team_count) {

    ResearchProjectFilterDto researchProjectFilterDto =
        ResearchProjectFilterDto.builder()
            .id(id)
            .name(name)
            .domain(domain)
            .funding_amount(funding_amount)
            .team_count(team_count)
            .build();

    log.info("Get all projects that matched these filters:" + researchProjectFilterDto);
    return researchProjectService.getAllProjectsBasedOnTheseFilters(researchProjectFilterDto);
  }
}
