package com.sandeep.medicalresearch.dao.kafka;

import com.sandeep.medicalresearch.dto.ProjectDeleteKafkaMessageDto;
import com.sandeep.medicalresearch.exception.MedicalResearchKafkaProducerException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Component
@Slf4j
public class ResearchProjectKafkaDao {

  private final KafkaTemplate<String, String> kafkaTemplate;

  private static final String PROJECT_DELETE_KAFKA_TOPIC = "medical_research_request";

  public ResearchProjectKafkaDao(KafkaTemplate kafkaTemplate) {
    this.kafkaTemplate = kafkaTemplate;
  }

  public void sendDeleteRequest(ProjectDeleteKafkaMessageDto projectDeleteKafkaMessageDto) {

    ListenableFuture<SendResult<String, String>> future =
        kafkaTemplate.send(PROJECT_DELETE_KAFKA_TOPIC, projectDeleteKafkaMessageDto.toString());

    future.addCallback(
            new ListenableFutureCallback<>() {

                @Override
                public void onSuccess(SendResult<String, String> result) {
                    log.info(
                            "Sent message=["
                                    + projectDeleteKafkaMessageDto
                                    + "] with offset=["
                                    + result.getRecordMetadata().offset()
                                    + "]");
                }

                @Override
                public void onFailure(Throwable ex) {
                    log.error(
                            "Unable to send message=["
                                    + projectDeleteKafkaMessageDto
                                    + "] due to : "
                                    + ex.getMessage());
                    throw new MedicalResearchKafkaProducerException("Unable to send message=["
                            + projectDeleteKafkaMessageDto
                            + "] due to : "
                            + ex.getMessage());
                }
            });
  }
}
