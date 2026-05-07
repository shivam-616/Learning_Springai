package com.example.spring_ai.learning_springai.service;
import org.springframework.ai.chat.client.ChatClient;
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

}
