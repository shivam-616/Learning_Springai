package com.example.spring_ai.learning_springai.service;

import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.filter.FilterExpressionBuilder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteManagementService {

    private final VectorStore vectorStore;

    public NoteManagementService(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    // Deleting by a specific metadata filter (image_001b17.png)
    public void deleteNotesBySubject(String subject) {
        FilterExpressionBuilder b = new FilterExpressionBuilder();

        // This command tells ChromaDB to wipe everything labeled with this subject
        vectorStore.delete(b.eq("subject", subject).build());

        System.out.println("Cleaned up all notes for: " + subject);
    }

    public void deleteNoteById(List<String> id) {
        vectorStore.delete(id);
    }

}
