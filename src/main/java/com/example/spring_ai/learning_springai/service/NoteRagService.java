package com.example.spring_ai.learning_springai.service;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

@Service
public class NoteRagService {

    private ChatClient chatclient;
    private VectorStore vectorstore;

    public NoteRagService(ChatClient.Builder chatclient, VectorStore vectorstore) {
        this.chatclient =chatclient.build();
        this.vectorstore = vectorstore;
    }
    public String askTutor(String query, String subject) {

        var qaAdvisor = QuestionAnswerAdvisor.builder(vectorstore)
                .searchRequest(SearchRequest.builder().similarityThreshold(0.8d).filterExpression("subject == '" + subject + "'").topK(6).build())
                .build();

        return chatclient.prompt()
                .advisors(qaAdvisor)
                .user(query)
                .call()
                .content();
    }

}
