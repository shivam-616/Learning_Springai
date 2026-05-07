package com.example.spring_ai.learning_springai.model;

import java.util.List;

public record studyInsit(
        String conceptName,
        List<String> keyDefinitions,
        String complexityLevel,
        String memoryTrick
) {
}
