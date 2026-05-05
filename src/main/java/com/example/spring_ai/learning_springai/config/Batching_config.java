package com.example.spring_ai.learning_springai.config;


import com.knuddels.jtokkit.api.EncodingType;
import org.springframework.ai.embedding.BatchingStrategy;
import org.springframework.ai.embedding.TokenCountBatchingStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Batching_config {
   @Bean
    public BatchingStrategy batchingStrategy() {
       return new TokenCountBatchingStrategy(
               EncodingType.CL100K_BASE,  // Specify the encoding type
               8000,                      // Set the maximum input token count
               0.1                        // Set the reserve percentage
       );
   }
}
