package com.example.spring_ai.learning_springai.controller;

import org.junit.jupiter.api.Test;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class IngestionControllerTest {

    @Autowired
    private IngestionController ingestionController;

    @Autowired
    private VectorStore vectorStore;

    @Test
    void fullIngestionPipelineTest() throws IOException {
        // Arrange
        String content = "The Slav Defense is a very solid chess opening for Black.";
        ByteArrayResource resource = new ByteArrayResource(content.getBytes(), "chess.txt");

        MultipartFile m = new MockMultipartFile("file", resource.getFilename(), "text/plain", resource.getInputStream());

        // Act: Run the whole pipeline
        ingestionController.uploadNotes(m, "Chess");
        // Assert: Try to find the document in ChromaDB
        var results = vectorStore.similaritySearch(
                SearchRequest.builder().query("What is the Slav Defense?").topK(5).build()
        );

        assertThat(results).isNotEmpty();
        assertThat(results.get(0).getFormattedContent()).contains("Slav Defense");
        assertThat(results.get(0).getMetadata()).containsEntry("subject", "Chess");
    }
}