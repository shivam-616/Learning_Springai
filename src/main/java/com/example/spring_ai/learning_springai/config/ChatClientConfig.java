package com.example.spring_ai.learning_springai.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.memory.repository.jdbc.JdbcChatMemoryRepository;
import org.springframework.ai.chat.memory.repository.jdbc.PostgresChatMemoryRepositoryDialect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class ChatClientConfig {

    // 1. Define the ChatClient and attach the Advisor (image_850472.png)
    @Bean
    public ChatClient chatClient(ChatClient.Builder chatClientBuilder, ChatMemory chatMemory) {
        return chatClientBuilder
                // Professional way to build the advisor with memory (image_850472.png)
                .defaultAdvisors(MessageChatMemoryAdvisor.builder(chatMemory).build())
                .build();
    }

    @Bean
    public ChatMemory chatMemory(JdbcTemplate jdbcTemplate) {
        // Correctly building the JDBC repository (image_850478.png)
        var repository = JdbcChatMemoryRepository.builder()
                .jdbcTemplate(jdbcTemplate)
                .dialect(new org.springframework.ai.chat.memory.repository.jdbc.H2ChatMemoryRepositoryDialect())
                .build();

        // Wrapping in a MessageWindowChatMemory to control the conversation size
        return MessageWindowChatMemory.builder()
                .chatMemoryRepository(repository)
                .build();
    }
}