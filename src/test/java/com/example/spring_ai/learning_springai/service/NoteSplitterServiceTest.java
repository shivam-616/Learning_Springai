package com.example.spring_ai.learning_springai.service;

import org.junit.jupiter.api.Test;
import org.springframework.ai.document.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.util.List;



@SpringBootTest
class NoteSplitterServiceTest {
    @Autowired
    private NoteSplitterService noteSplitterService;

    @Test
    void shouldSplit() throws IOException {
        //Arrange
        String longText = "Spring Boot makes it easy to create stand-alone, production-grade Spring based Applications. ".repeat(100);
        Document largeDoc = new Document(longText);
        // act
        List<Document> doc = noteSplitterService.split(List.of(largeDoc));
        // assert
        assertThat(doc).hasSizeGreaterThan((1));

    }


}