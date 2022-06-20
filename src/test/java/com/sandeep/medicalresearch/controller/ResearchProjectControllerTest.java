package com.sandeep.medicalresearch.controller;

import com.sandeep.medicalresearch.dto.ResearchProjectDto;
import com.sandeep.medicalresearch.dto.ResearchScientistDto;
import com.sandeep.medicalresearch.entity.ResearchDomain;
import com.sandeep.medicalresearch.service.ResearchProjectService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.validation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

@ExtendWith(MockitoExtension.class)
@Slf4j
class ResearchProjectControllerTest {

    @Mock
    private ResearchProjectService researchProjectService;

  static Stream<ResearchProjectDto> buildValidResearchProjectDto() {
      Set<ResearchScientistDto> scientistDtoSet = new HashSet<>();
      scientistDtoSet.add(ResearchScientistDto.builder().name("sandeep").id(1L).email("test@gmail.com").build());
    return Stream.of(ResearchProjectDto.builder().name("test").domain(ResearchDomain.BIOCHEMISTRY).scientist_list(scientistDtoSet).build());
  }


  @ParameterizedTest
  @MethodSource("buildValidResearchProjectDto")
  void validatePayload(ResearchProjectDto researchProjectDto){
      log.info("Validating researchProjectDto object");
      Configuration<?> config = Validation.byDefaultProvider().configure();
        ValidatorFactory factory = config.buildValidatorFactory();
       Validator validator = factory.getValidator();
       factory.close();
      Set<ConstraintViolation<ResearchProjectDto>> violationSet = validator.validate(researchProjectDto);
      Assertions.assertEquals(0, violationSet.size(), "Invalid Research Project Dto payload" );
  }

  @ParameterizedTest
  @MethodSource("buildValidResearchProjectDto")
  void create(ResearchProjectDto researchProjectDto) {
    log.info("Testing create function in Research Project controller");
    log.info("Research Project Dto:"+ researchProjectDto);

    ResearchProjectController researchProjectController = new ResearchProjectController(researchProjectService);
      try {
          ResponseEntity response =  researchProjectController.create(researchProjectDto, null);
          Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
      } catch (SQLException e) {
          throw new RuntimeException(e);
      } catch (IOException e) {
          throw new RuntimeException(e);
      }

  }
}
