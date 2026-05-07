package com.example.spring_ai.learning_springai.service;

import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.VectorStoreRetriever;
import org.springframework.ai.vectorstore.filter.Filter;
import org.springframework.ai.vectorstore.filter.FilterExpressionBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class NoteSearchService {

    @Autowired
    private  VectorStore vectorStore;


    public List<Document> searchNotes(String query , String subject){

        FilterExpressionBuilder f = new FilterExpressionBuilder();
        Filter.Expression fe =  f.eq("subject" , subject).build();

        SearchRequest searchRequest = SearchRequest.builder()
                .query(query)
                .topK(4)
                .similarityThreshold(0.7)
                .filterExpression(fe)
                .build();
        return vectorStore.similaritySearch(searchRequest);
    }
}
