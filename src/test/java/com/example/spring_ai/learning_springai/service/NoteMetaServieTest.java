package com.example.spring_ai.learning_springai.service;

import org.junit.jupiter.api.Test;
import org.springframework.ai.document.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class NoteMetaServieTest{
    @Autowired
   private NoteMetaServie noteMetaServie;

    @Test
    void shouldaddmetadatatoallchunks() {
        List<Document> chunnks = List.of(new Document("chunk 1 "),
                new Document("chunk 2 "),
                new Document("chunk 3 "));

        String subject = "chunk";
        String filepath = "chunk/set ";

        List<Document> enrichedChunks = noteMetaServie.enrich(chunnks, subject, filepath);

        assertThat(enrichedChunks).isNotEmpty();
        assertThat(enrichedChunks).allSatisfy(chunk -> {
            assertThat(chunk.getMetadata().get("subject")).isEqualTo(subject);
            assertThat(chunk.getMetadata().get("filepath")).isEqualTo(filepath);
        });
    }

}