package com.lyh.controller;

import com.lyh.aiservice.ConsultantAiService;
import dev.langchain4j.model.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("")
public class TestController {
/*
    @Autowired
    private OpenAiChatModel model;
*/

    @Autowired
    private ConsultantAiService consultantAiService;

    @GetMapping(value = "/chat" , produces = "text/html;charset=utf-8")
    public Flux<String> chat(String memoryId,String message){
        return consultantAiService.chat(memoryId,message);
    }



    /*@PostMapping("/chat")
    public String chat(String message) {
        String chat = model.chat(message);

        return "chat:"+chat;
    }*/

    
}
