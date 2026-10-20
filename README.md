# lang-chain-4j-demo（AIAdvisor）

基于 **LangChain4j + Spring Boot** 的 AI 志愿填报咨询助手，一个学习实践项目：把「对话记忆 / RAG 知识检索 / Tool Calling」三件事在 Java 侧跑通。

> 说明：本项目是跟随课程的学习实践，代码与结构部分参考课程讲解，在此之上做了配置改造、RAG 知识库与预约工具链路的完整落地。

## 技术栈

| 层面 | 用了什么 |
|------|---------|
| 框架 | Spring Boot 3 + Java 17 + LangChain4j |
| 模型 | 通义千问（OpenAI 兼容协议接入，`qwen3.8-max`），同时注册非流式与流式两个模型 Bean |
| 记忆 | `ChatMemory` + 自定义 `RedisChatMemoryStore`，会话历史持久化到 Redis |
| 检索 | LangChain4j 社区 Redis 向量库（维度 384），知识源为 `src/main/resources/content/*.md` |
| 工具 | `@Tool` 声明的预约工具，模型按需调用 |
| 数据 | MyBatis + MySQL（本地 `volunteer` 库）；令牌隔离用 Redis |
| 前端 | `src/main/resources/static/index.html` |

## 功能

- **AI 咨询对话**：`ConsultantAiService` 用 `@AiService` 声明，绑定 chatModel / streamingChatModel
- **多轮记忆**：会话历史按 conversationId 隔离并持久化，重启不丢
- **RAG 问答**：把高校/专业资料切分向量化后写入 Redis 向量库，问答走检索增强
- **预约工具调用**：模型识别到预约意图 → 调用 `ReservationTools` → 落库（`reservation` 表）
- **流式输出**：返回 `Flux<String>` 的接口走流式模型，前端打字机效果

## 目录结构

```
src/main/java/com/lyh
├── LangChain4jDemoApplication.java   # 启动类
├── aiservice/ConsultantAiService.java # AI 服务接口（@AiService）
├── config/CommonConfig.java           # 模型/记忆/检索的装配
├── controller/                        # 对话与预约接口
├── repository/RedisChatMemoryStore.java # 会话记忆持久化
├── service/ + service/impl/           # 预约业务
├── mapper/ + entity/po/               # MyBatis 数据层
└── tools/ReservationTools.java        # @Tool 工具

src/main/resources
├── application.yml                    # 配置（密钥走环境变量）
├── content/*.md                       # RAG 知识源
├── mapper/ReservationMapper.xml
└── static/index.html                  # 页面
```

## 快速开始

1. 前置：JDK 17、MySQL（建库 `volunteer`，执行建表脚本）、Redis（默认 `localhost:6379`）
2. 配置环境变量（**API Key 不写在代码里**）：

```bash
set QWEN_API_KEY=你的通义千问 API Key
```

3. 启动：

```bash
mvn spring-boot:run
```

4. 打开 `http://localhost:8082/`

## 配置说明

- `application.yml` 里的 `api-key: ${QWEN_API_KEY}` 从环境变量读取，仓库里不含任何真实密钥
- 模型地址、模型名、向量库维度都在 `langchain4j` 段下，换厂商只改 `base-url` + `model-name`
- 本地 MySQL 口令也建议改成环境变量形式，避免入库

## 学习要点

- 大模型 API 是无状态的，"记忆"= 按 conversationId 把历史拼回 prompt
- LangChain4j 的 `ChatMemoryProvider` 负责按会话现场创建记忆，绝不共享记忆 Bean
- RAG 的检索器挂在 `contentRetriever` 上，问答链路自动做"检索 → 拼接 → 生成"
- `@Tool` 方法要让模型看得懂：方法名、参数名、`@P` 描述都算提示词
