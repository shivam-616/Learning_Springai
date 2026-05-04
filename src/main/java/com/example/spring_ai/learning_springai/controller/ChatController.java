package com.example.spring_ai.learning_springai.controller;


import org.springframework.ai.huggingface.HuggingfaceChatModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class ChatController {
    private final HuggingfaceChatModel huggingfaceChatModel;

    public ChatController(HuggingfaceChatModel huggingfaceChatModel) {
        this.huggingfaceChatModel = huggingfaceChatModel;
    }
    @GetMapping("/ai/genrate")
    public Map generate(@RequestParam String genrate){
       return Map.of("genrate" , this.huggingfaceChatModel.call(genrate));
    }
}