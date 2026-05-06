package com.example.spring_ai.learning_springai.controller;


import com.example.spring_ai.learning_springai.service.NoteReaderService;
import com.example.spring_ai.learning_springai.service.NoteSearchService;
import com.example.spring_ai.learning_springai.service.NoteSplitterService;
import org.springframework.ai.document.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/api/search")
@RestController
public class SearchController {
    @Autowired
    NoteSearchService  noteSearchService;

    @GetMapping
    public List<Document> search(@RequestParam String query ,@RequestParam String subject) {
        return noteSearchService.searchNotes(query, subject);
    }
}
