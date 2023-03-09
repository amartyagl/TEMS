package com.globallogic.xlstodatabase.topic;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class TopicConfig {
    private String topicname="gmailTopic";
    @Bean
    public NewTopic topic()
    {
        return TopicBuilder.name(topicname)
                .partitions(1)
                .replicas(1)
                .build();
    }
}
