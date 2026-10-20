package com.lyh.aiservice;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.spring.AiService;
import dev.langchain4j.service.spring.AiServiceWiringMode;
import reactor.core.publisher.Flux;

@AiService(
        wiringMode = AiServiceWiringMode.EXPLICIT, //
        chatModel = "openAiChatModel", // 聊天模型
        streamingChatModel = "openAiStreamingChatModel", //输入输出流
//        chatMemory = "chatMemory" //聊天记忆
        chatMemoryProvider = "chatMemoryProvider", // 聊天记忆隔离提供者
        contentRetriever = "contentRetriever" ,// 内容检索器
        tools = "reservationTools" //工具

)
public interface ConsultantAiService {
    //用于聊天的方法
    @SystemMessage(fromResource = "system.txt")
    Flux<String> chat(
            @MemoryId String memoryId,
            @UserMessage String message
    );
}
