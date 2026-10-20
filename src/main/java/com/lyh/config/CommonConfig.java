package com.lyh.config;

import com.lyh.aiservice.ConsultantAiService;
import com.lyh.repository.RedisChatMemoryStore;
import dev.langchain4j.community.store.embedding.redis.RedisEmbeddingStore;
import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.loader.ClassPathDocumentLoader;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import dev.langchain4j.store.memory.chat.InMemoryChatMemoryStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class CommonConfig {
    @Autowired
    private OpenAiChatModel model;

    @Autowired
    private RedisChatMemoryStore redisChatMemoryStore;

    @Autowired
    private RedisEmbeddingStore redisEmbeddingStore;


    // 使用aiService为接口创建代理对象
    /*@Bean
    public ConsultantAiService consultantAiService() {
        ConsultantAiService consultantAiService = AiServices.builder(ConsultantAiService.class)
                .chatModel(model)
                .build();

        return consultantAiService;
    }*/

    //实现记忆隔离的ChatMemory
   /* @Bean
    public ChatMemory chatMemory(){
        return MessageWindowChatMemory.builder()
                .maxMessages(20)
                .build();
    }*/

    @Bean
    public ChatMemoryProvider chatMemoryProvider(){
        ChatMemoryProvider chatMemoryProvider = new ChatMemoryProvider() {
            @Override
            public ChatMemory get(Object memoryId) {
                return MessageWindowChatMemory.builder()
                        .id(memoryId)
                        .maxMessages(20)
                        .chatMemoryStore(redisChatMemoryStore)
                        .build();
            }
        };
        return chatMemoryProvider;
    }

    /**
     * 向量数据库
     * 配置向量数据库，和对应的操作
     * @return 向量数据库
     */
    @Bean
    public EmbeddingStore store(){
        //1、加载文档进内存
        List<Document> documents = ClassPathDocumentLoader.loadDocuments("content");
        //2、构建向量 数据库操作对象  这个操作的是内存版本的数据库。现在使用的是RediSearch
        InMemoryEmbeddingStore store = new InMemoryEmbeddingStore();
        //3、构建一个EmbeddingStoreIngestor对象，完成文本数据切割，向量化，存储
        EmbeddingStoreIngestor build = EmbeddingStoreIngestor.builder()
                //由于使用的是
                .embeddingStore(redisEmbeddingStore)
                .build();
        build.ingest(documents);
        return store;
    }


    //写向量数据库索引的bean
    @Bean
    public ContentRetriever contentRetriever(EmbeddingStore store){
        return EmbeddingStoreContentRetriever.builder()
                .embeddingStore(redisEmbeddingStore) //对应的向量数据库
                .minScore(0.5) // 设置最小分数阈值
                .maxResults(3) // 设置最大结果数
                .build();

    }







}
