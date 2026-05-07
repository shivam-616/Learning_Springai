package com.example.spring_ai.learning_springai.controller;

import com.example.spring_ai.learning_springai.service.NoteRagService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/rag")
public class RAGController {

    private final NoteRagService ragService;

    public RAGController(NoteRagService ragService) {
        this.ragService = ragService;
    }

    @GetMapping("/ask")
    public String ask(@RequestParam String query, @RequestParam String subject) {
        return ragService.askTutor(query, subject);
    }
}