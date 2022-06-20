package com.sandeep.medicalresearch.config.kafka;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.LinkedList;
import java.util.List;

@Data
@Configuration
@ConfigurationProperties("medical-research.kafka.topic")
public class KafkaTopicConfig {

  private final ConfigurableBeanFactory bean_factory;
  private static final String MEDICAL_RESEARCH_REQUEST_TOPIC = "medical_research_request";
  private Short replication;
  private Integer partition;

  public KafkaTopicConfig(ConfigurableBeanFactory bean_factory) {
    this.bean_factory = bean_factory;
  }

  @PostConstruct
  public void createTopics() {
    List<String> medical_research_topics = new LinkedList<>();
    medical_research_topics.add(KafkaTopicConfig.MEDICAL_RESEARCH_REQUEST_TOPIC);

    for (String each_topic : medical_research_topics) {
      if (!bean_factory.containsSingleton(each_topic)) {
        bean_factory.registerSingleton(
            MEDICAL_RESEARCH_REQUEST_TOPIC, new NewTopic(each_topic, partition, replication));
      }
    }
  }
}
