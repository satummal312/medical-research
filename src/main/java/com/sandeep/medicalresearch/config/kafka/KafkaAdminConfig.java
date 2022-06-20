package com.sandeep.medicalresearch.config.kafka;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Configuration
@ConfigurationProperties("medical-research.kafka")
public class KafkaAdminConfig {
    private String bootstrap_servers;

    @Bean
    public KafkaAdmin kafkaAdmin(){
        Map<String, Object> configs  = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrap_servers);
        return new KafkaAdmin(configs);
    }

    @Bean
    public String bootstrap_servers(){
        return bootstrap_servers;
    }




}
