package com.lyh.repository;

import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.ChatMessageDeserializer;
import dev.langchain4j.data.message.ChatMessageSerializer;
import dev.langchain4j.store.memory.chat.ChatMemoryStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.AccessType;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.util.List;


@Repository
public class RedisChatMemoryStore implements ChatMemoryStore {
    @Autowired
    private StringRedisTemplate redisTemplate;


    @Override
    public List<ChatMessage> getMessages(Object memoryId) {
        //从Redis中获取json数据。会话消息
        //根据id进行获取的
        String json = redisTemplate.opsForValue().get(memoryId);
        //把json数据转化成List<ChatMessage>
        List<ChatMessage> list = ChatMessageDeserializer.messagesFromJson(json);
        return list;
    }

    @Override
    public void updateMessages(Object memoryId, List<ChatMessage> messages) {
        //更新会话消息
        //将消息保存到redis
        String json = ChatMessageSerializer.messagesToJson(messages);
        //将json的消息保存到redis
        redisTemplate.opsForValue().set(memoryId.toString(), json, Duration.ofHours(1));
    }
    @Override
    public void deleteMessages(Object memoryId) {
        redisTemplate.delete(memoryId.toString());
    }
}
