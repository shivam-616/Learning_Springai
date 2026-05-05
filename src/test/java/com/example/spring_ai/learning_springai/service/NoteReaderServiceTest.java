package com.example.spring_ai.learning_springai.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.ai.document.Document;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class NoteReaderServiceTest {
 @Autowired
 private NoteReaderService noteReaderService;


 @Test
 void shouldreadcontentfromvalidfile() {
     // Arrange
     ByteArrayResource r = new ByteArrayResource("java spring ai".getBytes() , "test_note.txt");

//     act

     List<Document> ls = noteReaderService.readNotes(r);

     // asset
     assertThat(ls).isNotEmpty();

 }
    @Test
    void shouldThrowExceptionWhenFileIsInvalid() {
        // Arrange: A resource that doesn't exist
        ByteArrayResource emptyResource = new ByteArrayResource(new byte[0], "empty.txt");

        // Act & Assert: Verify it throws a RuntimeException
        assertThrows(RuntimeException.class, () -> {
            noteReaderService.readNotes(emptyResource);
        });
    }
}