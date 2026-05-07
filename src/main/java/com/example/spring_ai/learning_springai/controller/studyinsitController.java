package com.example.spring_ai.learning_springai.controller;


import com.example.spring_ai.learning_springai.model.studyInsit;
import com.example.spring_ai.learning_springai.service.StructuredStudyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/insit")
@RestController
public class studyinsitController {
    @Autowired
    private StructuredStudyService structuredStudyService;

    @GetMapping
    public studyInsit studyinsit(@RequestParam String query , @RequestParam String subject){
       return  structuredStudyService.getStructuredInsight(query,subject);
    }
}
