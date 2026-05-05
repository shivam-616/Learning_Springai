package com.example.spring_ai.learning_springai.service;

import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.stereotype.Service;
import org.springframework.ai.document.Document;



import java.io.IOException;
import java.util.List;

@Service
public class NoteSplitterService {
    private final TokenTextSplitter splitter = new TokenTextSplitter();

    public List<Document> split(List<Document> resource) throws IOException {
        return splitter.apply(resource);

    }

}
