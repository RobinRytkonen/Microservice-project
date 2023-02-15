package com.work.LeoProjectGaming.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.config.TopicConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

import static com.work.LeoProjectGaming.util.Constants.BETTING_TRANSFER_TOPIC;

@Configuration
public class KafkaConfig {

    @Bean
    public NewTopic bettingTransferTopic() {
        return TopicBuilder
                .name(BETTING_TRANSFER_TOPIC)
                .config(TopicConfig.RETENTION_MS_CONFIG, String.valueOf(10000))
                .partitions(1)
                .replicas(1)
                .build();
    }
}
