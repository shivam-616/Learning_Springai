package com.example.spring_ai.learning_springai.service;

import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.document.Document;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class NodeEmbeddingService {

    private final EmbeddingModel embeddingModel;

    public NodeEmbeddingService(EmbeddingModel embeddingModel) {
        this.embeddingModel = embeddingModel;
    }

    /**
     * Converts a single piece of text into a vector.
     */
    public float[] computeEmbedding(String text) {
        return embeddingModel.embed(text);
    }

    /**
     * Note: In a real flow, you don't usually call this manually.
     * The VectorStore.add() method calls the embedding model internally!
     */
}
