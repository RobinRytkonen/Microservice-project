package com.work.LeoProjectPayment.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.config.TopicConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

import static com.work.LeoProjectPayment.util.Constants.TRANSACTION_TOPIC;

@Configuration
public class KafkaConfig {

    @Bean
    public NewTopic transactionTopic() {
        return TopicBuilder
                .name(TRANSACTION_TOPIC)
                .config(TopicConfig.RETENTION_MS_CONFIG, String.valueOf(10000))
                .partitions(1)
                .replicas(1)
                .build();
    }
}
