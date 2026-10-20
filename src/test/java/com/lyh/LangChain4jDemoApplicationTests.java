package com.lyh;

import dev.langchain4j.model.openai.OpenAiChatModel;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class LangChain4jDemoApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void testopenai(){
        // 创建OpenAiChatModel实例
        OpenAiChatModel model = OpenAiChatModel.builder()
                .baseUrl("https://maas.qianwenaiapi.com/compatible-mode/v1")
                .apiKey(System.getenv("QWEN_API_KEY"))
                .modelName("qwen3.8-max")
                .logRequests(true)
                .logResponses(true)
                .build();

        // 调用chat方法和模型进行交互
        String chat = model.chat("你好");
        System.out.println(chat);

    }

}
