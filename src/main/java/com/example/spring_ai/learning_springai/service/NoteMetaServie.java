package com.example.spring_ai.learning_springai.service;

import org.springframework.ai.document.Document;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class NoteMetaServie {

    public List<Document> enrich(List<Document> chunks , String subject , String filepath){
     for(Document document : chunks){
        Map<String, Object> metadata = document.getMetadata();
        metadata.put("subject", subject);
        metadata.put("filepath", filepath);
     }
     return chunks;
    }
}
