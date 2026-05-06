package com.example.spring_ai.learning_springai.service;

import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.ai.chroma.vectorstore.ChromaVectorStore;
import org.springframework.ai.document.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class NoteSearchServiceTest {

    @Autowired
    private ChromaVectorStore vectorStore;
    @Autowired
    private NoteSearchService searchService;

    @Test
    void shouldonlyReturntheSpecficSubject(){
        Document doc1 = new Document("Java is object oriented", Map.of("subject", "Java"));
        Document doc2 = new Document("The Slav Defense is solid", Map.of("subject", "Chess"));
        vectorStore.add(List.of(doc1, doc2));
        // Act: Search for "Defense" but filter for "Java"
        List<Document> results = searchService.searchNotes("Tell me about defense", "Java");

        // Assert: It should be empty or NOT contain the Chess note
        assertThat(results).isEmpty();
    }
}