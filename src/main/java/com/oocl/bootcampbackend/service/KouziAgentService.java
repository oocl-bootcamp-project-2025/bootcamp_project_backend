package com.oocl.bootcampbackend.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import okhttp3.MediaType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

@Service
public class KouziAgentService {
    @Value("${kouzi.api.url}")
    private String kouziApiUrl;

    @Value("${kouzi.api.key:}")
    private String kouziApiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    public String sendMessage(String message) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);
        if (kouziApiKey != null && !kouziApiKey.isEmpty()) {
            headers.set("Authorization", "Bearer " + kouziApiKey);
        }
        Map<String, Object> body = new HashMap<>();
        body.put("message", message);
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(kouziApiUrl, request, String.class);
        return response.getBody();
    }

    /**
     * 使用OkHttp流式请求Coze智能体API，逐步返回数据
     */
    public void streamChatWithCoze(String botId, String userId, String content, Consumer<String> onData) throws IOException {
        OkHttpClient client = new OkHttpClient();
        String url = "https://api.coze.cn/v3/chat?";
        String json = "{" +
                "\"bot_id\": \"" + botId + "\"," +
                "\"user_id\": \"" + userId + "\"," +
                "\"stream\": true," +
                "\"additional_messages\": [" +
                "{\"role\":\"user\",\"type\":\"question\",\"content_type\":\"text\",\"content\":\"" + content + "\"}" +
                "]}";
        RequestBody body = RequestBody.create(json, MediaType.parse("application/json"));
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", "Bearer pat_YYDHIzVdqWVzwbLS3HlZITrMHJtEGI2SVOGNqp5gkWDc6GxF7O6KYM90zE4we10p")
                .addHeader("Content-Type", "application/json")
                .post(body)
                .build();
        Call call = client.newCall(request);
        Response response = call.execute();
        if (!response.isSuccessful() || response.body() == null) {
            throw new IOException("Unexpected code " + response);
        }
        try (okio.BufferedSource source = response.body().source()) {
            while (!source.exhausted()) {
                String line = source.readUtf8LineStrict();
                onData.accept(line);
            }
        }
    }

    /**
     * 流式请求Coze智能体API，拼接所有content字段并返回完整内容
     */
    public String streamChatWithCozeAndCollectContent(String botId, String userId, String content) throws IOException {
        StringBuilder sb = new StringBuilder();
        ObjectMapper objectMapper = new ObjectMapper();
        streamChatWithCoze(botId, userId, content, line -> {
            try {
                String jsonLine = line;
                // 兼容SSE格式，去掉前缀"data: "（注意有空格）
                if (jsonLine.startsWith("data:")) {
                    jsonLine = jsonLine.substring(5);
                }
                if (jsonLine.trim().isEmpty() || jsonLine.trim().equals("[DONE]")) return;
                JsonNode node = objectMapper.readTree(jsonLine);
                if (!node.has("type") || !node.get("type").asText().equals("answer")) {
                    return; // 忽略心跳包
                }
                if (node.has("content")) {
                    String part = node.get("content").asText();
                    if (part != null && !part.isEmpty()) {
                        sb.append(part);
                    }
                }
            } catch (Exception e) {
                // 忽略解析异常，防止流中非JSON内容导致中断
//                System.out.println("Error parsing line: " + e.getMessage());
            }
        });
        return sb.toString();
    }

    /**
     * 将多个拼接的JSON对象字符串合并为一个Map（如{"day1": [...] }{"day1": [...] }）
     */
    public Map<String, Object> parseAndMergeJsonObjects(String result) {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> merged = new HashMap<>();
        // 用正则分割，提取每个JSON对象
        String[] jsonObjects = result.trim().split("\\}\\s*\\{");
        for (int i = 0; i < jsonObjects.length; i++) {
            String json = jsonObjects[i].trim();
            // 补全被分割掉的括号
            if (!json.startsWith("{")) json = "{" + json;
            if (!json.endsWith("}")) json = json + "}";
            try {
                Map<String, Object> map = objectMapper.readValue(json, Map.class);
                for (Map.Entry<String, Object> entry : map.entrySet()) {
                    String key = entry.getKey();
                    Object value = entry.getValue();
                    if (merged.containsKey(key) && value instanceof ArrayList && merged.get(key) instanceof ArrayList) {
                        ((ArrayList) merged.get(key)).addAll((ArrayList) value);
                    } else {
                        merged.put(key, value);
                    }
                }
            } catch (Exception e) {
                // 可选：打印日志
                // e.printStackTrace();
            }
        }
        return merged;
    }
}
