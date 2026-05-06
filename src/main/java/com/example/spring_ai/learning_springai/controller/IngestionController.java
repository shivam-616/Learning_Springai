package com.example.spring_ai.learning_springai.controller;



import com.example.spring_ai.learning_springai.service.NoteMetaServie;
import com.example.spring_ai.learning_springai.service.NoteReaderService;
import com.example.spring_ai.learning_springai.service.NoteSplitterService;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RequestMapping("/api/ingest")
@RestController
public class IngestionController {
    private final NoteReaderService readerService;
    private final NoteSplitterService transformerService;
    private final NoteMetaServie metadataService;
    private final VectorStore vectorStore;

    public IngestionController(NoteReaderService readerService, NoteSplitterService transformerService, NoteMetaServie metadataService, VectorStore vectorStore) {
        this.readerService = readerService;
        this.transformerService = transformerService;
        this.metadataService = metadataService;
        this.vectorStore = vectorStore;
    }

    @PostMapping("/notes")
    public String uploadNotes(@RequestParam("file") MultipartFile file,
                              @RequestParam("subject") String subject) {
        try {
            // 1. Convert MultipartFile to Resource
            // This allows you to keep your NoteReaderService logic unchanged
            Resource resource = new ByteArrayResource(file.getBytes()) {
                @Override
                public String getFilename() {
                    return file.getOriginalFilename();
                }
            };

            // 2. READ (Topic 1)
            List<Document> rawDocs = readerService.readNotes(resource);

            // 3. TRANSFORM (Topic 2)
            List<Document> chunks = transformerService.split(rawDocs);

            // 4. ENRICH (Topic 3)
            List<Document> enriched = metadataService.enrich(chunks, subject, file.getOriginalFilename());

            // 5. WRITE & BATCH (Topic 5)
            vectorStore.add(enriched);

            return "Successfully ingested " + enriched.size() + " chunks for " + subject;

        } catch (IOException e) {
            throw new RuntimeException("Could not read file data", e);
        }
    }
}


