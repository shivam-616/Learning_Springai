package com.example.spring_ai.learning_springai.service;

import com.example.spring_ai.learning_springai.model.studyInsit;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class StructuredStudyServiceTest {

    @Autowired
    private StructuredStudyService structuredStudyService;

    @Test
    void shouldreturninsit(){
        //act

        studyInsit s =  structuredStudyService.getStructuredInsight("Explain Inner Join", "Java");
        assertThat(s).isNotNull();
        assertThat(s.complexityLevel()).isNotNull();
        assertThat(s.memoryTrick()).isNotNull();
        assertThat(s.keyDefinitions()).isNotNull();

        System.out.println("Extracted Insight: " + s);


    }

}