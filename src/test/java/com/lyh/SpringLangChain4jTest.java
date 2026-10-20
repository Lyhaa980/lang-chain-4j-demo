package com.lyh;

import dev.langchain4j.model.openai.OpenAiChatModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SpringLangChain4jTest {

    @Autowired
    private OpenAiChatModel model;

    @Test
    public void testModel(){
        String chat = model.chat("你好");
        System.out.println(chat);
    }
}
