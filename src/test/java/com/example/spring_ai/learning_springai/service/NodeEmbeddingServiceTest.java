package com.example.spring_ai.learning_springai.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ai.embedding.EmbeddingModel;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class NodeEmbeddingServiceTest {

    @Autowired
    private EmbeddingModel embeddingModel;

    @Test
    void shouldGenerateNonEmptyEmbedding() {
        // 1. Act
        float[] vector = embeddingModel.embed("Spring Boot is awesome");

        // 2. Assert
        assertThat(vector).isNotEmpty();
        System.out.println("Vector Dimension: " + vector.length);
        // For nomic-embed-text, this is usually 768
    }

    @Test
    void similarSentencesShouldHaveSimilarVectors() {
        // Testing semantic logic
        float[] vector1 = embeddingModel.embed("How to play the Slav Defense in Chess");
        float[] vector2 = embeddingModel.embed("Chess opening strategies for beginners");
        float[] vector3 = embeddingModel.embed("Java Spring Boot Tutorial");

        // We can't easily compare floats, but we can verify the model is working
        assertThat(vector1).isNotEqualTo(vector2);
        assertThat(vector1).isNotEqualTo(vector3);
    }
}