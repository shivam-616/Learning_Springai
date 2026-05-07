package com.example.spring_ai.learning_springai.service;

import com.example.spring_ai.learning_springai.model.studyInsit;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.StructuredOutputValidationAdvisor;
import org.springframework.ai.chat.client.advisor.api.BaseAdvisor;
import org.springframework.ai.document.Document;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.stream.LangCollectors.collect;

@Service
public class StructuredStudyService {

    private ChatClient chatClient;
    private NoteSearchService noteSearchService;

    public StructuredStudyService(ChatClient.Builder builder, NoteSearchService searchService) {
        this.chatClient = builder.build();
        this.noteSearchService = searchService;
    }

    public studyInsit getStructuredInsight(String query, String subject) {
        // Use your existing search logic
        List<Document> relevantDocs = noteSearchService.searchNotes(query, subject);

        String context = relevantDocs.stream()
                .map(Document::getFormattedContent)
                .collect(Collectors.joining("\n\n"));

        var validationAdvisor = StructuredOutputValidationAdvisor.builder()
                .outputType(studyInsit.class)
                .maxRepeatAttempts(3)
                .advisorOrder(BaseAdvisor.HIGHEST_PRECEDENCE + 1000)
                .build();
        return chatClient.prompt()
                .user(u -> u.text("""
                        Analyze the provided context from my {subject} notes.
                        Extract the core concept into a structured format.
                        
                        CONTEXT:
                        {context}
                        
                        QUERY:
                        {query}
                        """)
                        .param("subject", subject)
                        .param("context", context)
                        .param("query", query))
                .advisors(validationAdvisor)
                .call()
                .entity(studyInsit.class);
    }
}
